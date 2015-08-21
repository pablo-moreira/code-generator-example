package com.github.cg.example.jsf.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.github.cg.example.core.dao.DAO;

import br.com.atos.core.dao.Pagination;
import br.com.atos.faces.view.FacesMessageUtil;

abstract public class PgLazyDataModelDAO<E,D extends DAO<?,?>> extends LazyDataModel<E> {

	private static final long serialVersionUID = 1L;

	protected final D dao;
	
	public PgLazyDataModelDAO(D dao) {
		this.dao = dao;
	}
	
	public D getDAO() {
		return dao;
	}

	abstract protected Pagination<E> getPagination(String sort, SortOrder sortOrder);
	
	protected void afterLoadResultList(int first, int pageSize, String sort, SortOrder sortOrder, List<E> objs) {}
		
	@Override
	public List<E> load(int first, int pageSize, String sort, SortOrder sortOrder, Map<String,Object> filters) {
		try {
			Pagination<E> pagination = getPagination(sort, sortOrder);
			
			Long count = pagination.getCount();

			setRowCount(count.intValue());
					
			List<E> objs;
			
			if (count > 0) {
				
				pagination.setFirstResult(first);
				pagination.setMaxResult(pageSize);
				
				objs = pagination.getResultList();
			}
			else {
				objs = new ArrayList<E>();
			}

			try {
				afterLoadResultList(first, pageSize, sort, sortOrder, objs);
			}
			catch (Exception e) {
				FacesMessageUtil.addError(e.getMessage());
			}
			
			return objs;
		} 
		catch (Exception e) {
			FacesMessageUtil.addError(e.getMessage());
			return new ArrayList<E>();
		}
	}
}