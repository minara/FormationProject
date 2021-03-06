package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;
import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class ComputerDAO {
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);
	@Autowired
	private BoneCPDataSource dataSource;

	public ComputerDAO() {
	}

	private void closeObjects(ResultSet rs, Statement stmt,
			Connection connection) {
		try {
			if (rs != null)

				rs.close();

			if (stmt != null)

				stmt.close();

			/*if (connection != null && connection.getAutoCommit())
				factory.closeConnection();*/

		} catch (SQLException e) {
			logger.debug("SQLException while closing connection to database in ComputerDAO");
		}
	}

	private Computer createComputer(ResultSet rs) throws SQLException {
		Company company = Company.builder().id(new Long(rs.getLong(5)))
				.name(rs.getString(6)).build();
		Computer c = Computer.builder().id(new Long(rs.getLong(1)))
				.name(rs.getString(2)).company(company).build();

		if (rs.getTimestamp(3) != null)
			c.setIntroduced(LocalDate.parse(rs.getTimestamp(3).toString(),DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));

		if (rs.getTimestamp(4) != null)
			c.setDiscontinued(LocalDate.parse(rs.getTimestamp(4).toString(),DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S")));

		return c;
	}

	public void count(Page<Computer> wrapper) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		String name = wrapper.getName();
		int nb = 0;

		logger.info("Counting computers");
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			if (name == null || name.length() == 0) {
				statement = connection
						.prepareStatement("SELECT COUNT(id) FROM computer;");
			} else {
				if (wrapper.getSearchDomain() == 1) {
					statement = connection
							.prepareStatement("SELECT COUNT(ct.id) FROM computer AS ct JOIN company AS cn ON ct.company_id=cn.id Where cn.name LIKE ?;");
				} else {
					statement = connection
							.prepareStatement("SELECT COUNT(id) FROM computer Where name LIKE ?;");
				}
				statement.setString(1, "%" + name + "%");
			}
			results = statement.executeQuery();
			if (results.next())
				nb = results.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug("SQLException while trying to count computers");
			throw new DataAccessResourceFailureException("SQL error:"+e.getMessage());
			
		} finally {
			closeObjects(results, statement, connection);
		}
		wrapper.setCount(nb);
	}

	public void getList(Page<Computer> wrapper) {
		Connection connection = null;
		ArrayList<Computer> computers = new ArrayList<Computer>(
				wrapper.getLimit());
		PreparedStatement statement = null;
		ResultSet results = null;
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

		try {
			connection = DataSourceUtils.getConnection(dataSource);
			if (name == null || name.length() == 0) {
				statement = connection
						.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
								+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id ORDER BY ? "
								+ asc + " LIMIT ?,?;");
				statement.setInt(1, ord);
				if (wrapper.getStart() < 0)
					statement.setNull(2, Types.INTEGER);
				else
					statement.setInt(2, wrapper.getStart());
				if (wrapper.getLimit() < 0)
					statement.setNull(3, Types.INTEGER);
				else
					statement.setInt(3, wrapper.getLimit());
			} else {
				if (wrapper.getSearchDomain() == 1)
					statement = connection
							.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
									+ "JOIN company AS cn ON ct.company_id=cn.id WHERE cn.name LIKE ? ORDER BY ? "
									+ asc + " LIMIT ?,?;");
				else
					statement = connection
							.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
									+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.name LIKE ? ORDER BY ? "
									+ asc + " LIMIT ?,?;");
				statement.setString(1, "%" + name + "%");
				statement.setInt(2, ord);
				if (wrapper.getStart() < 0)
					statement.setNull(3, Types.INTEGER);
				else
					statement.setInt(3, wrapper.getStart());
				if (wrapper.getLimit() < 0)
					statement.setNull(4, Types.INTEGER);
				else
					statement.setInt(4, wrapper.getLimit());
			}
			results = statement.executeQuery();
			while (results.next()) {
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to list all computers");
			e.printStackTrace();
			throw new DataAccessResourceFailureException("SQL error:"+e.getMessage());
		} finally {
			closeObjects(results, statement, connection);
		}

		wrapper.setObjects(computers);
	}

	public Computer getComputer(long id) {
		Connection connection = null;
		Computer computer = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		logger.info("Selecting computer n°" + id);
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection
					.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
							+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.id=?;");
			statement.setLong(1, id);
			results = statement.executeQuery();
			if (results.next()) {
				computer = this.createComputer(results);
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to search computer based on id");
			e.printStackTrace();
			throw new DataAccessResourceFailureException("SQL error:"+e.getMessage());
		} finally {
			closeObjects(results, statement, connection);
		}

		return computer;
	}

	public void add(Computer computer) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet results = null;

		logger.info("Adding a new computer");
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection.prepareStatement(
					"INSERT INTO computer(id, name, introduced, discontinued, company_id)"
							+ "VALUES(0,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, computer.getName());
			if (computer.getIntroduced() == null)
				statement.setNull(2, Types.TIMESTAMP);
			else
				statement.setString(2, computer.getIntroduced().toString());
			if (computer.getDiscontinued() == null)
				statement.setNull(3, Types.TIMESTAMP);
			else
				statement.setString(3, computer.getDiscontinued().toString());
			if (computer.getCompany() == null
					|| computer.getCompany().getId() < 1)
				statement.setNull(4, Types.INTEGER);
			else
				statement.setLong(4, computer.getCompany().getId());
			statement.executeUpdate();
			results = statement.getGeneratedKeys();
			if (results.next())
				computer.setId(results.getLong(1));
		} catch (SQLException e) {
			logger.debug("SQLException while adding computer");
			e.printStackTrace();
			throw new DataAccessResourceFailureException("SQL error:"+e.getMessage());
		} finally {
			closeObjects(null, statement, connection);
		}

	}

	public void edit(Computer computer) {
		Connection connection = null;
		PreparedStatement statement = null;

		logger.info("Editing computer n°" + computer.getId());
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection
					.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? "
							+ "WHERE id=?;");
			statement.setString(1, computer.getName());
			if (computer.getIntroduced() == null)
				statement.setNull(2, Types.TIMESTAMP);
			else
				statement.setString(2, computer.getIntroduced().toString());
			if (computer.getDiscontinued() == null)
				statement.setNull(3, Types.TIMESTAMP);
			else
				statement.setString(3, computer.getDiscontinued().toString());
			if (computer.getCompany() == null
					|| computer.getCompany().getId() < 1)
				statement.setNull(4, Types.INTEGER);
			else
				statement.setLong(4, computer.getCompany().getId());
			statement.setLong(5, computer.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while editing computer");
			//DAOFactory.getErrorTL().set(true);
			e.printStackTrace();
			throw new DataAccessResourceFailureException("SQL error:"+e.getMessage());
		} finally {
			closeObjects(null, statement, connection);
		}

	}

	public void delete(long computerId) {
		Connection connection = null;
		PreparedStatement statement = null;

		logger.info("Deleting computer n°" + computerId);
		try {
			connection = DataSourceUtils.getConnection(dataSource);
			statement = connection
					.prepareStatement("DELETE FROM computer WHERE id=?;");
			statement.setLong(1, computerId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while deleting computer");
			e.printStackTrace();
			throw new DataAccessResourceFailureException("SQL error:"+e.getMessage());
		} finally {
			closeObjects(null, statement, connection);
		}

	}

}
