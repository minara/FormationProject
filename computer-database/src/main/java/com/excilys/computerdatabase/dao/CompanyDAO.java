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
	final Logger logger= LoggerFactory.getLogger(CompanyDAO.class);
	private DAOFactory factory;

	private CompanyDAO() {
		factory=DAOFactory.FACTORY;
	}
	
	public static CompanyDAO getInstance() {
		return cd;
	}

	private Company createCompany(ResultSet rs) throws SQLException {
		Company c=Company.builder()
							.id(new Long(rs.getLong(1)))
							.name(rs.getString(2))
							.build();
		return c;
	}
	
	private void closeObjects(ResultSet rs, Statement stmt, Connection connection){
		try {
			if (rs != null)
				
				rs.close();
			
			if (stmt != null)
				
				stmt.close();
			
			if(connection!=null&&connection.getAutoCommit())
				factory.closeConnection();
			
		} catch (SQLException e) {
			logger.debug("SQLException while closing connections in CompanyDAO");
		}
	}
	
	public List<Company> getAllCompanies() {
		Connection connection=null;
		ArrayList<Company> companies=new ArrayList<Company>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Creating full list of companies");
		try {
			connection=factory.getConnection();
			statement=connection.prepareStatement("SELECT id, name FROM company;");
			results=statement.executeQuery();
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while listing all companies");
			DAOFactory.getErrorTL().set(true);
			e.printStackTrace();
		}finally {
			closeObjects(results, statement,connection);
		}
		
		return companies;
	}
	

}
