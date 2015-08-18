package com.github.cg.example.core;

import com.github.cg.annotation.Plugin;
import com.github.cg.annotation.Target;
import com.github.cg.annotation.TargetGroup;

@Plugin(
	targetsGroups = {
		@TargetGroup(
				name="DAO e Manager",
				targets={"DAO","Manager"}				
		)	
	},
	targets = { 
		@Target(
				name = "DAO", 
				description = "Generate DAO class",								 
				filenameTemplate = "${app.dirs.base}/${app.dirs.src}/${stringUtil.pkgToDir($app.pkgs.dao)}/${entity.name}DAO.java",
				template = "com.github.cg.example.core.dao.java.vm",
				allowOverwrite = false				
		),
		@Target(				
				name = "Manager",
				description = "Generate Manager class", 
				filenameTemplate = "${app.dirs.base}/${app.dirs.src}/${stringUtil.pkgToDir($app.pkgs.manager)}/${entity.name}Manager.java", 
				template = "com.github.cg.example.core.manager.java.vm",
				allowOverwrite = false
		)	
})
public interface CoreExamplePlugin {}