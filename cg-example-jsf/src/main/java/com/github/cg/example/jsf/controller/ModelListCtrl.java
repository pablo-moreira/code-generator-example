package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.dao.ModelDAO;
import com.github.cg.example.jsf.datamodel.LazyDataModelDefault;
import com.github.cg.example.jsf.manager.ModelManager;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class ModelListCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;

	@Inject
	private ModelManager modelManager;
	
	private Model modelSelected;
		
	private LazyDataModelDefault<ModelDAO, Model, Long> dmModels; 

	@Inject
    public void init(ModelDAO modelDAO) {
		dmModels = new LazyDataModelDefault<ModelDAO, Model, Long>(modelDAO);
    }
    
	public void delete() throws Exception {
		
		this.modelManager.delete(this.modelSelected);
		
		FacesMessageUtils.addInfo("The Model was deleted successfully!");
	}
	
	public Model getModelSelected() {
		return modelSelected;
	}

	public void setModelSelected(Model modelSelected) {
		this.modelSelected = modelSelected;
	}

	public LazyDataModelDefault<ModelDAO,Model,Long> getDmModels() {
		return dmModels;
	}
}
