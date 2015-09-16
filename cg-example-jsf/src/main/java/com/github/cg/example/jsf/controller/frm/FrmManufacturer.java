package com.github.cg.example.jsf.controller.frm;

import java.util.Collection;
import javax.inject.Inject;

import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.manager.ManufacturerManager;


import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.controller.frm.FrmModel;

@Named
@HandlesError
public class FrmManufacturer extends Frm<ManufacturerManager,Manufacturer,Long> {

	private static final long serialVersionUID = 1L;
	private SubFrmInside<FrmManufacturer,Manufacturer,FrmModel,Model> associationModels;

	@Inject
	public void initAssociations(FrmModel frmModel) throws Exception {
		
		this.associationModels = new SubFrmInside<FrmManufacturer,Manufacturer,FrmModel,Model>(this, frmModel, "tbView:dtModels") {

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
	
	public SubFrmInside<FrmManufacturer,Manufacturer,FrmModel,Model> getAssociationModels() {
		return associationModels;
	}
}
