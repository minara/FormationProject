package com.excilys.computerdatabase.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.LocalDate;

public class PastDateValidator implements ConstraintValidator<PastDate, String> {

	@Override
	public void initialize(PastDate arg0) {

	}

	@Override
	public boolean isValid(String date, ConstraintValidatorContext arg1) {
		if (date.isEmpty())
			return true;
		if (date.matches("^[012]\\d{3}\\-[01]\\d\\-[0123]\\d$")) {
			int month = Integer.parseInt(date.substring(5, 7));
			if (0 < month && month < 13) {
				int day = Integer.parseInt(date.substring(8, 10));
				if (day > 31 || day < 1)
					return false;
				else if (month == 2) {
					if (day > 29)
						return false;
					else if (day == 29) {
						int year = Integer.parseInt(date.substring(0, 4));
						if (year % 4 != 0)
							return false;
					}
				}
			} else
				return false;
		} else
			return false;

		LocalDate d = LocalDate.parse(date);
		LocalDate now = LocalDate.now();
		if (d.compareTo(now) > 0) {
			return false;
		}
		return true;
	}

}
