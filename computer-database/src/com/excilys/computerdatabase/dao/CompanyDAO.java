package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.om.Company;

public class CompanyDAO {
	private final static CompanyDAO cd=new CompanyDAO();
	private ConnectionManager cm;
	final Logger logger= LoggerFactory.getLogger(CompanyDAO.class);

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
			
		} catch (SQLException e) {
			logger.debug("SQLException while closing connections in CompanyDAO");
		}
	}
	
	public List<Company> getAllCompanies(){
		Connection connection=null;
		ArrayList<Company> companies=new ArrayList<Company>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Creating full list of companies");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT id, name FROM company;");
			results=statement.executeQuery();
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while listing all companies");
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
		
		logger.info("Creating partial list of companies");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT id, name FROM company WHERE id>?;");
			statement.setString(1, Integer.toString(minId));
			results=statement.executeQuery();
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while listing companies from index"+minId);
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		return companies;
	}



	

}
