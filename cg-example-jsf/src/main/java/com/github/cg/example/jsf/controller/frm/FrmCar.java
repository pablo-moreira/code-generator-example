package com.github.cg.example.jsf.controller.frm;
import java.util.List;
import javax.ejb.EJB;


import javax.inject.Named;

import com.github.cg.example.jsf.annotations.HandlesError;
import com.github.cg.example.core.model.Car;
import com.github.cg.example.jsf.manager.CarManager;

import com.github.cg.example.core.model.Model;
import com.github.cg.example.jsf.manager.ModelManager;

@Named
@HandlesError
public class FrmCar extends Frm<CarManager,Car,Long> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ModelManager modelManager;

	public List<Model> onCompleteModel(String suggest) {
		return this.modelManager.retrieveBySuggestOrderByDescription(suggest);
	}
}
