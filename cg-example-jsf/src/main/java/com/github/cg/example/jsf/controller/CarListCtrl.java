package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.jsf.datamodel.LazyDataModelDefault;
import com.github.cg.example.jsf.manager.CarManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class CarListCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;

	@Inject
	private CarManager carManager;
	
	private Car carSelected;
		
	private LazyDataModelDefault<CarDAO, Car, Long> dmCars; 

	@Inject
    public void init(CarDAO carDAO) {
		dmCars = new LazyDataModelDefault<CarDAO, Car, Long>(carDAO);
    }
    
	public void delete() throws Exception {
		
		this.carManager.delete(this.carSelected);
		
		FacesMessageUtils.addInfo("The Car was deleted successfully!");
	}
	
	public Car getCarSelected() {
		return carSelected;
	}

	public void setCarSelected(Car carSelected) {
		this.carSelected = carSelected;
	}

	public LazyDataModelDefault<CarDAO,Car,Long> getDmCars() {
		return dmCars;
	}
}