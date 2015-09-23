package com.github.cg.example.jsf.controller.frm;
import javax.ejb.EJB;

import java.util.List;

import java.util.Collection;
import javax.annotation.PostConstruct;

import javax.inject.Inject;

import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.manager.ModelManager;

import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.manager.ManufacturerManager;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.controller.frm.FrmCar;

@Named
@HandlesError
public class FrmModel extends Frm<ModelManager,Model,Long> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManufacturerManager manufacturerManager;

	@Inject
	private FrmCar frmCar;
	private SubFrmInside<FrmModel,Model,FrmCar,Car> subFrmCars;

	public List<Manufacturer> onCompleteManufacturer(String suggest) {
		return this.manufacturerManager.retrieveBySuggestOrderByDescription(suggest);
	}

	@PostConstruct
	public void init() {

		this.subFrmCars = new SubFrmInside<FrmModel,Model,FrmCar,Car>(this, this.frmCar, "tbView:dtCars") {

			@Override
			public void connect(Car association, Model entity) {
				association.setModel(entity);
			}

			@Override
			public Collection<Car> getAssociations(Model entity) {
				return entity.getCars();
			}
		};
	}

	public SubFrmInside<FrmModel,Model,FrmCar,Car> getSubFrmCars() {
		return this.subFrmCars;
	}
}
