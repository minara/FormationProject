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
 * Servlet implementation class ModifyServlet
 */
@WebServlet("/ModifyServlet")
public class ModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CompanyService companyService;
	private ComputerService computerService;


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
		ComputerDTO computerDTO=null;
		long id=0;
		if(request.getParameter("computerId")!=null)
			id=Long.parseLong(request.getParameter("computerId"));
		Boolean error=false;
		if(request.getAttribute("error")!=null)
			error=(Boolean) request.getAttribute("error");
		else
			request.setAttribute("error", error);
		String search=request.getParameter("search");

		companies=companyService.getCompanies();
		if(error){
			showErrorMsg(request);
		}else if(request.getAttribute("computer")==null){
			computerDTO=computerService.getComputer(id);
			request.setAttribute("computer", computerDTO);
		}
		
		request.setAttribute("search", search);
		request.setAttribute("errorResponse", Validator.errorResponse);
		request.setAttribute("companies", companies);
		request.getRequestDispatcher("WEB-INF/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Validator.validate(request);
		long id=0;
		if(request.getParameter("computerId")!=null)
			id=Long.parseLong(request.getParameter("computerId"));
		String search=request.getParameter("search");

		if(((Boolean)request.getAttribute("error")).equals(false)){
			ComputerDTO computerDTO=(ComputerDTO)request.getAttribute("computer");
			computerDTO.setId(id);
			Computer computer=ComputerMapper.fromDTO(computerDTO);
			if(computerService.edit(computer)){
				response.sendRedirect("DashboardServlet?search="+search);
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
