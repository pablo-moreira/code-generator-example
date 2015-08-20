package com.github.cg.exmple.jsf;

import com.github.cg.example.core.model.Car;

import br.com.atos.cg.CodeGenerator;

public class AppCodeGenerator {

	public static void main(String[] args) throws Exception {
		CodeGenerator cg = new CodeGenerator();
		cg.executeTargetByName("Manager.xhtml", Car.class);
	}
}
