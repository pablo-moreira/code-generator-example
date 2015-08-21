package com.github.cg.example.jsf.controller;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class LocaleCtrl {

	public String getLocale() {
		
		Locale locale = Locale.getDefault();
		
		return locale.getLanguage() + "_" + locale.getCountry();
	}
		
	private String getTimePatternByStyle(int style) {
		
		Locale locale = Locale.getDefault();
		
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getTimeInstance(style, locale);
		
		return sdf.toLocalizedPattern();
	}
	
	private String getDateTimePatternByStyle(int dateStyle, int timeStyle) {
		
		Locale locale = Locale.getDefault();
		
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
		
		return sdf.toLocalizedPattern();
	}

	private String getDatePatternByStyle(int style) {
		
		Locale locale = Locale.getDefault();
		
		SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateInstance(style, locale);
		
		return sdf.toLocalizedPattern();
	}
	
	public String getDateTimePattern() {
		return getDateTimePatternByStyle(SimpleDateFormat.MEDIUM, SimpleDateFormat.SHORT);
	}
	
	public String getDatePattern() {
		return getDatePatternByStyle(SimpleDateFormat.MEDIUM);
	}
	
	public String getTimePattern() {
		return getTimePatternByStyle(SimpleDateFormat.MEDIUM);
	}
}