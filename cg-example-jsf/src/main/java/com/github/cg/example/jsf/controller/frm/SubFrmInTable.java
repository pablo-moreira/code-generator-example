package com.github.cg.example.jsf.controller.frm;

import java.util.Collection;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;


public abstract class SubFrmInTable<FE extends Frm<? extends Manager<? extends DAO<E,?>,E,?>,E,?>, E extends IBaseEntity<?>, 
											  MA extends Manager<? extends DAO<A,?>,A,?>, A extends IBaseEntity<?>> extends SubFrmBase<FE,E,MA,A> implements SubFrm {
	
	public SubFrmInTable(FE frmEntity, MA associationManager, String componentToUpdateId) {
		super(frmEntity, associationManager, componentToUpdateId);
	}
	
	public void startInsert() throws Exception {		
		startInsertAction();		
	}
	
	public A startInsertAction() throws Exception {	

		A association = getAssociationManager().newInstance();
		
		E entity = getFrmEntity().getEntity();
		Collection<A> associations = getAssociations(entity);	
		connect(association, entity);
		associations.add(association);
		
		getFrmEntity().update(getComponentToUpdateId());
		
		return association;
	}
}