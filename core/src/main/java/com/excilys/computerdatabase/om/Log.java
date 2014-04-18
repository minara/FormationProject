package com.excilys.computerdatabase.om;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;


@Entity
@Table(name = "log")
public class Log {
	private long id;
	private LocalDate time;
	private String operation;
	private String tableName;
	private Computer computer;

	public Log() {
	}
	
	public static class Builder{
		Log log;
		
		private Builder(){
			this.log=new Log();
		}
		
		public Builder id(long id){
			if(id>0)
				this.log.setId(id);
			return this;
		}
		
		public Builder time(LocalDate time){
			if(time!=null)
				this.log.setTime(time);
			return this;
		}
		
		public Builder operation(String operation){
			if(operation!=null)
				this.log.setOperation(operation);
			return this;
		}
		
		public Builder tableName(String tableName){
			if(tableName!=null)
				this.log.setTableName(tableName);
			return this;
		}
		
		public Builder computer(Computer computer){
			if(computer!=null)
				this.log.setComputer(computer);
			return this;
		}
		
		public Log build() {
			return this.log;
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

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="time")
	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

	@Column(name="operation")
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Column(name="table_name")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@ManyToOne
	@JoinColumn(name="computer_id")
	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

}
