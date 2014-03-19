package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.om.FrenchDate;

public class UpdateComputerWrapper {
	private String name;
	private FrenchDate introduced, discontinued;
	private long id, companyId;

	public UpdateComputerWrapper() {
		this.id=-1;
		this.name=null;
		this.companyId=-1;
		this.introduced=null;
		this.discontinued=null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FrenchDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(FrenchDate introduced) {
		this.introduced = introduced;
	}

	public FrenchDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(FrenchDate discontinued) {
		this.discontinued = discontinued;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
