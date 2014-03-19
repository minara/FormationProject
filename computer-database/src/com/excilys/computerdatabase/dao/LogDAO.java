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
	final Logger logger= LoggerFactory.getLogger(CompanyDAO.class);

	private LogDAO() {
	}
	
	public static LogDAO getInstance(){
		return ld;
	}
	
	private void closeConnections(ResultSet rs, Statement stmt){
		try {
			if (rs != null)

				rs.close();

			if (stmt != null)

				stmt.close();
			
		} catch (SQLException e) {
			logger.debug("SQLException while closing connection to database in ComputerDAO");
		}
	}

	
	public void add(Connection connection, String operation, String table, long computerId) throws SQLException{
		PreparedStatement statement=null;
		
		logger.info("New operation "+operation+" on computer "+computerId);
		try {
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
			throw e;
		}finally{
			closeConnections(null, statement);
		}
		
	}

}
