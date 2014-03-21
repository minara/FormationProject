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

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.FrenchDate;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;
    private boolean error=false;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        companyService= CompanyService.getInstance();
        computerService=ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies=new ArrayList<Company>();
		
		companies=companyService.getCompanies();
		if(error){
			String errorMsg="An error has occured while treating your request. Please, try again.";
			request.setAttribute("errorMsg", errorMsg);
		}
		request.setAttribute("error", error);
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Computer computer=new Computer();
		computer.setName(request.getParameter("name"));
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		if(request.getParameter("introducedDate")!=null&&request.getParameter("introducedDate")!="")
			try {
				computer.setIntroduced(new FrenchDate(format.parse(request.getParameter("introducedDate"))));
			} catch (ParseException e) {
				//ignore
			}
		if(request.getParameter("discontinuedDate")!=null&&request.getParameter("discontinuedDate")!="")
			try {
				computer.setDiscontinued(new FrenchDate(format.parse(request.getParameter("discontinuedDate"))));
			} catch (ParseException e) {
				//ignore
			}
		if(request.getParameter("company")!=null){
			Company c=Company.builder()
							.id(Long.parseLong(request.getParameter("company")))
							.build();
			computer.setCompany(c);
		}
		
		
		if(computerService.add(computer)){
			error=false;
			response.sendRedirect("DashboardServlet");
		}else{
			error=true;
			request.setAttribute("computer", computer);
			doGet(request, response);
		}
		
	}

}
