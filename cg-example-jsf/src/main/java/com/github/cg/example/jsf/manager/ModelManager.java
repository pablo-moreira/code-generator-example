package com.github.cg.example.jsf.manager;
import java.util.List;

import javax.ejb.Stateless;

import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.dao.ModelDAO;
import com.github.cg.example.core.model.Model;

@Stateless
public class ModelManager extends Manager<ModelDAO, Model, Long> {

	private static final long serialVersionUID = 1L;

	public List<Model> retrieveBySuggestOrderByDescription(String suggest) {
		return getDAO().retrieveBySuggestOrderByDescription(suggest);
	}
}
