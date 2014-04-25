package com.excilys.computerdatabase.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Log;

@Service
public class ComputerService {
	private final Logger logger = LoggerFactory
			.getLogger(ComputerService.class);

	@Autowired
	private ComputerDAO computerDao;

	@Autowired
	private LogDAO logDao;

	private String table = "computer";

	public ComputerService() {
	}

	@Transactional(readOnly = true)
	public Page<Computer> search(String search, int searchDomain, Pageable pageable) {
		logger.info("Count and search transaction");
		if (search== null || search.length() == 0) {
			return computerDao.findAll(pageable);
		} else {
			if (searchDomain == 1) {
				return computerDao.findByCompanyNameContaining(search, pageable);
			} else {
				return computerDao.findByNameContaining(search, pageable);
			}
		}

	}

	@Transactional(readOnly = true)
	public Computer getComputer(long id) {
		return computerDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void add(Computer computer) {
		logger.info("Add transaction");
		computerDao.save(computer);
		logger.info(((Long) computer.getId()).toString());
		Log log = Log.builder().operation("Add").time(LocalDate.now())
				.tableName(table).computer(null).build();
		if (computer.getId() > 0)
			log.setComputer(Computer.builder().id(computer.getId()).build());
		logDao.save(log);
	}

	@Transactional(readOnly = false)
	public void edit(Computer computer) {
		logger.info("Edit transaction");
		computerDao.save(computer);
		Log log = Log.builder().operation("Edit").time(LocalDate.now())
				.tableName(table).computer(null).build();
		if (computer.getId() > 0)
			log.setComputer(Computer.builder().id(computer.getId()).build());
		logDao.save(log);
	}

	@Transactional(readOnly = false)
	public void delete(long computerId) {
		logger.info("Delete transaction");
		computerDao.delete(computerId);
		Log log = Log.builder().operation("Delete").time(LocalDate.now())
				.tableName(table)
				.computer(Computer.builder().id(computerId).build()).build();
		logDao.save(log);
	}

	public List<Computer> findAll() {
		return computerDao.findAll();
	}

}
