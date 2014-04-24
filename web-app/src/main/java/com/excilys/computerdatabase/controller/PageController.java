package com.excilys.computerdatabase.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Order;

@Controller
@RequestMapping("dashboard")
public class PageController {
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showDashboard(@CookieValue(value="search", defaultValue="") String search, @CookieValue(value="searchDomain", defaultValue="0") String searchDomain, 
			@CookieValue(value="limit", defaultValue="10") String limit, @CookieValue(value="page", defaultValue="1") String page, 
			@CookieValue(value="order", defaultValue="NAME") String order, @CookieValue(value="asc", defaultValue="true") String asc,
			@RequestParam(value="page", required=false) String newPage, HttpServletResponse response, @ModelAttribute("action") String action){

		String topMessage="welcome";
		if(!action.isEmpty())
			topMessage=action;
		Page.Builder<Computer> cb = new Page.Builder<Computer>();
		Page<Computer> wrapper= cb.name(search).searchDomain(Integer.parseInt(searchDomain)).limit(Integer.parseInt(limit))
				.page(Integer.parseInt(page)).asc(Boolean.parseBoolean(asc)).build();
		if(newPage!=null&&newPage!="")
			wrapper.setPage(Integer.parseInt(newPage));
		try {
			wrapper.setOrder(Order.getOrder(order));
		} catch (Exception e) {
			System.out.println("Choosen order is impossible");
		}
		if (wrapper.getPage() > 0) {
			Cookie cookie=new Cookie("page",Integer.toString(wrapper.getPage()) );
			response.addCookie(cookie);
		}
		
		Sort sort= selectSort(wrapper.getOrder(), wrapper.isAsc());
		Pageable pageable= new PageRequest(wrapper.getPage()-1, wrapper.getLimit(), sort);
		org.springframework.data.domain.Page<Computer> p=computerService.search(wrapper.getName(), wrapper.getSearchDomain(), pageable);
		wrapper.setCount(p.getTotalElements());
		wrapper.setPageMax(p.getTotalPages());
		wrapper.setObjects(p.getContent());
		
		ModelAndView result = new ModelAndView();
		result.setViewName("dashboard");
		result.addObject("topMessage", topMessage);
		result.addObject("wrapper", wrapper);
		return result;
	}
	
	private Sort selectSort(Order order, boolean asc) {
		Sort sort=null;
		switch(order){
		case COMPANY:
			if(asc)
				sort= new Sort(new Sort.Order(Sort.Direction.ASC, "company.name"),new Sort.Order(Sort.Direction.ASC, "name"));
			else 
				sort= new Sort(new Sort.Order(Sort.Direction.DESC, "company.name"),new Sort.Order(Sort.Direction.ASC, "name"));
			break;
		case DISCONTINUED:
			if(asc)
				sort= new Sort(new Sort.Order(Sort.Direction.ASC, "discontinued"),new Sort.Order(Sort.Direction.ASC, "name"));
			else 
				sort= new Sort(new Sort.Order(Sort.Direction.DESC,"discontinued"),new Sort.Order(Sort.Direction.ASC, "name"));
			break;
		case INTRODUCED:
			if(asc)
				sort= new Sort(new Sort.Order(Sort.Direction.ASC, "introduced"),new Sort.Order(Sort.Direction.ASC, "name"));
			else 
				sort= new Sort(new Sort.Order(Sort.Direction.DESC,"introduced"),new Sort.Order(Sort.Direction.ASC, "name"));
			break;
		case NAME:
			if(asc)
				sort= new Sort(Sort.Direction.ASC, "name");
			else 
				sort= new Sort(Sort.Direction.DESC,"name");
			break;
		default:
			break;
		
		}
		
		return sort;
	}

	@RequestMapping("/search")
	public ModelAndView changeSearch(@RequestParam("search")String search, @RequestParam("searchDomain") String searchDomain, HttpServletResponse response){
		response.addCookie(new Cookie("search", search));
		response.addCookie(new Cookie("searchDomain", searchDomain));
		response.addCookie(new Cookie("page", "1"));
		ModelAndView result = new ModelAndView();
		result.setViewName("redirect:..");
		return result;
	}
	
	@RequestMapping("/limit")
	public ModelAndView changeLimit(@RequestParam("limitation") String limit, HttpServletResponse response){
		response.addCookie(new Cookie("limit", limit));
		response.addCookie(new Cookie("page", "1"));
		ModelAndView result = new ModelAndView();
		result.setViewName("redirect:..");
		return result;
	}
	
	@RequestMapping("/order")
	public ModelAndView changeOrder(@RequestParam("order")String order, @RequestParam("asc") String asc, HttpServletResponse response){
		response.addCookie(new Cookie("order", order));
		response.addCookie(new Cookie("asc", asc));
		ModelAndView result = new ModelAndView();
		result.setViewName("redirect:..");
		return result;
	}

}
