package com.excilys.computerdatabase.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdatabase.om.Company;


public interface CompanyDAO extends JpaRepository<Company, Long>{

}
