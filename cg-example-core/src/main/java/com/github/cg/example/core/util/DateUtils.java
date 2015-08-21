package com.github.cg.example.core.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	
	public static Calendar getCalendarTodayEnd() {
		
		Calendar calendar = getCalendarTodayBegin();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);

		return calendar;
	}
	
	public static Calendar getCalendarTodayBegin() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);

		return calendar;
	}

	public static Date getDateTodayBegin() {
		return getCalendarTodayBegin().getTime();
	}

	public static Date getDateTodayEnd() {
		return getCalendarTodayEnd().getTime();
	}
		
	public static Calendar getCalendarBegin(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getCalendarBegin(calendar);
	}
	
	public static Calendar getCalendarBegin(Calendar calendar) {
		
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		
		return calendar;
	}
	
	public static Calendar getCalendarEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return getCalendarEnd(calendar);
	}
	
	public static Calendar getCalendarEnd(Calendar calendar) {

		calendar = getCalendarBegin(calendar);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);

		return calendar;
	}
	
	public static Date getDateBegin(Date data) {
		return getCalendarBegin(data).getTime();
	}
	
	public static Date getDateEnd(Date data) {
		return getCalendarEnd(data).getTime();
	}

	public static boolean isSameDay(Date dt1, Date dt2) {
	
		Calendar data = new GregorianCalendar();
		data.setTime(dt1);
		
		Calendar comparacao = new GregorianCalendar();
		comparacao.setTime(dt2);
				
		return data.get(Calendar.DAY_OF_YEAR) == comparacao.get(Calendar.DAY_OF_YEAR) 
			&& data.get(Calendar.YEAR) == comparacao.get(Calendar.YEAR);		
	}
	
	public static Date getDateBegin(Calendar cal) {
		return getCalendarBegin(cal).getTime();
	}
}
