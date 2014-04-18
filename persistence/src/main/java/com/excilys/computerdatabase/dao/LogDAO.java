package com.excilys.computerdatabase.dao;

import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Log;

@Repository
public class LogDAO {
	final Logger logger= LoggerFactory.getLogger(LogDAO.class);
	@Autowired
	private SessionFactory sessionFactory;

	public LogDAO() {
	}

	public void add( String operation, String table, long computerId) {
		logger.info("New operation "+operation+" on "+table+" "+computerId);

		Log log=Log.builder().operation(operation).time(LocalDate.now()).tableName(table).computer(null).build();
		
		if(computerId>0)
			log.setComputer(Computer.builder().id(computerId).build());
		
		sessionFactory.getCurrentSession().persist(log);
	}

	public void rollbackTest(){
		logger.info("Generating rollback through sqlexception");
		sessionFactory.getCurrentSession().createQuery("SELECT orphan FROM log").list();
	}

}
