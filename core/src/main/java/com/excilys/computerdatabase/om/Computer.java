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
@Table(name = "computer")
public class Computer {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Computer() {
		this.id = -1;
		this.name = null;
		this.company = null;
		this.introduced = null;
		this.discontinued = null;
	}

	public static class Builder {
		Computer computer;

		private Builder() {
			this.computer = new Computer();
		}

		public Builder id(long id) {
			if (id > 0)
				this.computer.setId(id);
			return this;
		}

		public Builder name(String name) {
			if (name != null)
				this.computer.setName(name);
			return this;
		}

		public Builder introduced(LocalDate intro) {
			if (intro != null)
				this.computer.setIntroduced(intro);
			return this;
		}

		public Builder discontinued(LocalDate disco) {
			if (disco != null)
				this.computer.setDiscontinued(disco);
			return this;
		}

		public Builder company(Company company) {
			if (company != null)
				this.computer.setCompany(company);
			return this;
		}

		public Computer build() {
			return this.computer;
		}
	}

	public static Builder builder() {
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

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="introduced")
	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	@Column(name="discontinued")
	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	@ManyToOne
	@JoinColumn(name="company_id")
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued + "]";
	}

	@Override
	public int hashCode() {
		int result = 0;
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
		Computer other = (Computer) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
