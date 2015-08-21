package com.github.cg.example.core.dao.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import com.github.cg.example.core.dao.CriteriaQueryUtils;
import com.github.cg.example.core.util.DateUtils;

public class QueryProcessor<E> {
	
	public Predicate processExpression(CriteriaBuilder cb, CriteriaQuery<?> cq, Expression expression) {
		
		Predicate predicate;
		
		if (expression != null && !expression.getExpressions().isEmpty()) {
			
			predicate = BooleanOperator.OR.equals(expression.getOperator()) ? cb.or() : cb.and();
			
			List<IPredicate> expressions = expression.getExpressions();
			
			for (IPredicate exp : expressions) {
				if (exp.isFilter()) {
					predicate.getExpressions().add(filterToPredicate(cb, cq, (Filter<?>) exp));
				}
				else {
					predicate.getExpressions().add(processExpression(cb,cq, (Expression) exp));
				}
			}
		}
		else {
			predicate = cb.and();
		}
		
		return predicate;
	}
	
	public Predicate processWhere(CriteriaBuilder cb, CriteriaQuery<?> cq, Expression where) {
		return processExpression(cb, cq, where);		
	}
	
	public void processSort(CriteriaBuilder cb, CriteriaQuery<?> cq, List<Sort> sorts) {
		
		if (sorts != null) {
			for (Sort sort : sorts) {
				processSort(cb, cq, sort);
			}
		}
	}
	
	public void processSort(CriteriaBuilder cb, CriteriaQuery<?> cq, Sort sort) {
		
		Path<?> sortPath = CriteriaQueryUtils.getPathByStringPath(cq, sort.getAttribute());
		
		if (SortOrder.ASC.equals(sort.getOrder())) {
			cq.orderBy(cb.asc(sortPath));
		}
		else if (SortOrder.DESC.equals(sort.getOrder())) {
			cq.orderBy(cb.desc(sortPath));
		}
	}
	
