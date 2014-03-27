package com.excilys.computerdatabase.om;

import java.util.Date;


public class Computer {
	private long     id;
	private String    name;
	private FrenchDate introduced;
	private FrenchDate discontinued;
	private Company company;
	

	public Computer() {
		this.id=-1;
		this.name=null;
		this.company=null;
		this.introduced=null;
		this.discontinued=null;
	}
	
	public static class Builder{
		Computer computer;
		
		private Builder(){
			this.computer=new Computer();
		}
		
		public Builder id(long id){
			if(id>0)
				this.computer.setId(id);
			return this;
		}
		
		public Builder name(String name){
			if(name!=null)
				this.computer.setName(name);
			return this;
		}
		
		public Builder introduced(FrenchDate intro) {
			if(intro!=null)
				this.computer.setIntroduced(intro);
			return this;
		}
		
		public Builder discontinued(FrenchDate disco) {
			if(disco!=null)	
				this.computer.setDiscontinued(disco);
			return this;
		}
		
		public Builder company(Company company) {
			if(company!=null)
				this.computer.setCompany(company);
			return this;
		}
		
		public Computer build() {
			return this.computer;
		}
	}
	
	public static Builder builder(){
		return new Builder();
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(FrenchDate introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(FrenchDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued +", company="
				+ company.getName() + "]";
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = (int)Math.sqrt(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
}
