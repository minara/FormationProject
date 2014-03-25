package com.excilys.computerdatabase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.dao.DAOFactory;
import com.excilys.computerdatabase.dao.LogDAO;
import com.excilys.computerdatabase.dto.ComputerDTO;
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

	public boolean count(Page<Computer> wrapper) {
		boolean success=true;
		logger.info("Count transaction");

		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			success=false;
		}else{
			computerDao.count( wrapper);
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Count",table, -1);
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				success=false;
			}
		}

		factory.closeConnection();
		return success;
	}

	public boolean search(Page<Computer> wrapper){
		boolean success=true;
		logger.info("Count and search transaction");
		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			success=false;
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
					DAOFactory.getErrorTL().set(true);
					e.printStackTrace();
				}
			}
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				success=false;
			}
		}
		factory.closeConnection();
		return success;
	}

	public ComputerDTO getComputer(long id){
		ComputerDTO computerDTO = null;
		logger.info("Select computer transaction");
		factory.startTransaction();
		if(!DAOFactory.getErrorTL().get()){
			computerDTO=computerDao.getComputer(id);
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Select",table, id);
			factory.endTransaction();
		}	
		factory.closeConnection();
		return computerDTO;
	}

	public boolean add(ComputerDTO computerDTO){
		boolean success=true;
		logger.info("Add transaction");
		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			success=false;
		}else{
			computerDao.add( computerDTO);
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Add",table, computerDTO.getId());
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				success=false;			}
		}
		factory.closeConnection();
		return success;
	}

	public boolean edit(ComputerDTO computerDTO){
		boolean success=true;
		logger.info("Edit transaction");
		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			success=false;
		}else{
			computerDao.edit(computerDTO);
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Edit",table, computerDTO.getId());
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				success=false;
			}
		}
		factory.closeConnection();
		return success;
	}

	public boolean delete(long computerId){
		boolean success=true;
		logger.info("Delete transaction");
		factory.startTransaction();
		if(DAOFactory.getErrorTL().get()){
			success=false;
		}else{
			computerDao.delete(computerId);
			if(!DAOFactory.getErrorTL().get())
				logDao.add("Delete",table, computerId);
			factory.endTransaction();
			if(DAOFactory.getErrorTL().get()){
				success=false;
			}
		}
		factory.closeConnection();
		return success;
	}

}
