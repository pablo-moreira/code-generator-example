package com.github.cg.example.jsf.controller.frm;

import java.io.Serializable;
import java.util.List;

import org.hibernate.collection.internal.PersistentBag;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.AssociationOneToManyUtils;
import com.github.cg.example.jsf.primefaces.util.PrimeFacesUtil;


public abstract class FrmAssociationOneToMany<FE extends Frm<? extends Manager<? extends DAO<E,?>,E,?>,E,?>, E extends IBaseEntity<?>, 
											  FA extends Frm<? extends Manager<? extends DAO<A,?>,A,?>,A,?>, A extends IBaseEntity<?>> {
	
	private FE frmEntity;	
	private FA frmAssociation;	
//	private String association; /* TODO - implementar os metodos connect e getAssociations automatically */
	private String componentToUpdateId;
	private String dialogDeleteId;
	private A associationSelected;
			
	public FrmAssociationOneToMany(FE frmEntity, FA frmAssociation, String association, String componentToUpdateId) {
		
		String id = Integer.valueOf((int) (Math.random() * 10000)).toString();
		
		this.dialogDeleteId = "DlgAomDelete_" + id;
		
		this.frmEntity = frmEntity;
		this.frmAssociation = frmAssociation;
//		this.association = association;
		this.componentToUpdateId = componentToUpdateId;
		this.frmEntity.addFrmAssociationOneToMany(this);
	}
	
	protected void afterDelete(A association) throws Exception {}
	
	protected void beforeDelete(A association) throws Exception {}
	
	/**
	 * TODO - Refatorar para utilizar o atributo association e realizar a conexao atraves de reflections
	 * @param association
	 * @param entity
	 */
	abstract public void connect(A association, E entity);
		
	public void delete(A association) throws Exception {
		
		beforeDelete(association);

		E entity = getFrmEntity().getEntity();
		
		getFrmAssociation().getManager().delete(association);
		
		List<A> associations = getAssociations(entity);
		
		if (associations.contains(association)) {
			associations.remove(association);
		}
		
		/* Workaround */
		try {
			if (associations instanceof PersistentBag) {
					
				PersistentBag bag = (PersistentBag) associations;
	
				Serializable storedSnapshot = bag.getStoredSnapshot();
				
				if (storedSnapshot instanceof List) {
					
					@SuppressWarnings("unchecked")
					List<A> ss = (List<A>) storedSnapshot;
					
					if (ss.contains(association)) {
						ss.remove(association);
					}
				}			
			}
		}
		catch (Exception e) {}
				
		getFrmEntity().update(componentToUpdateId);
		
		afterDelete(association);
	}
	
	abstract public List<A> getAssociations(E entity);

	/**
	 * TODO - Refatorar para utilizar o atributo association e realizar a conexao atraves de reflections
	 * 
	 * @return
	 */
	public FA getFrmAssociation() {
		return frmAssociation;
	}

	public FE getFrmEntity() {
		return frmEntity;
	}

	public void save() throws Exception {	

		A association = getFrmAssociation().getEntity();
		E entity = getFrmEntity().getEntity();
		List<A> associations = getAssociations(entity);
		
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
	
	public void cancelDelete() {
		this.associationSelected = null;
		PrimeFacesUtil.hideDialog(getDialogDeleteId());
	}
	
	public void delete() throws Exception {
		
		beforeDelete();
		
		E entity = getFrmEntity().getEntity();
		
		if (this.associationSelected.getId() != null) {
			getFrmAssociation().getManager().delete(this.associationSelected);
		}
		
		List<A> associations = getAssociations(entity);
				
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

	protected void afterDelete() {}

	protected void beforeDelete() {}
}