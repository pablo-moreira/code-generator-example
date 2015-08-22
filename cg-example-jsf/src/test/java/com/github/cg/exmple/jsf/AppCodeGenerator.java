package com.github.cg.exmple.jsf;

import br.com.atos.cg.CodeGenerator;

import com.github.cg.example.core.model.Car;

public class AppCodeGenerator {

	public static void main(String[] args) throws Exception {
		
		CodeGenerator cg = new CodeGenerator();
		//cg.start();
		//cg.executeTargetByName("List.xhtml", Car.class);
		cg.executeTargetByName("EditCtrl.java", Car.class);
//		System.exit(0);
	}
}
