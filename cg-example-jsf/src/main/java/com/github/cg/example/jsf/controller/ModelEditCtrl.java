package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.controller.frm.FrmModel;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class ModelEditCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Inject
	private FrmModel frm;
	
	public void start(ComponentSystemEvent evt) throws Exception {
		if (!getFrm().isInsertingOrUpdating()) {
			if (getId() != null) {
				getFrm().startUpdateById(getId());			
			}
			else {
				getFrm().startInsert();
			}
		}
	}

	public void save() throws Exception {
		
		getFrm().save();
				
		FacesMessageUtils.addInfo("The Model was save successfully!");
	}
	
	public void reset() throws Exception {
		getFrm().reset();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FrmModel getFrm() {
		return frm;
	}
}
