package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.Order;
import com.excilys.computerdatabase.dao.SearchComputersWrapper;
import com.excilys.computerdatabase.om.Computer;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerDAO computerDAO;
	private int searchDomain=0, limit=10, page=1;
	private Order order=Order.NAME;
	private boolean asc=true;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		computerDAO=ComputerDAO.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int count, start, pageMax ;
		List<Computer> computers;
		String name;
		boolean delete;
		SearchComputersWrapper wrapper=new SearchComputersWrapper();
		String nameSrc, introSrc, discoSrc, compSrc;
		nameSrc=introSrc=discoSrc=compSrc="image/downgris.jpg";

		if(request.getParameter("delete")!=null)
			delete=Boolean.parseBoolean(request.getParameter("delete"));
		else delete=false;

		if(delete==true){
			long computerId=Long.parseLong(request.getParameter("computerId"));
			computerDAO.delete(computerId);
		}

		name=request.getParameter("search");
		wrapper.setName(name);

		if(request.getParameter("searchDomain")!=null)
			searchDomain=Integer.parseInt(request.getParameter("searchDomain"));
		wrapper.setSearchDomain(searchDomain);

		computerDAO.count(wrapper);
		count=wrapper.getCount();

		if(request.getParameterValues("limitation")!=null)
			limit=Integer.parseInt(request.getParameter("limitation"));

		wrapper.setLimit(limit);

		pageMax=count/limit+1;
		if(request.getParameter("page")!=null){
			int p=Integer.parseInt(request.getParameter("page"));
			if(p>0){
				if(p<pageMax+1)
					page=p;
				else page=pageMax;
			}
		}else{
			if(page>pageMax)
				page=pageMax;
		}
		start=limit*(page-1);
		wrapper.setStart(start);

		if(request.getParameter("order")!=null){
			Order o=Order.getOrder(request.getParameter("order"));
			if(order.equals(o)){
				asc=!(asc);
			}else if(o!=null){
				order=o;
				asc=true;
			}
		}
		switch(order){
		case NAME:
			if(asc) nameSrc="image/downnoir.jpg";
			else nameSrc="image/upnoir.jpg";
			break;
		case INTRODUCED:
			if(asc) introSrc="image/downnoir.jpg";
			else introSrc="image/upnoir.jpg";
			break;
		case DISCONTINUED:
			if(asc) discoSrc="image/downnoir.jpg";
			else discoSrc="image/upnoir.jpg";
			break;
		case COMPANY:
			if(asc) compSrc="image/downnoir.jpg";
			else compSrc="image/upnoir.jpg";
			break;
		}
		try{
			wrapper.setOrder(order);
		}catch (Exception e){
			System.out.println("Choosen order is impossible");
		}
		wrapper.setAsc(asc);

		computerDAO.getList(wrapper);
		computers=wrapper.getComputers();

		request.setAttribute("name", name);
		request.setAttribute("domain", searchDomain);
		request.setAttribute("count", count);
		request.setAttribute("perPage", limit);
		request.setAttribute("pageNumber", page);
		request.setAttribute("lastPage", pageMax);
		request.setAttribute("nameSrc", nameSrc);
		request.setAttribute("introSrc", introSrc);
		request.setAttribute("discoSrc", discoSrc);
		request.setAttribute("compSrc", compSrc);
		request.setAttribute("computers", computers);
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}

}
