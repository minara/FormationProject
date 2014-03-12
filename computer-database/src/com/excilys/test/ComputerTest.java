package com.excilys.test;

import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.excilys.dbaccess.CompanyQuery;
import com.excilys.om.Company;

public class ComputerTest {
	
	public static void main(String[] args) {
		System.out.println("Starting tests");
		CompanyQuery company=CompanyQuery.getCq();
		System.out.println("End of init");
		List<Company> companies= company.getAllCompanies();
		System.out.println(companies);
		System.out.println("End of 1st test");
		companies=company.getCompanies("id>10 AND id<20");
		System.out.println(companies);
		companies=company.getCompanies("name LIKE \"Mic\"");
		System.out.println(companies);
		company.closeConnection();

	}

}
