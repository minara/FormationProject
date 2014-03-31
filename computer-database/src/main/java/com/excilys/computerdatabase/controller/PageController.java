package com.excilys.computerdatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Order;

@Controller
@RequestMapping("/dashboard")
public class PageController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showDashboard(){
		Boolean error=false;
		
		Page.Builder<Computer> cb = new Page.Builder<Computer>();
		Page<Computer> wrapper= cb.name("").searchDomain(0).limit(10)
				.page(1).asc(true).build();
		try {
			wrapper.setOrder(Order.NAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!computerService.search(wrapper)) {
			error = true;
		}
		
		
		ModelAndView result = new ModelAndView();
		result.setViewName("dashboard");
		result.addObject("wrapper", wrapper);
		result.addObject("error", error);
		return result;
	}

}
