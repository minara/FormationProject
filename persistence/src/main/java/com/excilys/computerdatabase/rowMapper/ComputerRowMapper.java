package com.excilys.computerdatabase.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		DateTimeFormatter format=DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");
		
		Company company = Company.builder().id(new Long(rs.getLong(5)))
				.name(rs.getString(6)).build();
		
		Computer c = Computer.builder().id(new Long(rs.getLong(1)))
				.name(rs.getString(2)).company(company).build();

		if (rs.getTimestamp(3) != null)
			c.setIntroduced(LocalDate.parse(rs.getTimestamp(3).toString(),format));

		if (rs.getTimestamp(4) != null)
			c.setDiscontinued(LocalDate.parse(rs.getTimestamp(4).toString(),format));

		return c;
	}

}
