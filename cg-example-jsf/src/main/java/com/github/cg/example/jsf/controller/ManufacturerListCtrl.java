package com.github.cg.example.jsf.controller;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Manufacturer;
import com.github.cg.example.jsf.annotations.HandlesError;
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
		
	private LazyDataModelDefault<ManufacturerManager,Manufacturer,Long> dmManufacturers; 

	@PostConstruct
    public void init() {
		dmManufacturers = new LazyDataModelDefault<ManufacturerManager,Manufacturer,Long>(manufacturerManager);
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

	public LazyDataModelDefault<ManufacturerManager,Manufacturer,Long> getDmManufacturers() {
		return dmManufacturers;
	}
}
