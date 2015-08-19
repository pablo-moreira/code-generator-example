package com.github.cg.example.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class Pagination<T> {

	private EntityManager entityManager;
	private CriteriaQuery<T> resultsCriteria;
	private int firstResult;
	private int maxResult;
	
	public Pagination(EntityManager entityManager, CriteriaQuery<T> resultsCriteria) {
		this.entityManager = entityManager;
		this.resultsCriteria = resultsCriteria;
	}
		
	private EntityManager getEntityManager() {
		return entityManager;
	}

	public Long getCount() {
	    return CriteriaQueryUtils.count(getEntityManager(), resultsCriteria);
	}
	
	public List<T> getResultList() {	
		
		TypedQuery<T> query = getEntityManager().createQuery(resultsCriteria);
		
		query.setFirstResult(getFirstResult());
		query.setMaxResults(getMaxResult());
		
		return query.getResultList();
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
}