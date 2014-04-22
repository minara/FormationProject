package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.excilys.computerdatabase.om.QComputer;
import com.mysema.query.jpa.hibernate.HibernateQuery;

@Repository
public class ComputerDAO {
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	@Autowired
	private SessionFactory sessionFactory;

	public ComputerDAO() {
	}

	public void count(Page<Computer> wrapper) {

		String name = wrapper.getName();
		int nb = 0;

		logger.info("Counting computers");

		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(
				sessionFactory.getCurrentSession());
		query.from(computer);
		if (!(name == null || name.length() == 0)) {
			StringBuilder search = new StringBuilder("%");
			search.append(name).append("%");

			if (wrapper.getSearchDomain() == 1)
				query.innerJoin(computer.company).where(
						computer.company.name.like(search.toString()));
			else
				query.where(computer.name.like(search.toString()));
		}

		nb = ((Long) query.count()).intValue();
		wrapper.setCount(nb);
	}

	public void getList(Page<Computer> wrapper) {
		List<Computer> computers = new ArrayList<Computer>(wrapper.getLimit());
		String name = wrapper.getName();

		logger.info("Creating list of computers");
		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(
				sessionFactory.getCurrentSession());
		query.from(computer).leftJoin(computer.company);
		if (!(name == null || name.length() == 0)) {
			StringBuilder search = new StringBuilder("%");
			search.append(name).append("%");
			if (wrapper.getSearchDomain() == 1)
				query.where(computer.company.name.like(search.toString()));
			else
				query.where(computer.name.like(search.toString()));
		}
		switch (wrapper.getOrder()) {
		case NAME:
			if (wrapper.isAsc())
				query.orderBy(computer.name.asc());
			else
				query.orderBy(computer.name.desc());
			break;
		case INTRODUCED:
			if (wrapper.isAsc())
				query.orderBy(computer.introduced.asc());
			else
				query.orderBy(computer.introduced.desc());
			break;
		case DISCONTINUED:
			if (wrapper.isAsc())
				query.orderBy(computer.discontinued.asc());
			else
				query.orderBy(computer.discontinued.desc());
			break;
		case COMPANY:
			if (wrapper.isAsc())
				query.orderBy(computer.company.name.asc());
			else
				query.orderBy(computer.company.name.desc());
			break;
		default:
			query.orderBy(computer.id.asc());
		}
		computers= query.offset(wrapper.getStart()).limit(wrapper.getLimit()).list(computer);
		wrapper.setObjects(computers);
	}

	public Computer getComputer(long id) {
		logger.info("Selecting computer n°" + id);
		QComputer computer = QComputer.computer;
		HibernateQuery query = new HibernateQuery(
				sessionFactory.getCurrentSession());
		return query.from(computer).leftJoin(computer.company)
				.where(computer.id.eq(id)).uniqueResult(computer);
	}

	public void add(Computer computer) {
		logger.info("Adding a new computer");
		sessionFactory.getCurrentSession().persist(computer);
		logger.info("Computer id " + computer.getId());

	}

	public void edit(Computer computer) {
		logger.info("Editing computer n°" + computer.getId());
		sessionFactory.getCurrentSession().merge(computer);

	}

	public void delete(long computerId) {
		logger.info("Deleting computer n°" + computerId);
		sessionFactory.getCurrentSession().delete(
				Computer.builder().id(computerId).build());
	}

}
