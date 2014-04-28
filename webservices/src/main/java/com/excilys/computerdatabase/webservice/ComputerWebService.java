package com.excilys.computerdatabase.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.service.ComputerService;

@Component
@Path("/web")
public class ComputerWebService {

	@Autowired
	private ComputerService computerService;

	@GET
	@Produces("application/xml")
	public List<Computer> findAll() {
		return computerService.findAll();
	}

}
