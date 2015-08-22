package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class CarViewCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;
		
	@Inject
	private CarDAO carDAO;
		
	private Long id;
	
	private Car entity;
	
	public void start(ComponentSystemEvent evt) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			if (getId() != null) { 
				entity = this.carDAO.retrieveById(getId());			
			}			
			else {
				FacesMessageUtils.addError("Car not found!");
			}
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Car getEntity() {
		return entity;
	}
}