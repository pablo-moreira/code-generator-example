package com.github.cg.example.core.util;

public class NumericoUtils {

	public static boolean isLong(String str) {
		
		boolean isLong = true;
		
		try {
			Long.parseLong(str);
		}
		catch (NumberFormatException nfe) {
			isLong = false;
		}
		
		return isLong;
	}
	
	public static boolean isInteger(String str) {

		boolean isInteger = true;
		
		try {
			Integer.parseInt(str);
		} 
		catch (NumberFormatException nfe) {
			isInteger = false;
		}
		
		return isInteger;
	}
}
