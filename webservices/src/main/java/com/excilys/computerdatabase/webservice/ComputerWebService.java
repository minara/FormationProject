package com.excilys.computerdatabase.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.service.ComputerService;

@WebService
public class ComputerWebService {
	
	@Autowired
	private ComputerService computerService;
	
	@WebMethod
	public List<Computer> findAll(){
		return computerService.findAll();
	}

}
