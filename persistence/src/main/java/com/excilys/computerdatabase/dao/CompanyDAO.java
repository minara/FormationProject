package com.excilys.computerdatabase.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.QCompany;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAO {
	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	@Autowired
	private SessionFactory sessionFactory;

	public CompanyDAO() {
	}

	
	public List<Company> getAllCompanies() {
		logger.info("Creating full list of companies");
		QCompany company=QCompany.company;
		HibernateQuery query = new HibernateQuery (sessionFactory.getCurrentSession());
		List<Company> companies =query.from(company).list(company);
		return companies;
	}

}
