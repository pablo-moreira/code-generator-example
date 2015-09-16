package com.github.cg.example.jsf.controller.frm;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.manager.ManufacturerManager;
import com.github.cg.example.jsf.manager.ModelManager;
import com.github.cg.example.jsf.manager.CarManager;

@Named
@HandlesError
public class FrmModel extends Frm<ModelManager,Model,Long> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManufacturerManager manufacturerManager;
	private SubFrmInTable<FrmModel,Model,CarManager,Car> subFrmCars;

	public List<Manufacturer> onCompleteManufacturer(String suggest) {
		return this.manufacturerManager.retrieveBySuggestOrderByDescription(suggest);
	}

	@Inject
	public void initAssociations(CarManager carManager) throws Exception {
		
		this.subFrmCars = new SubFrmInTable<FrmModel,Model,CarManager,Car>(this, carManager, "tbView:dtCars") {

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
	
	public SubFrmInTable<FrmModel,Model,CarManager,Car> getSubFrmCars() {
		return subFrmCars;
	}
}