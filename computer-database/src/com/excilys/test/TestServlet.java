package com.excilys.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.dbaccess.CompanyQuery;
import com.excilys.dbaccess.ComputerDAO;
import com.excilys.om.Company;

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
        // TODO Auto-generated constructor stub
        System.out.println("init");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Starting tests");
		System.out.println(ComputerDAO.getDao().getConnection()==null);
		/*CompanyQuery company=CompanyQuery.getCq();
		System.out.println("End of init");
		List<Company> companies= company.getAllCompanies();
		System.out.println(companies);
		System.out.println("End of 1st test");
		companies=company.getCompanies("id>10 AND id<20");
		System.out.println(companies);
		companies=company.getCompanies("name LIKE \"Mic\"");
		System.out.println(companies);
		company.closeConnection();*/
	}

}
