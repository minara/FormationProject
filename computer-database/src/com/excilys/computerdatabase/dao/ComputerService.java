package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.om.Computer;

public class ComputerService {
	private final static ComputerService cs=new ComputerService();
	private ConnectionManager cm;
	final Logger logger=LoggerFactory.getLogger(ComputerDAO.class);
	private ComputerDAO computerDao;
	private LogDAO logDao;
	private String table="computer";

	private ComputerService() {
		cm=ConnectionManager.getInstance();
		computerDao=ComputerDAO.getInstance();
		logDao=LogDAO.getInstance();
	}
	
	public static ComputerService getInstance(){
		return cs;
	} 
	
	private void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			logger.debug("Service failed to close connection");
			e.printStackTrace();
		}
	}
	
	public void count(SearchComputersWrapper wrapper){
		Connection connection=null;
		
		logger.info("Count transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			computerDao.count(connection, wrapper);
			logDao.add(connection,"Count",table, -1);
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed count transaction");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
	}
	
	public void search(SearchComputersWrapper wrapper){
		Connection connection=null;
		
		logger.info("Search transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			computerDao.getList(connection, wrapper);
			logDao.add(connection,"Search",table, -1);
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed search transaction");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
	}
	
	public Computer getComputer(long id){
		Computer computer=null;
		Connection connection=null;
		
		logger.info("Select computer transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			computer=computerDao.getComputer(connection, id);
			logDao.add(connection,"Select",table, id);
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed select transaction");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
		return computer;
	}
	
	public void add(UpdateComputerWrapper wrapper){
		Connection connection=null;
		
		logger.info("Add transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			computerDao.add(connection, wrapper);
			logDao.add(connection,"Add",table, -1);
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed add transaction");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
	}
	
	public void edit(UpdateComputerWrapper wrapper){
		Connection connection=null;
		
		logger.info("Edit transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			computerDao.edit(connection,wrapper);
			logDao.add(connection,"Edit",table, wrapper.getId());
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed edit transaction: rollback");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
	}
	
	public void delete(long computerId){
		Connection connection=null;
		
		logger.info("Delete transaction");
		try {
			connection=cm.getConnection();
			connection.setAutoCommit(false);
			computerDao.delete(connection,computerId);
			logDao.add(connection,"Delete",table, computerId);
			connection.commit();
		} catch (SQLException e) {
			logger.debug("Service failed edit transaction");
			if(connection!=null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.debug("Failed to rollback");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			closeConnection(connection);
		}
	}

}
