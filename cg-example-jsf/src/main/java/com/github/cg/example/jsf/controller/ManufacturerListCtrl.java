package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.dao.ManufacturerDAO;
import com.github.cg.example.jsf.datamodel.LazyDataModelDefault;
import com.github.cg.example.jsf.manager.ManufacturerManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class ManufacturerListCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;

	@Inject
	private ManufacturerManager manufacturerManager;
	
	private Manufacturer manufacturerSelected;
		
	private LazyDataModelDefault<ManufacturerDAO, Manufacturer, Long> dmManufacturers; 

	@Inject
    public void init(ManufacturerDAO manufacturerDAO) {
		dmManufacturers = new LazyDataModelDefault<ManufacturerDAO, Manufacturer, Long>(manufacturerDAO);
    }
    
	public void delete() throws Exception {
		
		this.manufacturerManager.delete(this.manufacturerSelected);
		
		FacesMessageUtils.addInfo("The Manufacturer was deleted successfully!");
	}
	
	public Manufacturer getManufacturerSelected() {
		return manufacturerSelected;
	}

	public void setManufacturerSelected(Manufacturer manufacturerSelected) {
		this.manufacturerSelected = manufacturerSelected;
	}

	public LazyDataModelDefault<ManufacturerDAO,Manufacturer,Long> getDmManufacturers() {
		return dmManufacturers;
	}
}
