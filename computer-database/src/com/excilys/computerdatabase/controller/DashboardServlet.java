package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.om.Computer;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerDAO computerDAO;
	private String searchDomain="0";

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
		int nbComputer;
		List<Computer> computers;
		String name=request.getParameter("search");
		boolean delete;
		

		if(request.getParameter("delete")!=null)
			delete=Boolean.parseBoolean(request.getParameter("delete"));
		else delete=false;

		if(delete==true){
			long computerId=Long.parseLong(request.getParameter("computerId"));
			computerDAO.delete(computerId);
		}
		if(name==null || name.length()==0){
			nbComputer=computerDAO.countComputers();
			computers=computerDAO.getAllComputers();
		}else{
			searchDomain=request.getParameter("searchDomain");
			if(searchDomain.equals("0")){
				nbComputer=computerDAO.countComputers("%"+name+"%");
				computers=computerDAO.getComputers("%"+name+"%");
			}else{
				nbComputer=computerDAO.countComputersByCompany("%"+name+"%");
				computers=computerDAO.getComputersByCompany("%"+name+"%");
			}
		}
		request.setAttribute("name", name);
		request.setAttribute("nbComputer", nbComputer);
		request.setAttribute("computers", computers);
		request.setAttribute("domain", Integer.parseInt(searchDomain));
		request.getRequestDispatcher("WEB-INF/dashboard.jsp").forward(request, response);
	}

}
