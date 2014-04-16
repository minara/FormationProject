package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.excilys.computerdatabase.rowMapper.ComputerRowMapper;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAO {
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	@Autowired
	private BoneCPDataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ComputerDAO() {
	}

	public void count(Page<Computer> wrapper) {

		String name = wrapper.getName();
		int nb = 0;
		String query=null;

		logger.info("Counting computers");
		if (name == null || name.length() == 0) {
			nb=jdbcTemplate.queryForObject("SELECT COUNT(id) FROM computer;", Integer.class);			
		} else {
			if (wrapper.getSearchDomain() == 1) {
				query="SELECT COUNT(ct.id) FROM computer AS ct JOIN company AS cn ON ct.company_id=cn.id Where cn.name LIKE ?;";
			} else {
				query="SELECT COUNT(id) FROM computer Where name LIKE ?;";
			}
			StringBuilder search=new StringBuilder("%");
			search.append(name).append("%");
			nb=jdbcTemplate.queryForObject(query, Integer.class, search.toString());
		}
		wrapper.setCount(nb);
	}

	public void getList(Page<Computer> wrapper) {
		ArrayList<Computer> computers = new ArrayList<Computer>(wrapper.getLimit());
		String name = wrapper.getName();
		int ord = 1;
		String asc;

		logger.info("Creating list of computers");

		switch (wrapper.getOrder()) {
		case NAME:
			ord = 2;
			break;
		case INTRODUCED:
			ord = 3;
			break;
		case DISCONTINUED:
			ord = 4;
			break;
		case COMPANY:
			ord = 6;
			break;
		}
		if (wrapper.isAsc())
			asc = "ASC";
		else
			asc = "DESC";

		StringBuilder query=new StringBuilder("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct ");
		if (name == null || name.length() == 0) {
			Integer start=null, limit=null;
			if (wrapper.getStart() >= 0)
				start=wrapper.getStart();
			if (wrapper.getLimit() >= 0)
				limit=wrapper.getLimit();
			query.append("LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id ORDER BY ? ").append(asc).append(" LIMIT ?,?;");
			computers= (ArrayList<Computer>)jdbcTemplate.query(query.toString(),new ComputerRowMapper(), ord, start,limit);
		} else {
			if (wrapper.getSearchDomain() == 1)
				query.append("JOIN company AS cn ON ct.company_id=cn.id WHERE cn.name LIKE ? ORDER BY ? ").append(asc)
				.append(" LIMIT ?,?;");
			else
				query.append("LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.name LIKE ? ORDER BY ? ")
				.append( asc).append(" LIMIT ?,?;");
			StringBuilder search=new StringBuilder("%");
			search.append(name).append("%");
			Integer start=null, limit=null;
			if (wrapper.getStart() >= 0)
				start=wrapper.getStart();
			if (wrapper.getLimit() >= 0)
				limit=wrapper.getLimit();
			computers= (ArrayList<Computer>)jdbcTemplate.query(query.toString(),new ComputerRowMapper(),search.toString() , ord, start,limit);
		}
		wrapper.setObjects(computers);
	}

	public Computer getComputer(long id) {
		logger.info("Selecting computer n°" + id);
		return jdbcTemplate.queryForObject("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
				+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.id=?;",new ComputerRowMapper(), id);
	}

	public void add(Computer computer) {
		logger.info("Adding a new computer");
		final String name=computer.getName();
		final String intro=computer.getIntroduced() == null?null:computer.getIntroduced().toString();
		final String disco=computer.getDiscontinued() == null? null : computer.getDiscontinued().toString();
		final Long companyId=computer.getCompany() == null || computer.getCompany().getId() < 1? null : computer.getCompany().getId();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
				new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement statement = connection.prepareStatement("INSERT INTO computer(id, name, introduced, discontinued, company_id)"
								+ "VALUES(0,?,?,?,?);", new String[] {"id"});
						statement.setString(1, name);
						statement.setString(2, intro);
						statement.setString(3, disco);
						statement.setLong(4, companyId);
						return statement;
					}
				},
				keyHolder);
		computer.setId((Long) keyHolder.getKey());

	}

	public void edit(Computer computer) {
		logger.info("Editing computer n°" + computer.getId());
		String intro=computer.getIntroduced() == null? null : computer.getIntroduced().toString();
		String disco=computer.getDiscontinued() == null? null : computer.getDiscontinued().toString();
		Long companyId=computer.getCompany() == null || computer.getCompany().getId() < 1? null : computer.getCompany().getId();

		jdbcTemplate.update("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;",
				computer.getName(), intro, disco, companyId, computer.getId());

	}

	public void delete(long computerId) {
		logger.info("Deleting computer n°" + computerId);
		jdbcTemplate.update("DELETE FROM computer WHERE id=?;", computerId);

	}

}
