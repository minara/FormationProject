package com.excilys.computerdatabase.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Order;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private int searchDomain=0, limit=10, page=1;
	private Order order=Order.NAME;
	private boolean asc=true;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		computerService=ComputerService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean delete;
		Page<Computer> wrapper;
		String nameSrc, introSrc, discoSrc, compSrc;
		nameSrc=introSrc=discoSrc=compSrc="image/downgris.jpg";

		if(request.getParameter("delete")!=null)
			delete=Boolean.parseBoolean(request.getParameter("delete"));
		else delete=false;
		if(delete==true){
			long computerId=Long.parseLong(request.getParameter("computerId"));
			computerService.delete(computerId);
		}
		
		if(request.getParameter("searchDomain")!=null)
			searchDomain=Integer.parseInt(request.getParameter("searchDomain"));

		if(request.getParameterValues("limitation")!=null)
			limit=Integer.parseInt(request.getParameter("limitation"));

		Page.Builder<Computer> cb = new Page.Builder<Computer>();
		wrapper= cb.name(request.getParameter("search"))
					.searchDomain(searchDomain)
					.limit(limit)
					.page(page)
					.build();

		if(request.getParameter("page")!=null){
			wrapper.setNewPage(Integer.parseInt(request.getParameter("page")));
		}
		
		if(request.getParameter("order")!=null){
			Order o=Order.getOrder(request.getParameter("order"));
			if(order.equals(o)){
				asc=!(asc);
			}else if(o!=null){
				order=o;
				asc=true;
			}
		}
		try{
			wrapper.setOrder(order);
		}catch (Exception e){
			System.out.println("Choosen order is impossible");
		}
		wrapper.setAsc(asc);
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
		default:
			break;
		}

		computerService.search(wrapper);
		
		page=wrapper.getPage();

		request.setAttribute("wrapper", wrapper);
		request.setAttribute("nameSrc", nameSrc);
		request.setAttribute("introSrc", introSrc);
		request.setAttribute("discoSrc", discoSrc);
		request.setAttribute("compSrc", compSrc);
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}

}
