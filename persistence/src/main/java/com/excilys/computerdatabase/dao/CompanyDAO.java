package com.excilys.computerdatabase.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.rowMapper.CompanyRowMapper;

@Repository
public class CompanyDAO {
	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CompanyDAO() {
	}

	public List<Company> getAllCompanies() {
		logger.info("Creating full list of companies");

		List<Company> companies = jdbcTemplate.query("SELECT id, name FROM company;",
				new CompanyRowMapper());

		return companies;
	}

}
