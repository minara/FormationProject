package com.excilys.computerdatabase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.om.FrenchDate;

public class Validator {
	public final static String[] errorResponse={"This field is required; the answer you give must contain only alphanumeric characters and -_/. and spaces",
		"Incorrect date; the date must be in the past","Incorrect date; the date must be in the past and  more recent than the introduced date"};

	public Validator() {
	}

	public static FrenchDate validDate(String date){
		FrenchDate result=null;
		if(date.matches("^[012]\\d{3}\\-[01]\\d\\-[0123]\\d$")){
			int month=Integer.parseInt(date.substring(5, 7));
			if(0<month&&month<13){
				int day=Integer.parseInt(date.substring(8,10));
				if(day>31||day<1)
					return result;
				else if(month==2){
					if(day>29)
						return result;
					else if(day==29){
						int year=Integer.parseInt(date.substring(0, 4));
						if(year%4!=0)
							return result;
					}
				}
			}else return result;
		}else return result;


		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			result= new FrenchDate(format.parse(date));
			Date now=new Date();
			if(result.compareTo(now)>0){
				result=null;
				return result;
			}
		} catch (ParseException e) {
			result=null;
			return result;
		}
		return result;
		
	}

	public static void  validate(HttpServletRequest request) {
		boolean error=false;
		String name= null, introduced=null, discontinued=null;
		FrenchDate intro=null, disco=null;
		long companyId=0;
		String[] errors=new String[3];

		if(request.getParameter("name")!=null){
			name=request.getParameter("name");
			if(!name.matches("\\w+[\\w\\s\\./\\-]*")){
				error=true;
				errors[0]= errorResponse[0];
			}
		}else{
			error=true;
			errors[0]= errorResponse[0];
		}
		if(request.getParameter("introducedDate")!=null&&request.getParameter("introducedDate")!=""){
			introduced=request.getParameter("introducedDate");
			intro=validDate(introduced);
			if(intro==null){
				error=true;
				errors[1]= errorResponse[1];
			}
		}
		if(request.getParameter("discontinuedDate")!=null&&request.getParameter("discontinuedDate")!=""){
			discontinued=request.getParameter("discontinuedDate");
			disco=validDate(discontinued);
			if(disco!=null){
				if(intro!=null){
					if(disco.compareTo(intro)<1){
						error=true;
						errors[2]= errorResponse[2];
					}
				}
			}else{
				error=true;
				errors[2]= errorResponse[2];
			}
		}
		if(request.getParameter("company")!=null){
			companyId=Long.parseLong(request.getParameter("company"));
		}

		ComputerDTO computer=ComputerDTO.builder()
				.name(name)
				.introduced(introduced)
				.discontinued(discontinued)
				.companyId(companyId)
				.build();
		request.setAttribute("computer", computer);
		request.setAttribute("error", error);
		request.setAttribute("msgs", errors);
	}

}
