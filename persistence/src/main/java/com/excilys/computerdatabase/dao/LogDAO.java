package com.excilys.computerdatabase.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.excilys.computerdatabase.om.Log;

public interface LogDAO extends JpaRepository<Log, Long>{
}
