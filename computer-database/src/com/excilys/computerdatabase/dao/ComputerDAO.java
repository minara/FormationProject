package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.FrenchDate;

public class ComputerDAO {
	private final static ComputerDAO cd=new ComputerDAO();
	private ConnectionManager cm;
	final Logger logger=LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerDAO() {
		cm=ConnectionManager.getInstance();
	}

	public static ComputerDAO getInstance() {
		return cd;
	}

	private void closeConnections(ResultSet rs, Statement stmt, Connection cn){
		try {
			if (rs != null)

				rs.close();

			if (stmt != null)

				stmt.close();

			if(cn !=null)
				cn.close();

		} catch (SQLException e) {
			logger.debug("SQLException while closing connection to database in ComputerDAO");
		}
	}

	private Computer createComputer(ResultSet rs) throws SQLException {
		Computer c=new Computer();
		c.setId(new Long(rs.getLong(1)));
		c.setName(rs.getString(2));

		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		try {
			if(rs.getTimestamp(3)==null)
				c.setIntroduced(null);
			else{
				c.setIntroduced(new FrenchDate(format.parse(rs.getTimestamp(3).toString())));
			}
			if(rs.getTimestamp(4)==null)
				c.setDiscontinued(null);
			else
				c.setDiscontinued(new FrenchDate(format.parse(rs.getTimestamp(4).toString())));
		} catch (ParseException e) {
			logger.debug("Exception while parsing the date to create a computer");
			e.printStackTrace();
		}

		c.setCompany(new Company());
		c.getCompany().setId(new Long(rs.getLong(5)));
		c.getCompany().setName(rs.getString(6));
		return c;
	}

	public List<Computer> getAllComputers(){
		Connection connection=null;
		ArrayList<Computer> computers=new ArrayList<Computer>();
		PreparedStatement statement=null;
		ResultSet results=null;

		logger.info("Creating full list of computers");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct LEFT OUTER JOIN company AS cn "
					+ "ON ct.company_id=cn.id ;");
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to list all computers");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}

		return computers;
	}

	public List<Computer> getComputers(int fromIndex, int listSize){
		Connection connection=null;
		ArrayList<Computer> computers=new ArrayList<Computer>(listSize);
		PreparedStatement statement=null;
		ResultSet results=null;

		logger.info("Creating partial list of computers");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
					+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id LIMIT ?,?;");
			statement.setInt(1, fromIndex);
			statement.setInt(2,listSize);
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to list a group of computers");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}

		return computers;
	}
	
	public List<Computer> getComputers(String name){
		Connection connection=null;
		ArrayList<Computer> computers=new ArrayList<Computer>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Selecting computers according to name");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
					+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.name LIKE ?;");
			statement.setString(1, name);
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to search computers by name");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}

		return computers;
	}
	
	public Computer getComputer(long id){
		Connection connection=null;
		Computer computer=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Selecting computers according to name");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
					+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.id=?;");
			statement.setLong(1, id);
			results=statement.executeQuery();
			if(results.next()){
				computer=this.createComputer(results);
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to search computers by name");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}

		return computer;
	}

	public int countComputers() {
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		int nb=0;
		
		logger.info("Counting all computers");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT COUNT(id) FROM computer;");
			results=statement.executeQuery();
			if(results.next())
			  nb=results.getInt(1);
		} catch (SQLException e) {
			logger.debug("SQLException while trying to count all computers");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		return nb;
	}
	public int countComputers(String name) {
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		int nb=0;
		
		logger.info("Counting computers from search by name");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT COUNT(id) FROM computer Where name LIKE ?;");
			statement.setString(1, name);
			results=statement.executeQuery();
			if(results.next())
			  nb=results.getInt(1);
		} catch (SQLException e) {
			logger.debug("SQLException while trying to count computers chosen by name");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		return nb;
	}
	
	public void addComputer(String name, String introduced, String discontinued, String id){
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Adding a new computer");
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("INSERT INTO computer(id, name, introduced, discontinued, company_id)"
					+ "VALUES(0,?,?,?,?);");
			statement.setString(1, name);
			if(introduced==null||introduced=="")
				statement.setNull(2, Types.TIMESTAMP);
			else
				statement.setString(2, introduced);
			if(discontinued==null||discontinued=="")
				statement.setNull(3, Types.TIMESTAMP);
			else
				statement.setString(3, discontinued);
			if(id.equals("0"))
				statement.setNull(4, Types.INTEGER);
			else
				statement.setString(4, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while adding computer");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		
	}
	
	public void editComputer(long id,String name, String introduced, String discontinued, String companyId){
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Editing computer n°"+id);
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? "
					+ "WHERE id=?;");
			statement.setString(1, name);
			if(introduced==null||introduced=="")
				statement.setNull(2, Types.TIMESTAMP);
			else
				statement.setString(2, introduced);
			if(discontinued==null||discontinued=="")
				statement.setNull(3, Types.TIMESTAMP);
			else
				statement.setString(3, discontinued);
			if(companyId.equals("0"))
				statement.setNull(4, Types.INTEGER);
			else
				statement.setString(4, companyId);
			statement.setLong(5, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while editing computer");
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		
		
	}

}
