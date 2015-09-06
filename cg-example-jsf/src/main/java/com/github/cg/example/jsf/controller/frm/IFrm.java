package com.github.cg.example.jsf.controller.frm;

import java.io.Serializable;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;


public interface IFrm<M extends Manager<? extends DAO<E,I>,E,I>, E extends IBaseEntity<I>, I> extends Serializable {
	
	public E getEntity();
	public M getManager();
	
	public void update(String componentId);
}
