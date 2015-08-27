package com.github.cg.example.jsf.controller;
import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.dao.ModelDAO;
import com.github.cg.example.jsf.manager.ModelManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.dao.ManufacturerDAO;

@Named
@ConversationScoped
@HandlesError
public class ModelEditCtrl extends AppConversationCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Model entity;
	
	@Inject
	private ModelManager modelManager;
	
	@Inject
	private ModelDAO modelDAO;

	@Inject
	private ManufacturerDAO manufacturerDAO;
	
	public void start(ComponentSystemEvent evt) {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			reset();			
		}
	}

	public void save() throws Exception {
		
		this.entity = this.modelManager.save(getEntity());
		this.id = getEntity().getId();
		
		FacesMessageUtils.addInfo("The ${entity.label} was save successfully!");
	}
	
	public void reset() {		
		if (getId() != null) { 
			entity = this.modelDAO.retrieveById(getId());			
		}
		else {
			entity = new Model();
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

	public List<Manufacturer> onCompleteModel(String suggest) {
		return this.manufacturerDAO.retrieveBySuggestOrderByDescription(suggest);
	}
}
