package com.github.cg.example.jsf.dao;
import java.util.List;
import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.Car;

public class CarDAO extends DAO<Car,Long> {

	private static final long serialVersionUID = 1L;

	public CarDAO() {
		super(Car.class);
	}

	public List<Car> retrieveBySuggestOrderByDescription(String suggest) {
		return this.retrieveBySuggestionOrderByDescription(suggest, "id", "licensePlate");
	}
}
