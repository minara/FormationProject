package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping("Computer")
public class ComputerController {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showForm(){
		List<Company> companies=new ArrayList<Company>();
		companies=companyService.getCompanies();
		
		ModelAndView result = new ModelAndView();
		result.setViewName("addComputer");
		result.addObject("computerDTO", new ComputerDTO());
		result.addObject("companies", companies);
		return result;
	}
	
	@RequestMapping("editForm")
	public ModelAndView showEditForm(@RequestParam("computerId")String id){
		ComputerDTO computerDTO=null;
		Computer computer=computerService.getComputer(Long.parseLong(id));
		System.out.println(computer);
		computerDTO=ComputerMapper.toDTO(computer);
		ModelAndView result = new ModelAndView();
		result=showForm();
		result.addObject("computerDTO", computerDTO);
		result.setViewName("editComputer");
		return result;
	}
	
	@RequestMapping("delete")
	public ModelAndView delete(@RequestParam("computerId") String id){
		Boolean error=false;
		long computerId = Long.parseLong(id);
		if (!computerService.delete(computerId)) {
			error = true;
		}
		ModelAndView result = new ModelAndView();
		result.setViewName("redirect:../dashboard");
		result.addObject("error", error);
		result.addObject("action", "welcomeDelete");
		return result;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView saveForm(@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO, BindingResult result){
		ModelAndView mav = new ModelAndView();
		Boolean edit=computerDTO.getId()>0;
		if(result.hasErrors()){
			mav=showForm();
			mav.addObject("computerDTO", computerDTO);
			if(edit)
				mav.setViewName("editComputer");
		}else{
			Computer computer=ComputerMapper.fromDTO(computerDTO);
			boolean success=true;
			System.out.println(computer.getId());
			if(edit)
				success=computerService.edit(computer);
			else success=computerService.add(computer);
			if(success){
				mav.setViewName("redirect:dashboard");
				if(edit)
					mav.addObject("action", "welcomeEdit");
				else mav.addObject("action", "welcomeAdd");
			}else{
				mav=showForm();
				mav.addObject("error", true);
				mav.addObject("errorMsg","An error has occured while treating your request. Please, try again.");
				mav.addObject("computerDTO", computerDTO);
				if(edit)
					mav.setViewName("editComputer");
			}
		}
		return mav;
	}

	
}
