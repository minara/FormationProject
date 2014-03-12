package com.excilys.dbaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.om.Company;

public class CompanyQuery extends Service {
	private final static CompanyQuery cq=new CompanyQuery();

	private CompanyQuery() {
		super();
	}
	
	public static CompanyQuery getCq() {
		return cq;
	}

	private Company createCompany(ResultSet rs) throws SQLException {
		Company c=new Company();
		c.setId(new Long(rs.getLong(1)));
		c.setName(rs.getString(2));
		return null;
	}
	
	public ArrayList<Company> getAllCompanies(){
		ArrayList<Company> companies=new ArrayList<Company>();
		Statement statement=null;
		ResultSet results=null;
		
		try {
			statement=connection.createStatement();
			results=statement.executeQuery("SELECT id, name FROM company;");
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (results != null)
					
					results.close();
				
				if (statement != null)
					
					statement.close();
				
			} catch (SQLException e) {}
		}
		
		return companies;
	}
	
	public ArrayList<Company> getCompanies(String condition){
		ArrayList<Company> companies=new ArrayList<Company>();
		PreparedStatement statement=null;
		ResultSet results=null;
		
		try {
			statement=connection.prepareStatement("SELECT id, name FROM company WHERE ?;");
			statement.setString(1, condition);
			results=statement.executeQuery();
			while(results.next()){
				companies.add(this.createCompany(results));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (results != null)
					
					results.close();
				
				if (statement != null)
					
					statement.close();
				
			} catch (SQLException e) {}
		}
		
		return companies;
	}



	

}
