package com.github.cg.example.jsf.dao;
import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.Model;

public class ModelDAO extends DAO<Model, Long> {

	public ModelDAO() {
		super(Model.class);
	}
}
