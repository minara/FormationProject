package com.excilys.computerdatabase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.DAOFactory;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.om.Company;

public class CompanyService {
	private final static CompanyService cs=new CompanyService();
	final Logger logger=LoggerFactory.getLogger(ComputerService.class);
	private CompanyDAO companyDao;
	private LogDAO logDao;
	private DAOFactory factory;

	public CompanyService() {
		companyDao=CompanyDAO.getInstance();
		logDao=LogDAO.getInstance();
		factory=DAOFactory.FACTORY;
	}
	
	public static CompanyService getInstance(){
		return cs;
	}
	
	
	public List<Company> getCompanies(){
		List<Company> companies=null;
		
		logger.info("Get companies transaction");
		factory.startTransaction();
		if(!DAOFactory.getErrorTL().get()){
			companies=companyDao.getAllCompanies();
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Search","company", -1);
			factory.endTransaction();
		}
		factory.closeConnection();
		
		return companies;
	}

}
