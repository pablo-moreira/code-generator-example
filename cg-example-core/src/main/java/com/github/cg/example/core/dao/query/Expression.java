package com.github.cg.example.core.dao.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Expression implements IPredicate {

	public static Expression and() {
		return new Expression(BooleanOperator.AND);
	}
	
	public static Expression and(IPredicate ... expressions) {
		return new Expression(BooleanOperator.AND, expressions);
	}
	public static Expression or() {
		return new Expression(BooleanOperator.OR);
	}
	
	public static Expression or(IPredicate ... expressions) {
		return new Expression(BooleanOperator.OR, expressions);
	}

	private BooleanOperator operator;
	private List<IPredicate> expressions = new ArrayList<IPredicate>(); 

	public Expression() {}
	
	public Expression(BooleanOperator operator) {
		super();
		this.operator = operator;
	}
	
	public Expression(BooleanOperator operator, IPredicate ... expressions) {
		super();
		this.operator = operator;
		
		List<IPredicate> list = Arrays.asList(expressions);

		this.expressions = new ArrayList<IPredicate>();
		this.expressions.addAll(list);
	}
	
	public void setOperator(BooleanOperator operator) {
		this.operator = operator;
	}

	public void setExpressions(List<IPredicate> expressions) {
		this.expressions = expressions;
	}

	public BooleanOperator getOperator() {
		return operator;
	}

	public List<IPredicate> getExpressions() {
		return expressions;
	}

	public Expression add(IPredicate expression) {
		getExpressions().add(expression);
		return this;
	}

	public boolean isEmpty() {
		return getExpressions() != null;
	}
	
	public Expression copy() {
		
		Expression pred = new Expression(getOperator());
		
		for (IPredicate exp : getExpressions()) {
			if (exp instanceof Expression) {
				pred.add(((Expression) exp).copy());
			}
			else {
				pred.add(exp);
			}
		}
		
		return pred;
	}

	public List<Filter<?>> getFilters() {
				
		List<Filter<?>> filters = new ArrayList<Filter<?>>();
		
		for (IPredicate predicate : getExpressions()) {
			if (predicate.isFilter()) {
				filters.add((Filter<?>) predicate);
			}
			else {
				Expression pre = (Expression) predicate;
				filters.addAll(pre.getFilters());
			}
		}
		
		return filters;
	}

	@Override
	public boolean isFilter() {
		return false;
	}
}
