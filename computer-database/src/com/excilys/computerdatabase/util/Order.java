package com.excilys.computerdatabase.util;

public enum Order {
	NAME, INTRODUCED, DISCONTINUED, COMPANY;
	
	public static Order getOrder(String order){
		if(order.equals("name"))
			return NAME;
		else if(order.equals("introduced"))
			return INTRODUCED;
		else if(order.equals("discontinued"))
			return DISCONTINUED;
		else if(order.equals("company"))
			return COMPANY;
		else return null;
	}
	
}
