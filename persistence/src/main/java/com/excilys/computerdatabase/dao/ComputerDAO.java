package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAO {
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	@Autowired
	private BoneCPDataSource dataSource;
	@Autowired
	private SessionFactory sessionFactory;

	public ComputerDAO() {
	}

	public void count(Page<Computer> wrapper) {

		String name = wrapper.getName();
		int nb = 0;
		StringBuilder hql = new StringBuilder(
				"SELECT COUNT(ct.id) FROM Computer AS ct");
		Query query;

		logger.info("Counting computers");
		if (name == null || name.length() == 0) {
			query = sessionFactory.getCurrentSession().createQuery(
					hql.toString());
		} else {
			if (wrapper.getSearchDomain() == 1)
				hql.append(" JOIN ct.company AS cn Where cn.name LIKE :search");
			else 
				hql.append(" Where ct.name LIKE :search");
			
			StringBuilder search = new StringBuilder("%");
			search.append(name).append("%");
			query=sessionFactory.getCurrentSession().createQuery(
					hql.toString());
			query.setParameter("search", search.toString());
		}

		nb = ((Long) query.list().get(0)).intValue();
		wrapper.setCount(nb);
	}

	@SuppressWarnings("unchecked")
	public void getList(Page<Computer> wrapper) {
		List<Computer> computers = new ArrayList<Computer>(wrapper.getLimit());
		String name = wrapper.getName();
		String ord = "ct.id";
		String asc;

		logger.info("Creating list of computers");

		switch (wrapper.getOrder()) {
		case NAME:
			ord = "ct.name";
			break;
		case INTRODUCED:
			ord = "ct.introduced";
			break;
		case DISCONTINUED:
			ord = "ct.discontinued";
			break;
		case COMPANY:
			ord = "cn.name";
			break;
		}
		if (wrapper.isAsc())
			asc = "ASC";
		else
			asc = "DESC";

		StringBuilder hql = new StringBuilder("Select ct FROM Computer as ct");
		Query query;
		if (name == null || name.length() == 0) {
			hql.append(" left outer join ct.company as cn ORDER BY ").append(ord + " " + asc);
			query = sessionFactory.getCurrentSession().createQuery(hql.toString());
			
		} else {
			if (wrapper.getSearchDomain() == 1)
				hql.append(" join ct.company as cn WHERE cn.name LIKE :search ORDER BY ");
			else
				hql.append(" left outer join ct.company as cn WHERE ct.name LIKE :search ORDER BY ");
			hql.append(ord + " " + asc);
			StringBuilder search = new StringBuilder("%");
			search.append(name).append("%");
			query = sessionFactory.getCurrentSession().createQuery(hql.toString());
			query.setParameter("search", search.toString());
		}
		query.setFirstResult(wrapper.getStart());
		query.setMaxResults(wrapper.getLimit());
		computers = query.list();
		wrapper.setObjects(computers);
	}

	public Computer getComputer(long id) {
		logger.info("Selecting computer n°" + id);
		String hql="select ct from Computer as ct left outer join ct.company as cn where ct.id= :num";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("num", id);
		return (Computer) query.list().get(0);
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
