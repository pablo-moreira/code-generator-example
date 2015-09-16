package com.github.cg.example.jsf.controller.frm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.model.IBaseEntityCloneable;
import com.github.cg.example.jsf.primefaces.util.PrimeFacesUtil;


abstract public class Frm<M extends Manager<? extends DAO<E,I>,E,I>,E extends IBaseEntity<I>,I> implements IFrm<M,E,I> {

	private static final long serialVersionUID = 1L;

	public enum FrmState { INSERTING, UPDATING, INACTIVE }

	@EJB
	private M manager;	
	private FrmState state;
	protected E entity;
	private IBaseEntity<?> entityAssociated;
	private List<SubFrmInside<?,E,?,?>> frmAssociationsOneToMany = new ArrayList<SubFrmInside<?,E,?,?>>();
	private String frmClientId; 

	protected E newInstance() {
		return getManager().newInstance();
	}
	
	@Override
	public E getEntity() {
		return entity;
	}
	
	protected void afterSave() throws Exception {}	
	protected void beforeSave() throws Exception {}

	protected E saving() throws Exception {
		return getManager().save(getEntity());
	}
	
	public void saveAction() throws Exception {
		save();
	}
	
	public E save() throws Exception {
		
		beforeSave();
		
		this.entity = saving();
		
		afterSave();
		
		return this.entity;
	}
	
	protected void starting() throws Exception {}
	protected void startingUpdate() throws Exception {}	
	protected void startingInsert() throws Exception {}
		
	public void startInsert() throws Exception {
		startInsert(newInstance());
	}
	
	public void startInsert(E entity) throws Exception {
		
		this.entity = entity;
		
		state = FrmState.INSERTING;
		
		starting();
		
		startingInsert();
		
		for (SubFrmInside<?,?,?,?> frmAssociationOneToMany : frmAssociationsOneToMany) {
			frmAssociationOneToMany.startInsert();
		}
		
		refresh();
	}
	
	public void refresh() {
		// Check if onPreRenderView was executed, case not dont do anything because is load by request
		if (frmClientId != null) {
			PrimeFacesUtil.update(getFrmContentClientId());
		}
	}

	public void startUpdateById(I entityId) throws Exception {
		
		if (entityId != null) {
			entity = getManager().retrieveFullById(entityId);
		}
		
		state = FrmState.UPDATING;
		
		starting();
		
		startingUpdate();
		
		for (SubFrmInside<?,?,?,?> frmAssociationOneToMany : frmAssociationsOneToMany) {
			frmAssociationOneToMany.startInsert();
		}
		
		refresh();
	}
	
	public void startUpdate(E entity) throws Exception {
				
		this.entity = entity;
				
		startUpdateById(entity.getId());
	}
	
	@SuppressWarnings("unchecked")
	public void startInsertByCopy(E entity) throws Exception {
				
		// Verifica se o objeto nao e transiente
		if (entity.getId() != null) {
			entity = getManager().retrieveFullById(entity);			
		}
		
		if (entity instanceof IBaseEntityCloneable) {
			startInsert((E) ((IBaseEntityCloneable) entity).copy());
		}
		else {
			throw new Exception("Frm.startInsertByCopy() - A entidade " + entity.getClass().getSimpleName() + " não implementa a interface: " + IBaseEntityCloneable.class.getSimpleName());
		}
	}
	
	public void cancel() {		
		entity = null;
		state = FrmState.INACTIVE;
		canceling();
		//PrimeFacesUtil.hideDialog(getDialogWidgetVar());		
	}
			
	protected void canceling() {}
	
	public boolean isInsertingOrUpdating() {
		return isInserting() || isUpdating();
	}
	
	public boolean isUpdating() {
		return FrmState.UPDATING.equals(state);
	}

	public boolean isInserting() {
		return FrmState.INSERTING.equals(state);
	}

	public M getManager() {
		return manager;
	}
	
	public void onPreRenderComponent(ComponentSystemEvent event) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			UIComponent component = event.getComponent().getParent(); 
			this.frmClientId = component.getClientId();
		}
	}

	public IBaseEntity<?> getEntityAssociated() {
		return entityAssociated;
	}

	public void setEntityAssociated(IBaseEntity<?> entityAssociated) {
		this.entityAssociated = entityAssociated;
	}

	public String getFrmClientId() {
		return this.frmClientId;
	}

	private String getComponentClientId(String componentId) {
		
		UIComponent frm = FacesContext.getCurrentInstance().getViewRoot().findComponent(this.frmClientId);		
		
		UIComponent component = frm.findComponent(componentId);
		
		if (component == null) { 
			throw new RuntimeException("The component: " + componentId + " not found!");
		}
		else {
			return component.getClientId(FacesContext.getCurrentInstance());
		}
	}
	
	public String getFrmContentClientId() {
		return getComponentClientId("content");
	}
	
	public void update(String componentId) {		
		PrimeFacesUtil.update(getComponentClientId(componentId));
	}
	
	public boolean verifyEntityAssociated(Object object) {
		return getEntityAssociated() != null && getEntityAssociated().equals(object);
	}
	
	public void addFrmAssociationOneToMany(SubFrmInside<?,E,?,?> frm) {
		this.frmAssociationsOneToMany.add(frm);
	}
}