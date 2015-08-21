package com.github.cg.example.jsf.controller;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.jsf.manager.CarManager;

import br.com.atos.faces.view.FacesMessageUtil;
import br.com.atos.faces.view.interceptors.annotations.HandlesError;

@Named
@ConversationScoped
@HandlesError
public class CarListCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Conversation conversation;
	
	@Inject
	private CarManager carManager;
	
	@Inject
	private CarDAO carDAO;
	
	private Car carSelected;
	
	private List<Car> dmCars;
	
	@PostConstruct
	public void init() {
				
		initConversation();
		
		this.dmCars = this.carDAO.retrieveAll();
	}
	
    public void initConversation() {
        if (this.conversation.isTransient()) {
        	this.conversation.begin();
        }
    }
    
	public void delete() throws Exception {
		
		this.carManager.delete(this.carSelected);
		
		FacesMessageUtil.addInfo("The Car was deleted successfully!");
	}
	
	public Car getCarSelected() {
		return carSelected;
	}

	public void setCarSelected(Car carSelected) {
		this.carSelected = carSelected;
	}

	public List<Car> getDmCars() {
		return dmCars;
	}
}