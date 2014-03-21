package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.ArrayList;

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
	private Boolean error;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		computerService=ComputerService.getInstance();
	}

	public void showErrorMsg(HttpServletRequest request) {
		String errorMsg="An error has occured while treating your request. Please, try again.";
		error=true;
		request.setAttribute("errorMsg", errorMsg);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean delete;
		Page<Computer> wrapper;
		String nameSrc, introSrc, discoSrc, compSrc;
		ArrayList<String> source=new ArrayList<String>(4);
		int i;
		for(i=0; i<4;i++){
			source.add("image/downgris.jpg");
		}
		System.out.println(source);
		//nameSrc=introSrc=discoSrc=compSrc="image/downgris.jpg";
		error =false;
		
		if(request.getParameter("delete")!=null)
			delete=Boolean.parseBoolean(request.getParameter("delete"));
		else delete=false;
		if(delete==true){
			long computerId=Long.parseLong(request.getParameter("computerId"));
			if(!computerService.delete(computerId)){
				showErrorMsg(request);
			}
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
			if(asc) source.set(0,"image/downnoir.jpg");
			else source.set(0,"image/upnoir.jpg");
			break;
		case INTRODUCED:
			if(asc) source.set(1,"image/downnoir.jpg");
			else source.set(1,"image/upnoir.jpg");
			break;
		case DISCONTINUED:
			if(asc) source.set(2,"image/downnoir.jpg");
			else source.set(2,"image/upnoir.jpg");
			break;
		case COMPANY:
			if(asc) source.set(3,"image/downnoir.jpg");
			else source.set(3,"image/upnoir.jpg");
			break;
		default:
			break;
		}

		if(!computerService.search(wrapper))
			showErrorMsg(request);
		page=wrapper.getPage();
		request.setAttribute("error", error);
		request.setAttribute("wrapper", wrapper);
		request.setAttribute("source", source);
		/*request.setAttribute("nameSrc", nameSrc);
		request.setAttribute("introSrc", introSrc);
		request.setAttribute("discoSrc", discoSrc);
		request.setAttribute("compSrc", compSrc);*/
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}

}
