package com.github.cg.example.core.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.dao.query.PagedQuery;
import com.github.cg.example.core.dao.query.PagedQueryResult;
import com.github.cg.example.core.model.IBaseEntity;

abstract public class Manager<D extends DAO<E,I>,E extends IBaseEntity<I>,I> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private D dao;
	
	public Manager() {}
	
	public D getDAO() {
		return dao;
	}
	
	public E update(E entidade) throws Exception {
		return getDAO().merge(entidade);
	}
				
	public void delete(E e) throws Exception {
		e = getDAO().retrieveReferenceById(e.getId());
		getDAO().delete(e);
	}
		
	public void insert(E entity) throws Exception {
		getDAO().persist(entity);
	}

	public E save(E entity) throws Exception {
						
		IBaseEntity<?> baseEntity = (IBaseEntity<?>) entity;

		if (baseEntity.getId() == null) {
			insert(entity);
		}
		else {
			entity = update(entity);
		}
		
		return entity;
	}

	public List<E> save(List<E> entity) throws Exception {
		
		List<E> saveEntities = new ArrayList<E>(); 
		
		for (E entidade : entity) {
			saveEntities.add(save(entidade));			
		}
		
		return saveEntities;
	}

	public E retrieveById(E e) {
		return dao.retrieveById(e);
	}

	public E retrieveByIdString(String id) {
		return dao.retrieveByIdString(id);
	}

	public E retrieveById(I id) {
		return dao.retrieveById(id);
	}

	public E retrieveReferenceById(I id) {
		return dao.retrieveReferenceById(id);
	}

	public List<E> retrieveAll() {
		return dao.retrieveAll();
	}

	public E retrieveFullById(E entidade) {
		return dao.retrieveFullById(entidade);
	}

	public E retrieveFullById(I id) {
		return dao.retrieveFullById(id);
	}

	public PagedQueryResult<E> retrieveEntitiesPaged(PagedQuery pagedQuery) {
		return dao.retrieveEntitiesPaged(pagedQuery);
	}
	
	public E newInstance() {
		try {
			return getDAO().getEntityClass().newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}