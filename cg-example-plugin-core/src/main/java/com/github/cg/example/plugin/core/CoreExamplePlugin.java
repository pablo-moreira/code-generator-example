package com.github.cg.example.plugin.core;

import com.github.cg.annotation.Plugin;
import com.github.cg.annotation.Target;
import com.github.cg.annotation.TargetGroup;

@Plugin(
	targetsGroups = {
		@TargetGroup(
				name="EntityDAO.java and EntityManager.java",
				targets={"EntityDAO.java","EntityManager.java"}				
		)	
	},
	targets = { 
		@Target(
				name = "EntityDAO.java", 
				description = "Generate class EntityDAO.java",								 
				filename = "${app.dirs.base}/${app.dirs.src}/${stringUtils.pkgToDir($app.pkgs.dao)}/${entity.name}DAO.java",
				template = "/templates/com.github.cg.example.plugin.core.dao.java.vm",
				allowOverwrite = false				
		),
		@Target(				
				name = "EntityManager.java",
				description = "Generate class EntityManager.java", 
				filename = "${app.dirs.base}/${app.dirs.src}/${stringUtils.pkgToDir($app.pkgs.manager)}/${entity.name}Manager.java", 
				template = "/templates/com.github.cg.example.plugin.core.manager.java.vm",
				allowOverwrite = false
		)	
})
public interface CoreExamplePlugin {}