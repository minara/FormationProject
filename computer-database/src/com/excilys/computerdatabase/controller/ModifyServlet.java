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
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.FrenchDate;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Validator;

/**
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;
	private long id;
	private ComputerDTO computerDTO;
	private String search;
	private boolean error=false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyServlet() {
		super();
		companyService= CompanyService.getInstance();
		computerService=ComputerService.getInstance();
	}

	public void showErrorMsg(HttpServletRequest request) {
		String errorMsg="An error has occured while treating your request. Please, try again.";
		request.setAttribute("errorMsg", errorMsg);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies=new ArrayList<Company>();
		if(request.getParameter("computerId")!=null)
			id=Long.parseLong(request.getParameter("computerId"));
		search=request.getParameter("search");
		//System.out.println(search);

		companies=companyService.getCompanies();
		if(error){
			showErrorMsg(request);
		}else{
			computerDTO=computerService.getComputer(id);
		}

		if(request.getAttribute("computer")==null)
			request.setAttribute("computer", computerDTO);
		
		request.setAttribute("errorResponse", Validator.errorResponse);
		request.setAttribute("error", error);
		request.setAttribute("companies", companies);
		request.setAttribute("search", search);
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Validator.validate(request);

		if(((Boolean)request.getAttribute("error")).equals(false)){
			ComputerDTO computerDTO=(ComputerDTO)request.getAttribute("computer");
			computerDTO.setId(id);
			if(computerService.edit(computerDTO)){
				response.sendRedirect("DashboardServlet?search="+search);
				error=false;
			}else{
				this.computerDTO=computerDTO;
				error=true;
				doGet(request, response);
			}
		}else{
			error=false;
			doGet(request, response);
		}
	}

}
