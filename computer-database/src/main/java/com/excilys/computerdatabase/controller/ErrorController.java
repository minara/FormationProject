package com.excilys.computerdatabase.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ErrorController {
	
	@RequestMapping("/404")
	public ModelAndView show404(){
		ModelAndView result = new ModelAndView();
		result.setViewName("error404");
		return result;
	}
	
	@RequestMapping("/500")
	public ModelAndView show500(Exception e){
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		ModelAndView result = new ModelAndView();
		result.setViewName("error500");
		result.addObject("name", e.getClass().getSimpleName());
		result.addObject("message", e.getMessage());
		result.addObject("stackTrace", errors.toString());
		return result;
	}

}
