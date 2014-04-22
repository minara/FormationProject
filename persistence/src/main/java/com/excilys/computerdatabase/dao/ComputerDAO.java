package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;

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
		
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Computer.class);
		criteria.setProjection(Projections.rowCount());
		if (!(name == null || name.length() == 0)) {
			StringBuilder search = new StringBuilder("%");
			search.append(name).append("%");
			
			if (wrapper.getSearchDomain() == 1)
				criteria.createAlias("company", "cn").add(Restrictions.like("cn.name", search.toString()));
			else 
				criteria.add(Restrictions.like("name", search.toString()));
		}

		nb = ((Long) criteria.list().get(0)).intValue();
		wrapper.setCount(nb);
	}

	@SuppressWarnings("unchecked")
	public void getList(Page<Computer> wrapper) {
		List<Computer> computers = new ArrayList<Computer>(wrapper.getLimit());
		String name = wrapper.getName();
		String ord = "id";

		logger.info("Creating list of computers");

		switch (wrapper.getOrder()) {
		case NAME:
			ord = "name";
			break;
		case INTRODUCED:
			ord = "introduced";
			break;
		case DISCONTINUED:
			ord = "discontinued";
			break;
		case COMPANY:
			ord = "cn.name";
			break;
		}
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Computer.class);
		criteria.createAlias("company", "cn",JoinType.LEFT_OUTER_JOIN);
		if (!(name == null || name.length() == 0)) {
			StringBuilder search = new StringBuilder("%");
			search.append(name).append("%");
			if (wrapper.getSearchDomain() == 1)
				criteria.add(Restrictions.like("cn.name", search.toString()));
			else
				criteria.add(Restrictions.like("name", search.toString()));
		}
		if (wrapper.isAsc())
			criteria.addOrder(Order.asc(ord));
		else
			criteria.addOrder(Order.desc(ord));
		
		criteria.setFirstResult(wrapper.getStart());
		criteria.setMaxResults(wrapper.getLimit());
		computers = criteria.list();
		wrapper.setObjects(computers);
	}

	public Computer getComputer(long id) {
		logger.info("Selecting computer n°" + id);
		Criteria criteria=sessionFactory.getCurrentSession().createCriteria(Computer.class);
		criteria.createAlias("company", "cn",JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.idEq(id));
		return (Computer) criteria.list().get(0);
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
