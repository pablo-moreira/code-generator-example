package com.github.cg.example.jsf.dao;
import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.Manufacturer;

public class ManufacturerDAO extends DAO<Manufacturer, Long> {

	public ManufacturerDAO() {
		super(Manufacturer.class);
	}
}
