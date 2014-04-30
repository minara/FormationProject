package com.excilys.computerdatabase.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.om.Company;

@Service
public class CompanyService {
	final Logger logger = LoggerFactory.getLogger(ComputerService.class);
	@Autowired
	private CompanyDAO companyDao;
	
	public CompanyService() {
	}

	@Transactional(readOnly=true)
	public List<Company> getCompanies() {

		logger.info("Get companies transaction");
		return companyDao.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

}
