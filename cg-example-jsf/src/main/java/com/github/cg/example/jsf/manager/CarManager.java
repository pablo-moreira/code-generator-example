package com.github.cg.example.jsf.manager;
import javax.ejb.Stateless;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.jsf.dao.CarDAO;
import com.github.cg.example.core.model.Car;

@Stateless
public class CarManager extends Manager<CarDAO,Car,Long> {

	private static final long serialVersionUID = 1L;
	
}
