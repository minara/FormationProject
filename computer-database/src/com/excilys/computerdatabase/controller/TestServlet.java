package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ConnectionManager;
import com.excilys.computerdatabase.om.Company;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Passing test on 12/03/2014 11:53 testing CompanyDAO Class
		CompanyDAO companyDao=CompanyDAO.getInstance();
		System.out.println("End of init");
		List<Company> companies= companyDao.getAllCompanies();
		System.out.println(companies);
		System.out.println("End of 1st test");
		companies=companyDao.getCompanies(30);
		System.out.println(companies);
	}

}
