package com.github.cg.exmple.jsf;

import com.github.cg.CodeGenerator;
import com.github.cg.example.core.model.Manufacturer;

public class AppCodeGenerator {

	public static void main(String[] args) throws Exception {
		
		CodeGenerator cg = new CodeGenerator();
//		cg.start();
		cg.executeTargetByName("FrmEntity.java", Manufacturer.class);
		System.exit(0);
	}
}
