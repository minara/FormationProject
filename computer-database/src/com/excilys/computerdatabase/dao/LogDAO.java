package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDAO {
	private final static LogDAO ld=new LogDAO();
	final Logger logger= LoggerFactory.getLogger(LogDAO.class);
	private DAOFactory factory;

	private LogDAO() {
		factory=DAOFactory.FACTORY;
	}
	
	public static LogDAO getInstance(){
		return ld;
	}
	
	private void closeConnections(ResultSet rs, Statement stmt,Connection connection){
		try {
			if (rs != null)

				rs.close();

			if (stmt != null)

				stmt.close();
			if(connection!=null&&connection.getAutoCommit())
				factory.closeConnection();
			
		} catch (SQLException e) {
			logger.debug("SQLException while closing connection to database in ComputerDAO");
		}
	}

	
	public void add( String operation, String table, long computerId) {
		Connection connection= null;
		PreparedStatement statement=null;
		
		logger.info("New operation "+operation+" on computer "+computerId);
		try {
			connection=factory.getConnection();
			statement=connection.prepareStatement("INSERT INTO log(id, time, operation, table_name, computer_id)"
					+ "VALUES(0,?,?,?,?);");
			Date d=new Date();
			statement.setTimestamp(1, new Timestamp(d.getTime()));
			statement.setString(2, operation);
			statement.setString(3, table);
			if(computerId<0)
				statement.setNull(4, Types.BIGINT);
			else
				statement.setLong(4, computerId);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.info("Failed to add operation "+operation+" on computer "+computerId+"to base");
			DAOFactory.getErrorTL().set(true);
			e.printStackTrace();
		}finally{
			closeConnections(null, statement,connection);
		}
		
	}
	
	public void rollbackTest(){
		Connection connection= null;
		PreparedStatement statement=null;
		
		logger.info("Generating rollback through sqlexception");
		try {
			connection=factory.getConnection();
			statement=connection.prepareStatement("SELECT orphan FROM log;");
			statement.executeQuery();
		} catch (SQLException e) {
			logger.info("SQLException occured prepare for rollback");
			DAOFactory.getErrorTL().set(true);
			//e.printStackTrace();
		}finally{
			closeConnections(null, statement,connection);
		}
	}

}
