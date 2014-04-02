package com.excilys.computerdatabase.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.excilys.computerdatabase.util.PastDate;


public class ComputerDTO {
	@Min(0)
	private long   id;
	@NotBlank(message="This field is required;")
	@Pattern(regexp="\\w+[\\w\\s\\./\\-]*",message="the answer you give must contain only alphanumeric characters and -_/. and spaces")
	private String name;
	@PastDate(message="Incorrect date; the date must be in the past")
	private String introduced;
	@PastDate(message="Incorrect date; the date must be in the past")
	private String discontinued;
	@Min(0)
	private long   companyId;

	public ComputerDTO() {
		this.id=0;
		this.name=null;
		this.introduced=null;
		this.discontinued=null;
		this.companyId=0;
	}
	
	public static class Builder{
		ComputerDTO computerDTO;
		
		private Builder(){
			this.computerDTO=new ComputerDTO();
		}
		
		public Builder id(long id){
			if(id>0)
				this.computerDTO.setId(id);
			return this;
		}
		
		public Builder name(String name){
			if(name!=null)
				this.computerDTO.setName(name);
			return this;
		}
		
		public Builder introduced(String intro) {
			if(intro!=null)
				this.computerDTO.setIntroduced(intro);
			return this;
		}
		
		public Builder discontinued(String disco) {
			if(disco!=null)	
				this.computerDTO.setDiscontinued(disco);
			return this;
		}
		
		public Builder companyId(Long companyId) {
			if(companyId>0)
				this.computerDTO.setCompanyId(companyId);
			return this;
		}
		
		public ComputerDTO build() {
			return this.computerDTO;
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

}
