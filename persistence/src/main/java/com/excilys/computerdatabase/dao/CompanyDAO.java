package com.excilys.computerdatabase.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Company;

@Repository
public class CompanyDAO {
	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	@Autowired
	private SessionFactory sessionFactory;

	public CompanyDAO() {
	}

	@SuppressWarnings("unchecked")
	public List<Company> getAllCompanies() {
		logger.info("Creating full list of companies");
		List<Company> companies = sessionFactory.getCurrentSession().createCriteria(Company.class).list();
		return companies;
	}

}
