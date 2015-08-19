package com.github.cg.example.core.dao;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.internal.SessionImpl;

import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.HibernateUtils;

abstract public class DAO<E extends IBaseEntity<I>,I> {
		
	private Class<E> entityClass;

	@PersistenceContext
	private EntityManager entityManager;
	
	public DAO(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	protected Query createQuery(String jpql) {
		return entityManager.createQuery(jpql);
	}
	
	protected <T> TypedQuery<T> createQuery(CriteriaQuery<T> cq) {
		return entityManager.createQuery(cq);
	}
	
	protected Query createQuery(StringBuilder jpql) {
		return createQuery(jpql.toString());
	}
	
	protected TypedQuery<E> createTypedQuery(StringBuilder jpql) {
		return createTypedQuery(jpql.toString());
	}
	
	protected TypedQuery<E> createTypedQuery(String jpql) {
		return entityManager.createQuery(jpql, entityClass);
	}
	
	protected <T> TypedQuery<T> createTypedQuery(StringBuilder jpql, Class<T> entidadeClass) {
		return createTypedQuery(jpql.toString(), entidadeClass);
	}
	
	protected <T> TypedQuery<T> createTypedQuery(String jpql, Class<T> entidadeClass) {
		return entityManager.createQuery(jpql, entidadeClass);
	}
	

	public void delete(E e) {
		entityManager.remove(e);
	}
	
	protected SessionImpl getSession() {
		return (SessionImpl) entityManager.getDelegate();
	}
	
	public E merge(E e) {
		return entityManager.merge(e);		
	}
	
	public void persist(E e) {
		entityManager.persist(e);
	}

	public E retrieve(E e) {
		return entityManager.find(entityClass, e.getId());
	}
	
	public E retrieveById(E e) {
		return entityManager.find(entityClass, e.getId());
	}
	
	@SuppressWarnings("unchecked")
	public E retrieveByIdString(String id) {
		
		I objectId;
		
		try {
			Method methodGetId = entityClass.getMethod("getId", new Class<?>[]{});			
			Class<?> returnType = methodGetId.getReturnType();
			Method methodValueOf = returnType.getMethod("valueOf", new Class<?>[]{String.class});
			objectId = (I) methodValueOf.invoke(null, id);
		}		
		catch (Exception e) {
			throw new RuntimeException(MessageFormat.format("Não foi possível determinar o tipo do identificador da entidade {0}.", entityClass.getSimpleName()));
		}

		return retrieveById(objectId);
	}
	
	public E retrieveById(I id) {
		return entityManager.find(entityClass, id);
	}
	
	public E retrieveReferenceById(I id) {
		return entityManager.getReference(entityClass, id);
	}

	public List<E> retrieveAll(){
		CriteriaQuery<E> criteria = this.getEntityManager().getCriteriaBuilder().createQuery(entityClass);		
		return this.getEntityManager().createQuery(criteria.select(criteria.from(entityClass))).getResultList();		
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * Recupera a entidade com todas as suas associacoes eager,
	 * realizando um fetch all em todas as suas propriedades 
	 * @param entidade
	 * @return
	 */
	public E retrieveFullById(E entidade) {		
		return retrieveFullById(entidade.getId());
	}
	
	public E retrieveFullById(I id) {

		E entidade = retrieveById(id);
		
		HibernateUtils.initializeCollections(entidade);
		
		return entidade;
	}
	
	public CriteriaBuilder getCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}	
}	