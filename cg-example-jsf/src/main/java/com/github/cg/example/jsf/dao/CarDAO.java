package com.github.cg.example.jsf.dao;
import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.model.Car;

public class CarDAO extends DAO<Car, Long> {

	public CarDAO() {
		super(Car.class);
	}
}
