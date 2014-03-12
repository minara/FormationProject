package com.excilys.om;

import java.security.Timestamp;

public class Computer {
	private long     id;
	private String    name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private long     companyId;

	public Computer() {
		// TODO demander last id à la base et ++
	}

	public Computer(long id, String name, Timestamp introduced, Timestamp discontinued, long companyId){
		this.id=id;
		this.name=name;
		this.introduced=introduced;
		this.discontinued=discontinued;
		this.companyId=companyId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
}
