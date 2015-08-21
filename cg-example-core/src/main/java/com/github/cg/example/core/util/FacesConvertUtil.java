package com.github.cg.example.core.util;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.cg.example.core.model.IBaseEntity;

@ManagedBean
@RequestScoped
public class FacesConvertUtil {

	@PersistenceContext
	private EntityManager entityManager;
	
	public IBaseEntity<?> findEntityById(IBaseEntity<?> entity) {
		return entityManager.find(entity.getClass(), entity.getId());
	}	
}