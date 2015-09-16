package com.github.cg.example.jsf.datamodel;

import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;



abstract public class LazyDataModelBaseEntity<M extends Manager<?,E,I>,E extends IBaseEntity<I>,I> extends LazyDataModelManager<M,E> {
	
	private static final long serialVersionUID = 1L;
	
	public LazyDataModelBaseEntity(M manager) {
		super(manager);
	}
	
	@Override
	public Object getRowKey(E rowData) {
		return rowData.getId();
	}
	
	@Override
	public E getRowData(String rowKey) {
		return getManager().retrieveByIdString(rowKey);
	}
}