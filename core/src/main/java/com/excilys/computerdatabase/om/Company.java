package com.excilys.computerdatabase.om;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	private long  id;
	private String name;

	public Company() {
		this.id=-1;
		this.name=null;
	}
	
	public static class Builder{
		Company company;
		
		private Builder(){
			this.company=new Company();
		}
		
		public Builder id(long id){
			if(id>0)
				this.company.setId(id);
			return this;
		}
		
		public Builder name(String name){
			if(name!=null)
				this.company.setName(name);
			return this;
		}
		
		public Company build() {
			return this.company;
		}
		
	}

	public static Builder builder(){
		return new Builder();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "Company "+ id+": "+name+";";
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (int) Math.sqrt(id);
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
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
