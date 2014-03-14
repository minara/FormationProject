package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
	private long id;
	private Computer computer;
	private String search;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
        super();
        companyDAO= CompanyDAO.getInstance();
        computerDAO=ComputerDAO.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies=new ArrayList<Company>();
		id=Long.parseLong(request.getParameter("computerId"));
		search=request.getParameter("search");
		
		companies=companyDAO.getAllCompanies();
		computer=computerDAO.getComputer(id);
		request.setAttribute("companies", companies);
		request.setAttribute("computer", computer);
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String introduced=request.getParameter("introducedDate");
		String discontinued=request.getParameter("discontinuedDate");
		String companyId=request.getParameterValues("company")[0];
		System.out.println(name+" "+introduced+" "+discontinued+" "+companyId);
					
		computerDAO.editComputer(id, name, introduced, discontinued, companyId);
		if(search==null||search.length()==0)
			response.sendRedirect("DashboardServlet");
		else{
			response.sendRedirect("DashboardServlet?search="+search);
		}
	}

}
