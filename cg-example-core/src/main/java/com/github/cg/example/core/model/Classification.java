package com.github.cg.example.core.model;

public enum Classification implements BaseEnum {

	SUV("SUV"),
	ECONOMY("Economy"),
	SPORT("Sport"),
	LUXURY("Luxury"),
	MINIVAN("Minivan");
	
	private String description;

	Classification(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
