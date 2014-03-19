package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dao.CompanyService;
import com.excilys.computerdatabase.dao.ComputerService;
import com.excilys.computerdatabase.dao.UpdateComputerWrapper;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.FrenchDate;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;
	private long id;
	private Computer computer;
	private String search;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyServlet() {
        super();
        companyService= CompanyService.getInstance();
        computerService=ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies=new ArrayList<Company>();
		id=Long.parseLong(request.getParameter("computerId"));
		search=request.getParameter("search");
		
		companies=companyService.getCompanies();
		computer=computerService.getComputer(id);
		request.setAttribute("companies", companies);
		request.setAttribute("computer", computer);
		request.setAttribute("search", search);
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UpdateComputerWrapper wrapper=new UpdateComputerWrapper();
		wrapper.setName(request.getParameter("name"));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(request.getParameter("introducedDate")!=null&&request.getParameter("introducedDate")!="")
			try {
				wrapper.setIntroduced(new FrenchDate(format.parse(request.getParameter("introducedDate"))));
			} catch (ParseException e) {
				//ignore
			}
		if(request.getParameter("discontinuedDate")!=null&&request.getParameter("discontinuedDate")!="")
			try {
				wrapper.setDiscontinued(new FrenchDate(format.parse(request.getParameter("discontinuedDate"))));
			} catch (ParseException e) {
				//ignore
			}
		if(request.getParameter("company")!=null)
			wrapper.setCompanyId(Long.parseLong(request.getParameter("company")));
		wrapper.setId(id);
					
		computerService.edit(wrapper);
		if(search==null||search.length()==0)
			response.sendRedirect("DashboardServlet");
		else{
			response.sendRedirect("DashboardServlet?search="+search);
		}
	}

}
