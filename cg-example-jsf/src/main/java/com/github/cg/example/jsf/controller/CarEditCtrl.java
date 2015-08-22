package com.github.cg.example.jsf.controller;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.jsf.manager.CarManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.dao.ModelDAO;

@Named
@ConversationScoped
@HandlesError
public class CarEditCtrl extends AppConversationCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Car entity;
	
	@Inject
	private CarManager carManager;
	
	@Inject
	private CarDAO carDAO;

	@Inject
	private ModelDAO modelDAO;
	
	public void start(ComponentSystemEvent evt) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			reset();			
		}
	}

	public void save() throws Exception {
		
		this.entity = this.carManager.save(getEntity());
		this.id = getEntity().getId();
		
		FacesMessageUtils.addInfo("The Car was save successfully!");
	}
	
	public void reset() {		
		if (getId() != null) { 
			entity = this.carDAO.retrieveById(getId());			
		}
		else {
			entity = new Car();
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

	public List<Model> onCompleteModel(String suggest) {
		return this.modelDAO.retrieveBySuggestOrderByDescription(suggest);
	}
}
