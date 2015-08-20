package com.github.cg.example.jsf;

import br.com.atos.cg.gui.DlgFrmEntity;

import com.github.cg.annotation.Plugin;
import com.github.cg.annotation.Target;
import com.github.cg.annotation.TargetTask;
import com.github.cg.annotation.TaskConfig;
import com.github.cg.task.FrmEntityTask;

@Plugin(
	targets = { 
		@Target(
				name = "Manager.xhtml",
				description = "Generate View Manager.xhtml",
				filename = "${app.dirs.base}/${app.dirs.web}/pages/${entity.name}/${entity.nameFlc}Manager.xhtml", 
				template = "/templates/com.github.cg.example.jsf.manager.xhtml.vm",
				allowOverwrite = true,
				tasksToExecuteBefore={
						@TargetTask(
								task=FrmEntityTask.class,
								configs={
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_COLUMN, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FILTER, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_ATTRIBUTE_DESCRIPTION, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM_TYPE, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_SHOW_ATTRIBUTES_ONE_TO_MANY, value = "false"),
								}
						)
				}
		)
})
public interface JsfExamplePlugin {}