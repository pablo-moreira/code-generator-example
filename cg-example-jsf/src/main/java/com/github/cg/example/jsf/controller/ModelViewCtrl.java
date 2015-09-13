package com.github.cg.example.jsf.controller;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.manager.ModelManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class ModelViewCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;
		
	@EJB
	private ModelManager modelManager;
		
	private Long id;
	
	private Model entity;
	
	public void start(ComponentSystemEvent evt) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			if (getId() != null) { 
				entity = this.modelManager.retrieveFullById(getId());			
			}			
			else {
				FacesMessageUtils.addError("Model not found!");
			}
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Model getEntity() {
		return entity;
	}
}
