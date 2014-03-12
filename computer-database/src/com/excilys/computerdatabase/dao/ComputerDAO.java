package com.excilys.computerdatabase.dao;

public class ComputerDAO {
	private final static ComputerDAO cd=new ComputerDAO();
	private ConnectionManager cm;

	private ComputerDAO() {
		cm=ConnectionManager.getInstance();
	}
	
	public static ComputerDAO getInstance() {
		return cd;
	}
	
	

}
