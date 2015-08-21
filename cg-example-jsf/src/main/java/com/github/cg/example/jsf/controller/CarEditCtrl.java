package com.github.cg.example.jsf.controller;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.atos.faces.view.interceptors.annotations.HandlesError;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.jsf.dao.ModelDAO;
import com.github.cg.example.jsf.manager.CarManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class CarEditCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
	
	@Inject
	private CarManager carManager;
	
	@Inject
	private CarDAO carDAO;
	
	@Inject
	private ModelDAO modelDAO;
	
	private Long id;
	
	private Car entity;

	@PostConstruct
    public void initConversation() {
        if (this.conversation.isTransient()) {
        	this.conversation.begin();
        }
    }
	
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
			entity = this.carDAO.retrieveById(Long.valueOf(getId()));			
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