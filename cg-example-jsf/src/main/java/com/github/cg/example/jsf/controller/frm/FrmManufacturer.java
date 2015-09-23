package com.github.cg.example.jsf.controller.frm;
import javax.ejb.EJB;


import java.util.Collection;
import javax.annotation.PostConstruct;


import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.manager.ManufacturerManager;


import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.manager.ModelManager;

@Named
@HandlesError
public class FrmManufacturer extends Frm<ManufacturerManager,Manufacturer,Long> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ModelManager modelManager;
	private SubFrmExternalPage<FrmManufacturer,Manufacturer,ModelManager,Model> subFrmModels;

	@PostConstruct
	public void init() {

		this.subFrmModels = new SubFrmExternalPage<FrmManufacturer,Manufacturer,ModelManager,Model>(this, this.modelManager, "tbView:dtModels") {

			@Override
			public void connect(Model association, Manufacturer entity) {
				association.setManufacturer(entity);
			}

			@Override
			public Collection<Model> getAssociations(Manufacturer entity) {
				return entity.getModels();
			}
		};
	}

	public SubFrmExternalPage<FrmManufacturer,Manufacturer,ModelManager,Model> getSubFrmModels() {	
		return this.subFrmModels;
	}
}
