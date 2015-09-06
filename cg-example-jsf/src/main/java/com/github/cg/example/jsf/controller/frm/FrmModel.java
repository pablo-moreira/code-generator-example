package com.github.cg.example.jsf.controller.frm;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.manager.ManufacturerManager;
import com.github.cg.example.jsf.manager.ModelManager;

public class FrmModel extends Frm<ModelManager,Model,Long> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManufacturerManager manufacturerManager;
	
	private FrmAssociationOneToMany<FrmModel, Model, FrmCar, Car> associationCars;
		
	public List<Manufacturer> onCompleteManufacturer(String suggest) {
		return this.manufacturerManager.retrieveBySuggestOrderByDescription(suggest);
	}
	
	@Inject
	public void initAssociations(FrmCar frmCar) throws Exception {
		
		this.associationCars = new FrmAssociationOneToMany<FrmModel, Model, FrmCar, Car>(this, frmCar, "cars", "tbView:dtCars") {

			@Override
			public void connect(Car association, Model entity) {
				association.setModel(entity);
			}

			@Override
			public List<Car> getAssociations(Model entity) {
				return entity.getCars();
			}
		};
	}	
	
	public FrmAssociationOneToMany<FrmModel, Model, FrmCar, Car> getAssociationCars() {
		return associationCars;
	}
}
