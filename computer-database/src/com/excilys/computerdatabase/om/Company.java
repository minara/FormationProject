package com.excilys.computerdatabase.om;

public class Company {
	private long  id;
	private String name;

	public Company() {
	}
	//TODO: use pattern builder instead?
	public Company(long id, String name){
		this.id=id;
		this.name=name;
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
