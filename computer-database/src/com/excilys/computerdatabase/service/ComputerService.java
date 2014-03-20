package com.excilys.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.DAOFactory;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.om.Computer;
import com.excilys.computerdatabase.om.Page;

public class ComputerService {
	private final static ComputerService cs=new ComputerService();
	private final Logger logger=LoggerFactory.getLogger(ComputerService.class);
	private ComputerDAO computerDao;
	private LogDAO logDao;
	private DAOFactory factory;
	private String table="computer";

	private ComputerService() {
		computerDao=ComputerDAO.getInstance();
		logDao=LogDAO.getInstance();
		factory=DAOFactory.FACTORY;
	}

	public static ComputerService getInstance(){
		return cs;
	} 

	/*private void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			logger.debug("Service failed to close connection");
			e.printStackTrace();
		}
	}*/

	public void count(Page<Computer> wrapper) {

		logger.info("Count transaction");

		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			//wrapper message
		}else{
			computerDao.count( wrapper);
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Count",table, -1);
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				//wrapper message
			}
		}

		factory.closeConnection();

	}

	public void search(Page<Computer> wrapper){

		logger.info("Count and search transaction");
		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			//wrapper message
		}else{
			computerDao.count( wrapper);
			if(!DAOFactory.getErrorTL().get()){
				try {
					wrapper.computePage();
					computerDao.getList(wrapper);
					if(!DAOFactory.getErrorTL().get())
						logDao.add("Count and search",table, -1);
				} catch (Exception e) {
					logger.debug("Incorrect count made the page computation fail");
					e.printStackTrace();
				}
			}
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				//wrapper message
			}
		}
			factory.closeConnection();
		}

		public Computer getComputer(long id){
			Computer computer=null;

			logger.info("Select computer transaction");
			factory.startTransaction();
			if(DAOFactory.getErrorTL().get()){
				//wrapper message
			}else{
				computer=computerDao.getComputer( id);
				if(!DAOFactory.getErrorTL().get())
					logDao.add("Select",table, id);
				factory.endTransaction();
				if(DAOFactory.getErrorTL().get()){
					//wrapper message
				}
			}	
				factory.closeConnection();
			return computer;
		}

		public void add(Computer computer){
			
			logger.info("Add transaction");
			factory.startTransaction();
			if(DAOFactory.getErrorTL().get()){
				//wrapper message
			}else{
				computerDao.add( computer);
				if(!DAOFactory.getErrorTL().get())
					logDao.add("Add",table, computer.getId());
				factory.endTransaction();
				if(DAOFactory.getErrorTL().get()){
					//wrapper message
				}
			}
			factory.closeConnection();
			
		}

		public void edit(Computer computer){
			logger.info("Edit transaction");
			factory.startTransaction();
			if(DAOFactory.getErrorTL().get()){
				//wrapper message
			}else{
				computerDao.edit(computer);
				if(!DAOFactory.getErrorTL().get())
					logDao.add("Edit",table, computer.getId());
				factory.endTransaction();
				if(DAOFactory.getErrorTL().get()){
					//wrapper message
				}
			}
			factory.closeConnection();
			
		}

		public void delete(long computerId){
			logger.info("Delete transaction");
			factory.startTransaction();
			if(DAOFactory.getErrorTL().get()){
				//wrapper message
			}else{
				computerDao.delete(computerId);
				if(!DAOFactory.getErrorTL().get())
					logDao.add("Delete",table, computerId);
				factory.endTransaction();
				if(DAOFactory.getErrorTL().get()){
					//wrapper message
				}
			}
			factory.closeConnection();
			
		}

	}
