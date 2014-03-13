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

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyDAO companyDAO;
	private ComputerDAO computerDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        companyDAO= CompanyDAO.getInstance();
        computerDAO=ComputerDAO.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies=new ArrayList<Company>();
		
		companies=companyDAO.getAllCompanies();
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String introduced=request.getParameter("introducedDate");
		String discontinued=request.getParameter("discontinuedDate");
		String id=request.getParameterValues("company")[0];
		System.out.println(name+" "+introduced+" "+discontinued+" "+id);
		computerDAO.addComputer(name, introduced, discontinued, id);
		response.sendRedirect("DashboardServlet");
		
	}

}
