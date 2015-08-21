package com.github.cg.example.jsf.dao;
import java.util.List;
import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.Manufacturer;

public class ManufacturerDAO extends DAO<Manufacturer, Long> {

	private static final long serialVersionUID = 1L;

	public ManufacturerDAO() {
		super(Manufacturer.class);
	}

	public List<Manufacturer> retrieveBySuggestOrderByDescription(String suggest) {
		return this.retrieveBySuggestionOrderByDescription(suggest, "id", "${entity.attributeDescription.name}");
	}
}
