package com.github.cg.example.jsf.controller.frm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.collection.internal.PersistentBag;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.AssociationOneToManyUtils;
import com.github.cg.example.jsf.primefaces.util.PrimeFacesUtil;


public abstract class SubFrmInside<FE extends Frm<? extends Manager<? extends DAO<E,?>,E,?>,E,?>, E extends IBaseEntity<?>, 
											  FA extends Frm<? extends Manager<? extends DAO<A,?>,A,?>,A,?>, A extends IBaseEntity<?>> implements SubFrm {
	
	private FE frmEntity;	
	private FA frmAssociation;	
	private String componentToUpdateId;
	private String dialogDeleteId;
	private A associationSelected;
			
	public SubFrmInside(FE frmEntity, FA frmAssociation, String componentToUpdateId) {
		
		String id = Integer.valueOf((int) (Math.random() * 10000)).toString();
		
		this.dialogDeleteId = "DlgSubFrmDelete_" + id;
		
		this.frmEntity = frmEntity;
		this.frmAssociation = frmAssociation;
		this.componentToUpdateId = componentToUpdateId;
		this.frmEntity.addFrmAssociationOneToMany(this);
	}
	
	protected void afterDelete() {}

	protected void beforeDelete() {}
	
	abstract public void connect(A association, E entity);
		
	abstract public Collection<A> getAssociations(E entity);

	public FA getFrmAssociation() {
		return frmAssociation;
	}

	public FE getFrmEntity() {
		return frmEntity;
	}

	public void save() throws Exception {	

		A association = getFrmAssociation().getEntity();
		E entity = getFrmEntity().getEntity();
		Collection<A> associations = getAssociations(entity);
		
		if (association.getId() == null) {
			connect(association, entity);
		}

		if (entity.getId() == null) {

			if (!associations.contains(association)) {
				associations.add(association);
			}		
			
			getFrmEntity().update(componentToUpdateId);
			
			startInsert();
		}
		else {
			// Se der erro no salvar retorna nulo
			association = getFrmAssociation().getManager().save(association);

			// Se nao der erro
			if (association != null) {

				AssociationOneToManyUtils.updateAssociations(association, associations);
								
				getFrmEntity().update(componentToUpdateId);
				
				startInsert();
			}
		}		
	}
	
	public void startInsert() throws Exception {
		
		A association = getFrmAssociation().getManager().newInstance();
		E entity = getFrmEntity().getEntity();
		
		connect(association, entity);
		
		getFrmAssociation().startInsert(association);
		
		getFrmAssociation().setEntityAssociated(entity);
	}
	
	public void startInsertByCopy(A association) throws Exception {
		
		E entity = getFrmEntity().getEntity();
		
		getFrmAssociation().startInsertByCopy(association);
		
		getFrmAssociation().setEntityAssociated(entity);
	}

	public void startUpdate(A association) throws Exception {
		
		getFrmAssociation().startUpdate(association);
		
		getFrmAssociation().setEntityAssociated(getFrmEntity().getEntity());
	}

	@Override
	public String getDialogDeleteId() {
		return dialogDeleteId;
	}
	
	public void startDelete(A associationSelected) throws Exception {
		this.associationSelected = associationSelected;
		PrimeFacesUtil.showDialog(getDialogDeleteId());
	}
	
	public void cancel() throws Exception {
		
		startInsert();
		
		getFrmAssociation().refresh();
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
			getFrmAssociation().getManager().delete(this.associationSelected);
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