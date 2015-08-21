package com.github.cg.example.jsf.datamodel;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.IBaseEntity;



abstract public class PgLazyDataModelBaseEntity<E extends IBaseEntity<?>,D extends DAO<E,?>> extends PgLazyDataModelDAO<E,D> {
	
	private static final long serialVersionUID = 1L;
	
	public PgLazyDataModelBaseEntity(D dao) {
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