	/**
	 * Converter um objeto da classe filter para Predicate do CriteriaQuery
	 * 
	 * @param cb
	 * @param cq
	 * @param filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Predicate filterToPredicate(CriteriaBuilder cb, CriteriaQuery<?> cq, Filter<?> filter) {
		
		DateFilter dateFilter = null;
		
		if (filter instanceof DateFilter) {
			dateFilter = (DateFilter) filter;
		}
				
		Path<?> path = CriteriaQueryUtils.getPathByStringPath(cq, filter.getAttribute());
		
		Predicate predicate;
		
		if (FilterOperator.EQUALS == filter.getOperator()) {
				
			if (dateFilter != null && dateFilter.isDatePatternDate()) {
				predicate = cb.between((Path<Date>) path, DateUtils.getDateBegin(dateFilter.getValue()), DateUtils.getDateEnd(dateFilter.getValue()));
			}
			else {
				predicate = cb.equal(path, filter.getValue());
			}
		}
		else if (FilterOperator.GE == filter.getOperator()) {
							
			if (dateFilter != null) {
				if(dateFilter.isDatePatternDate()) {
					predicate = cb.greaterThanOrEqualTo((Path<Date>) path, DateUtils.getDateBegin(dateFilter.getValue()));
				}
				else {
					predicate = cb.greaterThanOrEqualTo((Path<Date>) path, dateFilter.getValue());
				}
			}
			else {
				predicate = cb.ge((Path<Number>) path, (Number) filter.getValue());
			}
		}
		else if (FilterOperator.GT == filter.getOperator()) {
					
			if (dateFilter != null) {
				if(dateFilter.isDatePatternDate()) {
					predicate = cb.greaterThan((Path<Date>) path, DateUtils.getDateBegin(dateFilter.getValue()));
				}
				else {
					predicate = cb.greaterThan((Path<Date>) path, dateFilter.getValue());
				}
			}
			else {
				predicate = cb.gt((Path<Number>) path, (Number) filter.getValue());
			}
		}
									
		else if (FilterOperator.IS_NOT_NULL == filter.getOperator()) { 
			predicate = cb.isNotNull(path);
		}
						
		else if (FilterOperator.IS_NULL == filter.getOperator()) { 
			predicate = cb.isNull(path);
		}
			
		else if (FilterOperator.LE == filter.getOperator()) {				
			
			if (dateFilter != null) {
				if(dateFilter.isDatePatternDate()) {
					predicate = cb.lessThanOrEqualTo((Path<Date>) path, DateUtils.getDateEnd(dateFilter.getValue()));
				}
				else {
					predicate = cb.lessThanOrEqualTo((Path<Date>) path, dateFilter.getValue());
				}
			}
			else {
				predicate = cb.le((Path<Number>) path, (Number) filter.getValue());
			}
		}
		else if (FilterOperator.LT == filter.getOperator()) {
			
			if (dateFilter != null) {
				if(dateFilter.isDatePatternDate()) {
					predicate = cb.lessThan((Path<Date>) path, DateUtils.getDateBegin(dateFilter.getValue()));
				}
				else {
					predicate = cb.lessThan((Path<Date>) path, dateFilter.getValue());
				}
			}
			else {
				predicate = cb.lt((Path<Number>) path, (Number) filter.getValue());
			}
		}
		else if (FilterOperator.BETWEEN == filter.getOperator()) {
			
			if (dateFilter != null) {
				if(dateFilter.isDatePatternDate()) {
					predicate = cb.between((Path<Date>) path, DateUtils.getDateBegin(dateFilter.getValueStart()), DateUtils.getDateEnd(dateFilter.getValueEnd()));
				}
				else {
					predicate = cb.between((Path<Date>) path, dateFilter.getValueStart(), dateFilter.getValueEnd());
				}
			}
			else {
				predicate = cb.and(cb.ge((Path<Number>) path, (Number) filter.getValueStart()), cb.le((Path<Number>) path, (Number) filter.getValueEnd()));				
			}
		}
		else if (FilterOperator.LIKE == filter.getOperator()) {
			predicate =  cb.like(cb.lower((Path<String>) path), ((String) filter.getValue()).toLowerCase());
		}
				
		else if (FilterOperator.CONTAINS == filter.getOperator()) { 
			predicate = cb.like(cb.lower((Path<String>) path), "%" + ((String) filter.getValue()).toLowerCase() + "%");
		}			
		else if (FilterOperator.END_WITH == filter.getOperator()) {
			predicate = cb.like(cb.lower((Path<String>) path), ((String) "%" + ((String) filter.getValue()).toLowerCase()));
		}		
		else if (FilterOperator.START_WITH == filter.getOperator()) { 
			predicate = cb.like(cb.lower((Path<String>) path), ((String) filter.getValue()).toLowerCase() + "%");
		}
		else if (FilterOperator.NOT_CONTAINS == filter.getOperator()) { 
			predicate = cb.notLike(cb.lower((Path<String>) path), "%" + ((String) filter.getValue()).toLowerCase() + "%");																						
		}			
		else if (FilterOperator.NOT_END_WITH == filter.getOperator()) {
			predicate = cb.notLike(cb.lower((Path<String>) path), "%" + ((String) filter.getValue()).toLowerCase());
		}			
		else if (FilterOperator.NOT_START_WITH == filter.getOperator()) { 
			predicate = cb.notLike(cb.lower((Path<String>) path), ((String) filter.getValue()).toLowerCase() + "%");
		}			
		else if (FilterOperator.NOT_EQUALS == filter.getOperator()) {
			predicate = cb.notEqual(path, filter.getValue());
		}			
		else if (FilterOperator.IN == filter.getOperator()) { 
			predicate = path.in(filter.getValues());
		}	
		else if (FilterOperator.NOT_IN == filter.getOperator()) { 
			predicate = cb.not(path.in(filter.getValues()));
		}	
		else {
			throw new RuntimeException("O operador informado é inválido!");
		}
		
		return predicate;
	}
	
	/**
	 * Gera os joins dinamicamente dos atributos utilizados nas clausulas where e orderBy
	 * @param from
	 * @param where
	 * @param sorts
	 */
	public void processAttributesJoins(From<?, ?> from, Expression where, List<Sort> sorts) {
		
		Set<String> attributes = new HashSet<String>();
		
		List<Filter<?>> filters = where.getFilters();
		
		for (Filter<?> filter : filters) {
			attributes.add(filter.getAttribute());
		}
		
		if (sorts != null && !sorts.isEmpty()) {
			for (Sort sort : sorts) {
				attributes.add(sort.getAttribute());
			}
		}
		
		processAttributesJoins(from, attributes);
	}
	
	public void processAttributesJoins(From<?, ?> root, Set<String> attributes) {
		
		List<String> listaAlias = new ArrayList<String>();
		
		for (String atributte : attributes) {

			String[] joins = atributte.split("\\.");

			String pre = "";
			String alias = "";
			From<?,?> from = root;
			
			for(int i=0, t = joins.length - 1; i < t; i++) {
				if (i > 0) {
					if (i==1) { 
						pre = joins[i-1]; 
					}
					else { 
						pre += "_" + joins[i-1]; 
					}		
					alias = pre + "_" + joins[i];					
				}
				else {					
					alias = joins[i];
				}
				
				if (!listaAlias.contains(alias)) {
					
					From<?,?> join = from.join(joins[i]);
					join.alias(alias);
					
					from = join;
					
					listaAlias.add(alias);
				}
				else {
					from = CriteriaQueryUtils.getJoinByAlias(from, alias);
				}
			}
		}
	}
	
	public String processAttributePath(String atributo) {		
		
		String[] itens = atributo.split("\\.");
		
		String item = itens[0];
		
		if (itens.length > 1) {
			for (int i=1, t = itens.length -1; i < t; i++) {
				item += "_" + itens[i];
			}
			item+= "." + itens[itens.length -1]; 
		}
		
		return item;
	}
}
