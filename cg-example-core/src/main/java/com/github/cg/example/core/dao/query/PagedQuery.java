package com.github.cg.example.core.dao.query;

import java.util.ArrayList;
import java.util.List;

public class PagedQuery {

	private Expression where;
	private List<Sort> sorts = new ArrayList<Sort>();	
	private Integer firstResult;	
	private Integer maxResult;

	public void setSorts(List<Sort> sorts) {
		this.sorts = sorts;
	}
	
	public Integer getFirstResult() {
		return firstResult;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}
	
	public Expression getWhere() {
		return where;
	}

	public void setWhere(Expression where) {
		this.where = where;
	}

	public List<Sort> getSorts() {
		return sorts;
	}

	public void addSort(Sort sort) {
		getSorts().add(sort);		
	}
}