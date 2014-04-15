package com.excilys.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;

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
	public boolean search(Page<Computer> wrapper) {// TODO: change all
													// signatures
		logger.info("Count and search transaction");
		computerDao.count(wrapper);
		wrapper.computePage();
		computerDao.getList(wrapper);
		return true; // success;
	}

	@Transactional(readOnly = true)
	public Computer getComputer(long id) {
		Computer computer = null;
		logger.info("Select computer transaction");
		computer = computerDao.getComputer(id);
		return computer;
	}

	@Transactional(readOnly = false)
	public boolean add(Computer computer) {
		boolean success = true;
		logger.info("Add transaction");
		computerDao.add(computer);
		logDao.add("Add", table, computer.getId());
		return success;
	}

	@Transactional(readOnly = false)
	public boolean edit(Computer computer) {
		boolean success = true;
		logger.info("Edit transaction");
		computerDao.edit(computer);
		logDao.add("Edit", table, computer.getId());
		return success;
	}

	@Transactional(readOnly = false)
	public boolean delete(long computerId) {
		boolean success = true;
		logger.info("Delete transaction");
		computerDao.delete(computerId);
		logDao.add("Delete", table, computerId);
		return success;
	}

}
