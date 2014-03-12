package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;

public class ComputerDAO {
	private final static ComputerDAO cd=new ComputerDAO();
	private ConnectionManager cm;

	private ComputerDAO() {
		cm=ConnectionManager.getInstance();
	}
	
	public static ComputerDAO getInstance() {
		return cd;
	}
	
	private void closeConnections(ResultSet rs, Statement stmt, Connection cn){
		try {
			if (rs != null)
				
				rs.close();
			
			if (stmt != null)
				
				stmt.close();
			
			if(cn !=null)
				cn.close();
			
		} catch (SQLException e) {}
	}
	
	private Computer createComputer(ResultSet rs) throws SQLException {
		Computer c=new Computer();
		c.setId(new Long(rs.getLong(1)));
		c.setName(rs.getString(2));
		c.setCompany(new Company());
		c.getCompany().setId(new Long(rs.getLong(3)));
		c.getCompany().setName(rs.getString(4));
		return c;
	}
	
	public List<Computer> getAllComputers(){
		Connection connection=null;
		ArrayList<Computer> computers=new ArrayList<Computer>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, cn.id, cn.name FROM computer AS ct LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id ;");
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		return computers;
	}
	
	public List<Computer> getComputers(int fromIndex, int listSize){
		Connection connection=null;
		ArrayList<Computer> computers=new ArrayList<Computer>(listSize);
		PreparedStatement statement=null;
		ResultSet results=null;
		
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name,cn.id, cn.name FROM computer AS ct LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id LIMIT ?,?;");
			statement.setString(1, Integer.toString(fromIndex));
			statement.setString(2, Integer.toString(listSize));
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		return computers;
	}

}
