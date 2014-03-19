package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
	final Logger logger=LoggerFactory.getLogger(ComputerDAO.class);

	private ComputerDAO() {
	}

	public static ComputerDAO getInstance() {
		return cd;
	}

	private void closeObjects(ResultSet rs, Statement stmt){
		try {
			if (rs != null)

				rs.close();

			if (stmt != null)

				stmt.close();
			
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
	
	public void count(Connection connection, SearchComputersWrapper wrapper) throws SQLException{
		PreparedStatement statement=null;
		ResultSet results=null;
		String name=wrapper.getName();
		int nb=0;
		
		logger.info("Counting computers");
		try {
			//connection=cm.getConnection();
			if(name==null||name.length()==0){
				statement=connection.prepareStatement("SELECT COUNT(id) FROM computer;");
			}else{
				if(wrapper.getSearchDomain()==1){
					statement=connection.prepareStatement("SELECT COUNT(ct.id) FROM computer AS ct JOIN company AS cn ON ct.company_id=cn.id Where cn.name LIKE ?;");
				}else{
					statement=connection.prepareStatement("SELECT COUNT(id) FROM computer Where name LIKE ?;");
				}
				statement.setString(1, "%"+name+"%");
			}
			results=statement.executeQuery();
			if(results.next())
			  nb=results.getInt(1);
		} catch (SQLException e) {
			logger.debug("SQLException while trying to count computers");
			throw e;
		}finally {
			closeObjects(results, statement);
		}
		wrapper.setCount(nb);
	}
	
	public void getList(Connection connection, SearchComputersWrapper wrapper) throws SQLException{
		ArrayList<Computer> computers=new ArrayList<Computer>(wrapper.getLimit());
		PreparedStatement statement=null;
		ResultSet results=null;
		String name=wrapper.getName();
		int ord=1;
		String asc;

		logger.info("Creating list of computers");
		
		switch(wrapper.getOrder()){
		case NAME:
			ord=2;
			break;
		case INTRODUCED:
			ord=3;
			break;
		case DISCONTINUED:
			ord=4;
			break;
		case COMPANY:
			ord=6;
			break;
		}
		if(wrapper.isAsc())
			asc="ASC";
		else
			asc="DESC";
		
		try {
			if(name==null||name.length()==0){
				statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
						+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id ORDER BY ? "+asc+" LIMIT ?,?;");
				statement.setInt(1,ord);
				if (wrapper.getStart()<0)
					statement.setNull(2, Types.INTEGER);
				else
					statement.setInt(2,wrapper.getStart());
				if(wrapper.getLimit()<0)
					statement.setNull(3, Types.INTEGER);
				else
					statement.setInt(3,wrapper.getLimit());
			}else{
				if(wrapper.getSearchDomain()==1)
					statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
							+ "JOIN company AS cn ON ct.company_id=cn.id WHERE cn.name LIKE ? ORDER BY ? "+asc+" LIMIT ?,?;");
				else
					statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
							+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.name LIKE ? ORDER BY ? "+asc+" LIMIT ?,?;");
				statement.setString(1, "%"+name+"%");
				statement.setInt(2, ord);
				if (wrapper.getStart()<0)
					statement.setNull(3, Types.INTEGER);
				else
					statement.setInt(3,wrapper.getStart());
				if(wrapper.getLimit()<0)
					statement.setNull(4, Types.INTEGER);
				else
					statement.setInt(4,wrapper.getLimit());
			}
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to list all computers");
			throw e;
		}finally {
			closeObjects(results, statement);
		}

		wrapper.setComputers(computers);
	}

	public Computer getComputer(Connection connection, long id) throws SQLException{
		Computer computer=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		
		logger.info("Selecting computer n°"+id);
		try {
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
					+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id WHERE ct.id=?;");
			statement.setLong(1, id);
			results=statement.executeQuery();
			if(results.next()){
				computer=this.createComputer(results);
			}
		} catch (SQLException e) {
			logger.debug("SQLException while trying to search computer based on id");
			throw e;
		}finally {
			closeObjects(results, statement);
		}

		return computer;
	}
	
	public void add(Connection connection, UpdateComputerWrapper wrapper) throws SQLException{
		PreparedStatement statement=null;
		
		logger.info("Adding a new computer");
		try {
			statement=connection.prepareStatement("INSERT INTO computer(id, name, introduced, discontinued, company_id)"
					+ "VALUES(0,?,?,?,?);");
			statement.setString(1, wrapper.getName());
			if(wrapper.getIntroduced()==null)
				statement.setNull(2, Types.TIMESTAMP);
			else
				statement.setTimestamp(2,new Timestamp(wrapper.getIntroduced().getTime()));
			if(wrapper.getDiscontinued()==null)
				statement.setNull(3, Types.TIMESTAMP);
			else
				statement.setTimestamp(3, new Timestamp(wrapper.getDiscontinued().getTime()));
			if(wrapper.getCompanyId()<1)
				statement.setNull(4, Types.INTEGER);
			else
				statement.setLong(4, wrapper.getCompanyId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while adding computer");
			throw e;
		}finally {
			closeObjects(null, statement);
		}
		
		
	}
	
	public void edit(Connection connection, UpdateComputerWrapper wrapper) throws SQLException{
		PreparedStatement statement=null;
		
		logger.info("Editing computer n°"+wrapper.getId());
		try {
			statement=connection.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? "
					+ "WHERE id=?;");
			statement.setString(1, wrapper.getName());
			if(wrapper.getIntroduced()==null)
				statement.setNull(2, Types.TIMESTAMP);
			else
				statement.setTimestamp(2,new Timestamp(wrapper.getIntroduced().getTime()));
			if(wrapper.getDiscontinued()==null)
				statement.setNull(3, Types.TIMESTAMP);
			else
				statement.setTimestamp(3, new Timestamp(wrapper.getDiscontinued().getTime()));
			if(wrapper.getCompanyId()<1)
				statement.setNull(4, Types.INTEGER);
			else
				statement.setLong(4, wrapper.getCompanyId());
			statement.setLong(5, wrapper.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while editing computer");
			throw e;
		}finally {
			closeObjects(null, statement);
		}
		
		
	}

	public void delete(Connection connection, long computerId) throws SQLException {
		PreparedStatement statement=null;
		
		logger.info("Deleting computer n°"+computerId);
		try {
			statement=connection.prepareStatement("DELETE FROM computer WHERE id=?;");
			statement.setLong(1,computerId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.debug("SQLException while deleting computer");
			throw e;
		}finally {
			closeObjects(null, statement);
		}
		
	}

}
