package com.github.cg.example.core.dao.query;

import java.util.List;

public class Filter<E> implements IPredicate {

	private String attribute;
	private E value;
	private E valueStart;
	private E valueEnd;
	private List<E> values;
	private FilterOperator operator;
	
	public Filter() {}
	
	public Filter(String attribute, FilterOperator operator) {
		this.attribute = attribute;
		this.operator = operator;		
	}
	
	public Filter(String attribute, FilterOperator operator, E value) {
		this(attribute, operator);
		this.value = value;
	}
	
	public Filter(String attribute, FilterOperator operator, List<E> values) {
		this(attribute, operator);
		this.values = values;
	}
	
	public Filter(String attribute, FilterOperator operator, E valueStart, E valueEnd) {
		this(attribute, operator);
		this.valueStart = valueStart;
		this.valueEnd = valueEnd;
	}
	
	public String getAttribute() {
		return attribute;
	}
	
	public FilterOperator getOperator() {
		return operator;
	}
	
	public E getValue() {
		return value;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setOperator(FilterOperator operator) {
		this.operator = operator;
	}

	public void setValue(E value) {
		this.value = value;
	}
	
	public E getValueStart() {
		return valueStart;
	}

	public void setValueStart(E valueStart) {
		this.valueStart = valueStart;
	}

	public E getValueEnd() {
		return valueEnd;
	}

	public void setValueEnd(E valueEnd) {
		this.valueEnd = valueEnd;
	}

	public List<E> getValues() {
		return values;
	}

	public void setValues(List<E> values) {
		this.values = values;
	}
	
	public boolean isOperatorBetween() {
		return (operator != null && (operator == FilterOperator.BETWEEN));
	}	
	
	public boolean isOperatorContains() {
		return (operator != null && operator == FilterOperator.CONTAINS);
	}
	
	public boolean isOperatorEquals() {
		return (operator != null && operator == FilterOperator.EQUALS);
	}
	
	public boolean isOperatorTypeIsNullOrIsNotNull() {
		return (operator != null &&
			(operator == FilterOperator.IS_NULL
					|| operator == FilterOperator.IS_NOT_NULL)
		);
	}
	
	public boolean isOperatorTypeSimple() {
		return (operator != null && (!isOperatorTypeIsNullOrIsNotNull() && !isOperatorBetween() && !isOperatorIn()));
	}
	
	public boolean isOperatorIn() {
		return (operator != null && (operator == FilterOperator.IN || operator == FilterOperator.NOT_IN));
	}

	@Override
	public boolean isFilter() {
		return true;
	}
}