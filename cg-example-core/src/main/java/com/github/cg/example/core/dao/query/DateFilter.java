package com.github.cg.example.core.dao.query;

import java.util.Date;

public class DateFilter extends Filter<Date> {

	private DateFilterPattern datePattern;

	public DateFilterPattern getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(DateFilterPattern datePattern) {
		this.datePattern = datePattern;
	}

	public boolean isDatePatternDate() {
		return getDatePattern() == DateFilterPattern.DATE;
	}
}