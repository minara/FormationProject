package com.excilys.computerdatabase.om;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FrenchDate extends Date {
	private SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

	public FrenchDate() {
	}

	public FrenchDate(long date) {
		super(date);
	}
	
	public FrenchDate(Date date){
		super(date.getTime());
	}

	@SuppressWarnings("unused")
	@Override
	public String toString() {
		if (this!=null)
			return dateFormat.format(this);
		else
			return super.toString();
	}


}
