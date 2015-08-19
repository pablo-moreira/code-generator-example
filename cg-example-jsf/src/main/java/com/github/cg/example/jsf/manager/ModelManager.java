package com.github.cg.example.jsf.manager;
import javax.inject.Inject;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.dao.ModelDAO;
import com.github.cg.example.core.model.Model;

public class ModelManager extends Manager<ModelDAO, Model, Long> {

	@Inject
	public ModelManager(ModelDAO dao) {
		super(dao);
	}
}
