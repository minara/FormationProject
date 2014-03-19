package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.om.Company;

public class CompanyService {
	private final static CompanyService cs=new CompanyService();
	private ConnectionManager cm;
	final Logger logger=LoggerFactory.getLogger(ComputerDAO.class);
	private CompanyDAO companyDao;
	private LogDAO logDao;

	public CompanyService() {
		cm=ConnectionManager.getInstance();
		companyDao=CompanyDAO.getInstance();
		logDao=LogDAO.getInstance();
	}
	
	public static CompanyService getInstance(){
		return cs;
	}
	
	private void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			logger.debug("Service failed to close connection");
			e.printStackTrace();
		}
	}
	
	public List<Company> getCompanies(){
		Connection connection=null;
		List<Company> companies=null;
		
		logger.info("Get companies transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			companies=companyDao.getAllCompanies(connection);
			logDao.add(connection,"Search","company", -1);
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed get companies transaction");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
		
		return companies;
	}

}
