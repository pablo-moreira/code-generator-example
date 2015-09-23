package com.github.cg.example.jsf.controller.frm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.collection.internal.PersistentBag;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.jsf.primefaces.util.PrimeFacesUtil;


public abstract class SubFrmBase<FE extends Frm<? extends Manager<? extends DAO<E,?>,E,?>,E,?>, E extends IBaseEntity<?>, 
											  MA extends Manager<? extends DAO<A,?>,A,?>, A extends IBaseEntity<?>> implements SubFrm {
	
	private FE frmEntity;	
	private MA associationManager;	
	private String componentToUpdateId;
	private String dialogDeleteId;
	private A associationSelected;
			
	public SubFrmBase(FE frmEntity, MA associationManager, String componentToUpdateId) {
		
		String id = Integer.valueOf((int) (Math.random() * 10000)).toString();
		
		this.dialogDeleteId = "DlgSubFrmDelete_" + id;
		
		this.frmEntity = frmEntity;
		this.associationManager = associationManager;
		this.componentToUpdateId = componentToUpdateId;
	}
	
	protected void afterDelete() {}

	protected void beforeDelete() {}
		
	abstract public void connect(A association, E entity);
	
	abstract public Collection<A> getAssociations(E entity);

	public FE getFrmEntity() {
		return frmEntity;
	}
		
	@Override
	public String getDialogDeleteId() {
		return dialogDeleteId;
	}
	
	public void startDelete(A associationSelected) throws Exception {
		this.associationSelected = associationSelected;
		PrimeFacesUtil.showDialog(getDialogDeleteId());
	}
	
	@Override
	public void cancelDelete() {
		this.associationSelected = null;
		PrimeFacesUtil.hideDialog(getDialogDeleteId());
	}
	
	@Override
	public void delete() throws Exception {
		
		beforeDelete();
		
		E entity = getFrmEntity().getEntity();
		
		if (this.associationSelected.getId() != null) {
			this.associationManager.delete(this.associationSelected);
		}
		
		Collection<A> associations = getAssociations(entity);
				
		if (associations.contains(this.associationSelected)) {
			associations.remove(this.associationSelected);
		}
		
		/* Workaround */
		try {
			if (associations instanceof PersistentBag) {
					
				PersistentBag bag = (PersistentBag) associations;
	
				Serializable storedSnapshot = bag.getStoredSnapshot();
				
				if (storedSnapshot instanceof List) {
					
					@SuppressWarnings("unchecked")
					List<A> ss = (List<A>) storedSnapshot;
					
					if (ss.contains(this.associationSelected)) {
						ss.remove(this.associationSelected);
					}
				}			
			}
		}
		catch (Exception e) {}
		
		cancelDelete();
		
		getFrmEntity().update(componentToUpdateId);
		
		afterDelete();
	}
}