package com.github.cg.example.jsf.dao;
import java.util.List;
import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.Model;

public class ModelDAO extends DAO<Model, Long> {

	private static final long serialVersionUID = 1L;

	public ModelDAO() {
		super(Model.class);
	}

	public List<Model> retrieveBySuggestOrderByDescription(String suggest) {
		return this.retrieveBySuggestionOrderByDescription(suggest, "id", "name");
	}
}
