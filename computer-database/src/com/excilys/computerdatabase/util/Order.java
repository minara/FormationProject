package com.excilys.computerdatabase.util;

public enum Order {
	NAME, INTRODUCED, DISCONTINUED, COMPANY;
	
	public static Order getOrder(String order){
		if(order.equals("NAME"))
			return NAME;
		else if(order.equals("INTRODUCED"))
			return INTRODUCED;
		else if(order.equals("DISCONTINUED"))
			return DISCONTINUED;
		else if(order.equals("COMPANY"))
			return COMPANY;
		else return null;
	}
	
}
