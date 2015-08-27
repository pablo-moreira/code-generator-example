package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.dao.ManufacturerDAO;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class ManufacturerViewCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;
		
	@Inject
	private ManufacturerDAO manufacturerDAO;
		
	private Long id;
	
	private Manufacturer entity;
	
	public void start(ComponentSystemEvent evt) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			if (getId() != null) { 
				entity = this.manufacturerDAO.retrieveById(getId());			
			}			
			else {
				FacesMessageUtils.addError("Manufacturer not found!");
			}
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
