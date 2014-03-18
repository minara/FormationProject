package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.om.Computer;

public class SearchComputersWrapper {
	private int start, limit, count, searchDomain;
	private String name;
	private List<Computer> computers;
	private Order order;
	private boolean asc;

	public SearchComputersWrapper() {
		this.count=-1;
		this.start=-1;
		this.limit=-1;
		this.name=null;
		this.searchDomain=0;
		this.order=Order.NAME;
		this.asc=true;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) throws Exception {
		if(order==null)
			throw new Exception("order can't be null");
		else
			this.order = order;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSearchDomain() {
		return searchDomain;
	}

	public void setSearchDomain(int searchDomain) {
		this.searchDomain = searchDomain;
	}

	public List<Computer> getComputers() {
		return computers;
	}

	public void setComputers(List<Computer> computers) {
		this.computers = computers;
	}
	

}
