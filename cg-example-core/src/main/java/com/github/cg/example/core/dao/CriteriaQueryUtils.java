package com.github.cg.example.core.dao;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class CriteriaQueryUtils {
	
	private static int ALIAS_COUNT = 0;

	/**
	 * Copy Criteria without Selection
	 * @param from source Criteria
	 * @param to destination Criteria
	 */
	public static void  copyCriteriaNoSelectionNoFetchNoOrderBy(CriteriaQuery<?> from, CriteriaQuery<?> to) {

		// Copy Roots
		for (Root<?> root : from.getRoots()) {
			
			Root<?> dest = to.from(root.getJavaType());
			dest.alias(getOrCreateAlias(root));
			copyJoins(root, dest, false);
		}
		
		if (from.getGroupList() != null) {
			to.groupBy(from.getGroupList());
		}
		
		to.distinct(from.isDistinct());
		
		if (from.getGroupRestriction() != null) {
			to.having(from.getGroupRestriction());
		}
		
		if (from.getRestriction() != null) {
			to.where(from.getRestriction());
		}	
	}
		
	/**
	 * Copy Joins
	 * @param from source Join
	 * @param to destination Join
	 */
	public static void copyJoins(From<?, ?> from, From<?, ?> to, boolean copyFetch) {
		
		for (Join<?, ?> j : from.getJoins()) {			
			Join<?, ?> toJoin = to.join(j.getAttribute().getName(), j.getJoinType());
			toJoin.alias(getOrCreateAlias(j));						
			copyJoins(j, toJoin, copyFetch);
		}
		
		if (copyFetch) {
			for (Fetch<?, ?> f : from.getFetches()) {
				Fetch<?, ?> toFetch = to.fetch(f.getAttribute().getName());
				copyFetches(f, toFetch);			
			}
		}
	}
	
	/**
	 * Copy Fetches
	 * @param from source Fetch
	 * @param to dest Fetch
	 */
	public static void copyFetches(Fetch<?, ?> from, Fetch<?, ?> to) {
		for (Fetch<?, ?> f : from.getFetches()) {
			Fetch<?, ?> toFetch = to.fetch(f.getAttribute().getName());
			// recursively copy fetches
			copyFetches(f, toFetch);
		}
	}
	

	/**
	 * Result count from a CriteriaQuery
	 * @param em Entity Manager
	 * @param criteria Criteria Query to count results
	 * @return row count
	 */
	public static <T> Long count(EntityManager em, CriteriaQuery<T> criteria) {
	    return em.createQuery(countCriteria(em, criteria)).getSingleResult();
	}
	
	/**
	 * Create a row count CriteriaQuery from a CriteriaQuery
	 * @param em entity manager
	 * @param criteria source criteria
	 * @return row coutnt CriteriaQuery
	 */
	public static <T> CriteriaQuery<Long> countCriteria(EntityManager em, CriteriaQuery<T> criteria) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		copyCriteriaNoSelectionNoFetchNoOrderBy(criteria, countCriteria);
		countCriteria.select(builder.count(findRoot(countCriteria, criteria.getResultType())));
		
		Root<T> root = findRoot(countCriteria, criteria.getResultType());
		
		countCriteria.select(criteria.isDistinct() ? builder.countDistinct(root) : builder.count(root));
		
		return countCriteria;
	}
	
	/**
	 * Find the Root with type class on CriteriaQuery Root Set
	 * @param <T> root type
	 * @param query criteria query
	 * @param clazz root type
	 * @return Root<T> of null if none
	 */
	@SuppressWarnings("unchecked")
	public static  <T> Root<T> findRoot(CriteriaQuery<?> query, Class<T> clazz) {

		for (Root<?> r : query.getRoots()) {
			if (clazz.equals(r.getJavaType())) {
				return (Root<T>) r.as(clazz);
			}
		}
		
		return (Root<T>) query.getRoots().iterator().next();
	}
	
	@SuppressWarnings("unchecked")
	public static <T,E> From<T,E> getFromByAlias(CriteriaQuery<?> cq, String alias) {
		
		Set<Root<?>> roots = cq.getRoots();
		
		for (Root<?> root : roots) {

			From<?,?> found = getFromByAlias(root, alias);
			
			if (found != null) {
				return (From<T,E>) found;
			}
		}

		return null;		
	}
	
	private static From<?,?> getFromByAlias(From<?,?> from, String alias) {
		
		if (alias.equals(from.getAlias())) {
			return from;
		}

		for (Join<?,?> join : from.getJoins()) {
						
			From<?,?> found = getFromByAlias(join, alias);
			
			if (found != null) {
				return found;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Path<T> getPathByStringPath(CriteriaQuery<?> cq, String stringPath) {
		
		String[] pathItens = stringPath.split("\\.");
		
		Path<Object> path = null;
		
		if (pathItens.length > 1) {
			
			From<?, ?> from = getFromByAlias(cq, pathItens[0]);

			if (from == null) {
				Root<?> root = cq.getRoots().iterator().next();
				path = root.get(pathItens[0]);
			}
			
			for (int i=1; i < pathItens.length; i++) {
				if (path == null) {
					path = from.get(pathItens[i]);
				}
				else {
					path = path.get(pathItens[i]);
				}
			}
		}
		else {
			Root<?> root = cq.getRoots().iterator().next();
			path = root.get(pathItens[0]);
		}
		
		return (Path<T>) path;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Root<T> getRootByAlias(CriteriaQuery<T> q, String alias) {

		Set<Root<?>> roots = q.getRoots();

		for (Root<?> root : roots) {
			if (alias.equals(root.getAlias())) {
				return (Root<T>) root;			
			}
		}

		return null;
	}
		
	public static Join<?,?> getJoinByAlias(From<?,?> root, String alias) {
		for (Join<?,?> join : root.getJoins()) {
			if (alias.equals(join.getAlias())) {
				return join;
			}
		}
		return null;
	}

	public static Join<?,?> getJoinByAlias(CriteriaQuery<?> q, String alias) {
		
		Set<Root<?>> roots = q.getRoots();
		
		for (Root<?> root : roots) {
			
			Join<?, ?> join = getJoinByAlias(root, alias);
			
			if (join != null) {
				return join;
			}			
		}

		return null;
	}
	
	/**
	 * Gets The result alias, if none set a default one and return it
	 * @param criteria criteria
	 * @return root alias or generated one
	 */
	public static synchronized <T> String getOrCreateAlias(Selection<T> selection) {

		// reset alias count
		if (ALIAS_COUNT > 1000)
			ALIAS_COUNT = 0;
			
		String alias = selection.getAlias();
		
		if (alias == null) {
			alias = "alias__" + ALIAS_COUNT++;
			selection.alias(alias);
		}

		return alias;		
	}

	public static boolean isDeclaredWhere(CriteriaQuery<?> cq) {
	
		Predicate predicate = cq.getRestriction();

		return predicate != null && predicate.getExpressions() != null && !predicate.getExpressions().isEmpty();
	}
}