package com.github.cg.example.jsf.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.github.cg.example.core.dao.query.PagedQueryResult;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

abstract public class LazyDataModelManager<M extends Manager<?,?,?>,E> extends LazyDataModel<E> {

	private static final long serialVersionUID = 1L;

	private M manager;
	
	public LazyDataModelManager(M manager) {
		this.manager = manager;
	}
	
	public M getManager() {
		return manager;
	}

	abstract protected PagedQueryResult<E> getPagedQueryResult(int first, int pageSize, String sort, SortOrder sortOrder, Map<String, Object> filters);
	
	protected void afterLoad(int first, int pageSize, String sort, SortOrder sortOrder, List<E> objs) {}
		
	@Override
	public List<E> load(int first, int pageSize, String sort, SortOrder sortOrder, Map<String,Object> filters) {
		try {
			
			PagedQueryResult<E> pagedQueryResult = getPagedQueryResult(first, pageSize, sort, sortOrder, filters);
			
			setRowCount(pagedQueryResult.getCount().intValue());
					
			List<E> objs = pagedQueryResult.getEntities(); 

			try {
				afterLoad(first, pageSize, sort, sortOrder, objs);
			}
			catch (Exception e) {
				FacesMessageUtils.addError(e.getMessage());
			}
			
			return objs;
		} 
		catch (Exception e) {
			FacesMessageUtils.addError(e.getMessage());
			return new ArrayList<E>();
		}
	}
}