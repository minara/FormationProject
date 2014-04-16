package com.excilys.computerdatabase.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.om.Company;

public class CompanyRowMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company c = Company.builder().id(new Long(rs.getLong(1)))
				.name(rs.getString(2)).build();
		return c;
	}

}
