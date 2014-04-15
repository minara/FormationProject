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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.jolbox.bonecp.BoneCPDataSource;

@Repository
public class LogDAO {
	final Logger logger= LoggerFactory.getLogger(LogDAO.class);
	@Autowired
	private BoneCPDataSource dataSource;

	public LogDAO() {
	}
	
	private void closeConnections(ResultSet rs, Statement stmt,Connection connection){
		try {
			if (rs != null)

				rs.close();

			if (stmt != null)

				stmt.close();
			/*if(connection!=null&&connection.getAutoCommit())
				factory.closeConnection();*/
			
		} catch (SQLException e) {
			logger.debug("SQLException while closing connection to database in ComputerDAO");
		}
	}

	
	public void add( String operation, String table, long computerId) {
		Connection connection= DataSourceUtils.getConnection(dataSource);
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
			e.printStackTrace();
			throw new DataAccessResourceFailureException("SQL error: "+e.getMessage());
		}finally{
			closeConnections(null, statement,connection);
		}
		
	}
	
	public void rollbackTest(){
		Connection connection= DataSourceUtils.getConnection(dataSource);
		PreparedStatement statement=null;
		
		logger.info("Generating rollback through sqlexception");
		try {
			statement=connection.prepareStatement("SELECT orphan FROM log;");
			statement.executeQuery();
		} catch (SQLException e) {
			logger.info("SQLException occured prepare for rollback");
			throw new DataAccessResourceFailureException("SQL error: "+e.getMessage());
		}finally{
			closeConnections(null, statement,connection);
		}
	}

}
