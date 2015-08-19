package com.github.cg.example.jsf.manager;
import javax.inject.Inject;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.core.model.Car;

public class CarManager extends Manager<CarDAO, Car, Long> {

	@Inject
	public CarManager(CarDAO dao) {
		super(dao);
	}
}
