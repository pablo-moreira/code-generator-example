package com.github.cg.example.core.dao.query;

public class Sort {
	
	public static Sort asc(String attribute) {
		return new Sort(attribute, SortOrder.ASC);
	}
	
	public static Sort desc(String attribute) {
		return new Sort(attribute, SortOrder.DESC);
	}
	
	private String attribute;
	
	private SortOrder order = SortOrder.ASC;

	public Sort() {}
	
	public Sort(String attribute, SortOrder order) {
		this.attribute = attribute;
		this.order = order;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public SortOrder getOrder() {
		return order;
	}

	public void setOrder(SortOrder order) {
		this.order = order;
	}
}
