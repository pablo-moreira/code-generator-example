package com.github.cg.example.jsf.datamodel;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.IBaseEntity;



abstract public class LazyDataModelBaseEntity<D extends DAO<E,I>,E extends IBaseEntity<I>,I> extends LazyDataModelDAO<D,E> {
	
	private static final long serialVersionUID = 1L;
	
	public LazyDataModelBaseEntity(D dao) {
		super(dao);
	}
	
	@Override
	public Object getRowKey(E rowData) {
		return rowData.getId();
	}
	
	@Override
	public E getRowData(String rowKey) {
		return getDAO().retrieveByIdString(rowKey);
	}
}