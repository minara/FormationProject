package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.FrenchDate;

public class ComputerDAO {
	private final static ComputerDAO cd=new ComputerDAO();
	private ConnectionManager cm;

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

		} catch (SQLException e) {}
	}

	private Computer createComputer(ResultSet rs) throws SQLException {
		Computer c=new Computer();
		c.setId(new Long(rs.getLong(1)));
		c.setName(rs.getString(2));

		SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss.S");
		//System.out.println(rs.getTimestamp(3)+" "+rs.getTimestamp(4));
		try {
			if(rs.getTimestamp(3)==null)
				c.setIntroduced(null);
			else
				c.setIntroduced(new FrenchDate(format.parse(rs.getTimestamp(3).toString())));
			if(rs.getTimestamp(4)==null)
				c.setDiscontinued(null);
			else
				c.setDiscontinued(new FrenchDate(format.parse(rs.getTimestamp(4).toString())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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

		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct LEFT OUTER JOIN company AS cn "
					+ "ON ct.company_id=cn.id ;");
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT ct.id, ct.name, ct.introduced, ct.discontinued, cn.id, cn.name FROM computer AS ct "
					+ "LEFT OUTER JOIN company AS cn ON ct.company_id=cn.id LIMIT ?,?;");
			statement.setString(1, Integer.toString(fromIndex));
			statement.setString(2, Integer.toString(listSize));
			results=statement.executeQuery();
			while(results.next()){
				computers.add(this.createComputer(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}

		return computers;
	}

	public int countComputers() {
		Connection connection=null;
		PreparedStatement statement=null;
		ResultSet results=null;
		int nb=0;
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT COUNT(id) FROM computer;");
			results=statement.executeQuery();
			if(results.next())
			  nb=results.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		try {
			connection=cm.getConnection();
			statement=connection.prepareStatement("SELECT COUNT(id) FROM computer Where name LIKE ?;");
			statement.setString(1, name);
			results=statement.executeQuery();
			if(results.next())
			  nb=results.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConnections(results, statement, connection);
		}
		return nb;
	}

}
