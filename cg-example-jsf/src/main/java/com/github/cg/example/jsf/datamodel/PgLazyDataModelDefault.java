//package com.github.cg.example.jsf.datamodel;
//
//import org.primefaces.model.SortOrder;
//
//import br.com.atos.core.dao.Pagination;
//
//import com.github.cg.example.core.dao.DAO;
//import com.github.cg.example.core.model.IBaseEntity;
//
//
//
//public class PgLazyDataModelDefault<E extends IBaseEntity<?>,D extends DAO<E,?>> extends PgLazyDataModelBaseEntity<E,D> {
//	
//	private static final long serialVersionUID = 1L;
//	
//	public PgLazyDataModelDefault(D dao) {
//		super(dao);
//	}
//
//	@Override
//	protected Pagination<E> getPagination(String sort, SortOrder sortOrder) {
//		//return getDAO().getPaginationRetrieveAll()
//	}
//	
//}