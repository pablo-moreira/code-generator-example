package com.github.cg.example.jsf.manager;
import javax.inject.Inject;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.dao.ManufacturerDAO;
import com.github.cg.example.core.model.Manufacturer;

public class ManufacturerManager extends Manager<ManufacturerDAO, Manufacturer, Long> {

	@Inject
	public ManufacturerManager(ManufacturerDAO dao) {
		super(dao);
	}
}
