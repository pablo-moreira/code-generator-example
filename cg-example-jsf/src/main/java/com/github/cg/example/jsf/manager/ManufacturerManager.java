package com.github.cg.example.jsf.manager;
import javax.ejb.Stateless;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.dao.ManufacturerDAO;
import com.github.cg.example.core.model.Manufacturer;

@Stateless
public class ManufacturerManager extends Manager<ManufacturerDAO, Manufacturer, Long> {

	private static final long serialVersionUID = 1L;
	
}
