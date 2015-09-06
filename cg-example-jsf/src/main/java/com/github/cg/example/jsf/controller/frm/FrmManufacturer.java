package com.github.cg.example.jsf.controller.frm;

import java.util.List;

import javax.ejb.EJB;

import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.manager.ManufacturerManager;
import com.github.cg.example.jsf.manager.ModelManager;

public class FrmManufacturer extends Frm<ModelManager,Model,Long> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManufacturerManager manufacturerManager;
	
	public List<Manufacturer> onCompleteManufacturer(String suggest) {
		return this.manufacturerManager.retrieveBySuggestOrderByDescription(suggest);
	}
	
	@Override
	public void update(String componentId) {
		
	}
}
