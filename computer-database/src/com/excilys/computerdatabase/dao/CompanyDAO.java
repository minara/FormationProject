package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.om.Company;

public class CompanyDAO {
	private final static CompanyDAO cd=new CompanyDAO();
	private ConnectionManager cm;

	private CompanyDAO() {
		cm=ConnectionManager.getInstance();
	}
	
	public static CompanyDAO getInstance() {
		return cd;
	}

	private Company createCompany(ResultSet rs) throws SQLException {
		Company c=new Company();
		c.setId(new Long(rs.getLong(1)));
		c.setName(rs.getString(2));
		return c;
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
	
	public List<Company> getAllCompanies(){
		Connection connection=null;
		ArrayList<Company> companies=new ArrayList<Company>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT id, name FROM company;");
			results=statement.executeQuery();
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		return companies;
	}
	
	public List<Company> getCompanies(int minId){
		Connection connection=null;
		ArrayList<Company> companies=new ArrayList<Company>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT id, name FROM company WHERE id>?;");
			statement.setString(1, Integer.toString(minId));
			results=statement.executeQuery();
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		return companies;
	}



	

}
