package com.github.cg.example.jsf.datamodel;

import java.util.Map;

import org.primefaces.model.SortOrder;

import com.github.cg.example.core.dao.query.Expression;
import com.github.cg.example.core.dao.query.PagedQuery;
import com.github.cg.example.core.dao.query.PagedQueryResult;
import com.github.cg.example.core.dao.query.Sort;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.StringUtils;

public class LazyDataModelDefault<M extends Manager<?,E,I>,E extends IBaseEntity<I>,I> extends LazyDataModelBaseEntity<M,E,I> {

	private static final long serialVersionUID = 1L;

	public LazyDataModelDefault(M manager) {
		super(manager);
	}
	
	@Override
	protected PagedQueryResult<E> getPagedQueryResult(int firstResult, int maxResult, String sort, SortOrder sortOrder, Map<String, Object> filters) {

		PagedQuery pagedQuery = new PagedQuery();
		pagedQuery.setFirstResult(firstResult);
		pagedQuery.setMaxResult(maxResult);
		pagedQuery.setWhere(Expression.and());
		
		if (!StringUtils.isNullOrEmpty(sort)) {
			pagedQuery.addSort(SortOrder.ASCENDING.equals(sortOrder) ? Sort.asc(sort) : Sort.desc(sort));
		}
		
		return getManager().retrieveEntitiesPaged(pagedQuery);
	}
}