package com.github.cg.example.jsf.controller;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.jsf.controller.frm.FrmCar;
import com.github.cg.example.jsf.util.FacesMessageUtils;

@Named
@ConversationScoped
@HandlesError
public class CarEditCtrl extends AppConversationCtrl {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Inject
	private FrmCar frm;
	
	public void start(ComponentSystemEvent evt) throws Exception {
		if (!FacesContext.getCurrentInstance().isPostback() && !FacesContext.getCurrentInstance().isValidationFailed()) {
			reset();			
		}
	}

	public void save() throws Exception {
		
		Car entity = getFrm().save();
		this.id = entity.getId();
		
		FacesMessageUtils.addInfo("The Car was save successfully!");
	}
	
	public void reset() throws Exception {		
		if (getId() != null) {
			getFrm().startUpdateById(getId());			
		}
		else {
			getFrm().startInsert();
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FrmCar getFrm() {
		return frm;
	}
}
