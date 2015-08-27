package com.github.cg.example.jsf.controller;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.dao.ManufacturerDAO;
import com.github.cg.example.jsf.manager.ManufacturerManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;


@Named
@ConversationScoped
@HandlesError
public class ManufacturerEditCtrl extends AppConversationCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Manufacturer entity;
	
	@Inject
	private ManufacturerManager manufacturerManager;
	
	@Inject
	private ManufacturerDAO manufacturerDAO;
	
	public void start(ComponentSystemEvent evt) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			reset();			
		}
	}

	public void save() throws Exception {
		
		this.entity = this.manufacturerManager.save(getEntity());
		this.id = getEntity().getId();
		
		FacesMessageUtils.addInfo("The Manufacturer was save successfully!");
	}
	
	public void reset() {		
		if (getId() != null) { 
			entity = this.manufacturerDAO.retrieveById(getId());			
		}
		else {
			entity = new Manufacturer();
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Manufacturer getEntity() {
		return entity;
	}
}
