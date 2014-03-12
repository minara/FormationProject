package com.excilys.computerdatabase.om;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FrenchDate extends Date {
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");

	public FrenchDate() {
	}

	public FrenchDate(long date) {
		super(date);
	}
	
	@SuppressWarnings("deprecation")
	public FrenchDate(Date date){
		super(date.getDate());
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
