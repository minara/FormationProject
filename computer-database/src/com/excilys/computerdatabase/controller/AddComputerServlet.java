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

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.FrenchDate;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Validator;

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

		request.setAttribute("errorResponse", Validator.errorResponse);
		request.setAttribute("error", error);
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Validator.validate(request);

		if(((Boolean)request.getAttribute("error")).equals(false)){
			ComputerDTO computerDTO=(ComputerDTO)request.getAttribute("computer");
			if(computerService.add(computerDTO)){
				error=false;
				response.sendRedirect("DashboardServlet");
			}else{
				error=true;
				doGet(request, response);
			}
		}else{
			error=false;
			doGet(request, response);
		}

	}

}
