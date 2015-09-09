package com.github.cg.exmple.jsf;

import br.com.atos.cg.CodeGenerator;

import com.github.cg.example.core.model.Model;

public class AppCodeGenerator {

	public static void main(String[] args) throws Exception {
		
		CodeGenerator cg = new CodeGenerator();
		//cg.start();
		cg.executeTargetByName("FrmEntity.java", Model.class);
		System.exit(0);
	}
}
