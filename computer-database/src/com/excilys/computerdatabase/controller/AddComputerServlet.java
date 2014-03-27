package com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
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
		
		Boolean error=false;
		if(request.getAttribute("error")!=null)
			error=(Boolean) request.getAttribute("error");
		else
			request.setAttribute("error", error);
		
		if(error){
			String errorMsg="An error has occured while treating your request. Please, try again.";
			request.setAttribute("errorMsg", errorMsg);
		}

		request.setAttribute("errorResponse", Validator.errorResponse);
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("WEB-INF/addComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Validator.validate(request);
		Boolean error=(Boolean)request.getAttribute("error");

		if(!error){
			ComputerDTO computerDTO=(ComputerDTO)request.getAttribute("computer");
			Computer computer=ComputerMapper.fromDTO(computerDTO);
			if(computerService.add(computer)){
				response.sendRedirect("DashboardServlet");
			}else{
				request.setAttribute("error", true);
				doGet(request, response);
			}
		}else{
			request.setAttribute("error", false);
			doGet(request, response);
		}

	}

}
