package com.excilys.computerdatabase.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.excilys.computerdatabase.om.Computer;


public interface ComputerDAO  extends JpaRepository<Computer, Long>{
	
	Page<Computer> findByNameContaining(String search, Pageable pageable);
	
	Page<Computer> findByCompanyNameContaining(String search, Pageable pageable);
}
