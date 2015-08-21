package com.github.cg.example.core.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.hibernate.internal.SessionImpl;

import com.github.cg.example.core.dao.query.QueryProcessor;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.HibernateUtils;
import com.github.cg.example.core.util.NumericoUtils;
import com.github.cg.example.core.util.StringUtils;

abstract public class DAO<E extends IBaseEntity<I>,I> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Class<E> entityClass;

	@PersistenceContext
	private EntityManager entityManager;
	
	private QueryProcessor<E> queryProcessor;
	
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
			throw new RuntimeException(MessageFormat.format("Could not determine the type of entity identifier {0}.", entityClass.getSimpleName()));
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
	
	protected List<E> retrieveBySuggestionOrderByDescription(String suggestion, String attributeId, String attributeDescription) {
		return retrieveBySuggestionOrderByDescription(suggestion, attributeId, attributeDescription, 200);
	}
	
	protected List<E> retrieveBySuggestionOrderByDescription(String suggestion, String attributeId, String attributeDescription, Integer maxResult) {
		
		CriteriaQuery<E> q = getCriteriaQueryRetrieveLightBySuggestionOrderByDescription(suggestion, attributeId, attributeDescription);
		
		Root<E> e = (Root<E>) CriteriaQueryUtils.getRootByAlias(q, "e");
		
		q.select(e);

		TypedQuery<E> query = getEntityManager().createQuery(q);
		
		query.setMaxResults(maxResult);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	protected CriteriaQuery<E> getCriteriaQueryRetrieveLightBySuggestionOrderByDescription(String suggestion, String attributeId, String attributeDescription, String ... attributes) {

		Set<String> attributesList = new HashSet<String>();
		attributesList.add(attributeId);
		attributesList.add(attributeDescription);
				
		if (attributes != null) {
			attributesList.addAll(Arrays.asList(attributes));	
		}		
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<E> q = cb.createQuery(getEntityClass());
		
		Root<E> e = q.from(getEntityClass());
		e.alias("e");
		
		getQueryProcessor().processAttributesJoins(e, attributesList);
		
		Path<?> pathAttributeId = CriteriaQueryUtils.getPathByStringPath(q, getQueryProcessor().processAttributePath(attributeId));
		Path<?> pathAttributeDescription = CriteriaQueryUtils.getPathByStringPath(q, getQueryProcessor().processAttributePath(attributeDescription));
		
		if (!StringUtils.isNullOrEmpty(suggestion)) {
			
			Predicate rotuloLike = cb.like(cb.lower((Expression<String>) pathAttributeDescription), "%" + suggestion.toLowerCase() + "%");

			// Verifica se a sugestao e um numero			
			if (NumericoUtils.isInteger(suggestion)) {				
				q.where(cb.or(
						cb.equal(pathAttributeId, suggestion),
						rotuloLike
				));
			}
			else {
				q.where(rotuloLike);
			}
		}
		
		q.orderBy(cb.asc(pathAttributeDescription));
		
		CompoundSelection<E> construct;
		
		if (attributes != null && attributes.length > 0) {
			
			List<Selection<?>> selections = new ArrayList<Selection<?>>();
			selections.add(pathAttributeId);
			selections.add(pathAttributeDescription);

			for (String attribute : attributes) {
				Path<?> path = CriteriaQueryUtils.getPathByStringPath(q, getQueryProcessor().processAttributePath(attribute));
				selections.add(path);
			}

			construct = cb.construct(getEntityClass(), selections.toArray(new Selection[]{}));
		}
		else {
			construct = cb.construct(getEntityClass(), pathAttributeId, pathAttributeDescription);
		}
		
		q.select(construct);

		return q;
	}

	protected QueryProcessor<E> getQueryProcessor() {
		if (this.queryProcessor == null) {
			this.queryProcessor = new QueryProcessor<E>();
		}	
		return this.queryProcessor;
	}
}	