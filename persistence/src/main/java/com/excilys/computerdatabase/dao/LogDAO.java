package com.excilys.computerdatabase.dao;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LogDAO {
	final Logger logger= LoggerFactory.getLogger(LogDAO.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public LogDAO() {
	}

	public void add( String operation, String table, long computerId) {
		logger.info("New operation "+operation+" on "+table+" "+computerId);

		LocalDate date= LocalDate.now();
		Long id=null;
		if(computerId>0)
			id=computerId;
		jdbcTemplate.update("INSERT INTO log(id, time, operation, table_name, computer_id) VALUES(0,?,?,?,?);", date.toString(), operation, table, id);
	}

	public void rollbackTest(){
		logger.info("Generating rollback through sqlexception");
		jdbcTemplate.queryForObject("SELECT orphan FROM log;", Integer.class);
	}

}
