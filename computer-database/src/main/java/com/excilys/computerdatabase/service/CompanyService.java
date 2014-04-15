package com.excilys.computerdatabase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.om.Company;

@Service
public class CompanyService {
	final Logger logger = LoggerFactory.getLogger(ComputerService.class);
	@Autowired
	private CompanyDAO companyDao;
	@Autowired
	private LogDAO logDao;

	public CompanyService() {
	}

	@Transactional
	public List<Company> getCompanies() {
		List<Company> companies = null;

		logger.info("Get companies transaction");
		companies = companyDao.getAllCompanies();
		logDao.add("Search", "company", -1);
		return companies;
	}

}
