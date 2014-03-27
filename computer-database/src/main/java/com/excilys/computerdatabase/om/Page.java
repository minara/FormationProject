package com.excilys.computerdatabase.om;

import java.util.List;

import com.excilys.computerdatabase.util.Order;

public class Page<E>{
	private int start, limit, count, searchDomain, page, pageMax, newPage;
	private String name, errorMsg;
	private List<E> objects;
	private Order order;
	private boolean asc;

	public Page() {
		this.count=-1;
		this.start=-1;
		this.limit=-1;
		this.page=-1;
		this.pageMax=-1;
		this.newPage=-1;
		this.name=null;
		this.searchDomain=0;
		this.order=Order.NAME;
		this.asc=true;
		this.errorMsg=null;
	}
	
	public void computePage() throws Exception{
		if (count<0)
			throw new Exception("Incorrect count: no page creation");
		if(limit<1)
			limit=10;
		
		pageMax=count/limit;
		if(count>pageMax*limit||count==0)
			pageMax++;
		
		if(newPage>0){
			if(newPage<pageMax+1)
				page=newPage;
			else page=pageMax;
		}else{
			if(page<1)
				page=1;
			else if(page>pageMax)
				page=pageMax;
		}
		
		start=limit*(page-1);
	}

	public static class Builder<E>{
		Page<E> wrapper;
				
		public Builder(){
			this.wrapper=new Page<E>();
		}
		
		public Builder<E> start(int start) {
			if(start>=0)
				this.wrapper.setStart(start);
			return this;
		}
		
		public Builder<E> limit(int limit) {
			if(limit>0)
				this.wrapper.setLimit(limit);
			return this;
		}
		
		public Builder<E> count(int count) {
			if(count>=0)
				this.wrapper.setCount(count);
			return this;
		}
		
		public Builder<E> searchDomain(int domain) {
			if(domain>=0)
				this.wrapper.setSearchDomain(domain);
			return this;
		}
		
		public Builder<E> page(int page) {
			if(page>0)
				this.wrapper.setPage(page);
			return this;
		}
		
		public Builder<E> pageMax(int pageMax) {
			if(pageMax>0)
				this.wrapper.setPageMax(pageMax);
			return this;
		}
		
		public Builder<E> newPage(int page) {
			if(page>=0)
				this.wrapper.setNewPage(page);
			return this;
		}
		
		public Builder<E> name(String name) {
			if(name!=null)
				this.wrapper.setName(name);
			return this;
		}
		
		public Builder<E> objects(List<E> objects) {
			if(objects!=null)
				this.wrapper.setObjects(objects);
			return this;
		}
		
		public Builder<E> order(Order order) throws Exception {
			if(order!=null)
				this.wrapper.setOrder(order);
			return this;
		}
		
		public Builder<E> asc(boolean asc) {
			this.wrapper.setAsc(asc);
			return this;
		}
		
		public Builder<E> errorMsg(String msg){
			if(msg!=null)
				this.wrapper.setErrorMsg(msg);
			return this;
		}
		
		public Page<E> build() {
			return this.wrapper;
		}
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getNewPage() {
		return newPage;
	}

	public void setNewPage(int newPage) {
		this.newPage = newPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageMax() {
		return pageMax;
	}

	public void setPageMax(int pageMax) {
		this.pageMax = pageMax;
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

	public List<E> getObjects() {
		return objects;
	}

	public void setObjects(List<E> objects) {
		this.objects = objects;
	}
	

}
