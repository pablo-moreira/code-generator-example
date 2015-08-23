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
				name = "entityEdit.xhtml",
				description = "Generate View entityEdit.xhtml",
				filename = "${app.dirs.base}/${app.dirs.web}/pages/${entity.nameFlc}/${entity.nameFlc}Edit.xhtml", 
				template = "/templates/com.github.cg.example.jsf.edit.xhtml.vm",
				allowOverwrite = true,
				tasksToExecuteBefore={
						@TargetTask(
								task=FrmEntityTask.class,
								configs={
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_COLUMN, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FILTER, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_ATTRIBUTE_DESCRIPTION, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM_TYPE, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_SHOW_ATTRIBUTES_ONE_TO_MANY, value = "false"),
								}
						)
				}
		),
		@Target(
				name = "EditCtrl.java",
				description = "Generate Class EntityEditCtrl.java",
				filename = "${app.dirs.base}/${app.dirs.src}/${stringUtils.pkgToDir($app.pkgs.controller)}/${entity.name}EditCtrl.java", 
				template = "/templates/com.github.cg.example.jsf.editCtrl.java.vm",
				allowOverwrite = true,
				tasksToExecuteBefore={
						@TargetTask(
								task=FrmEntityTask.class,
								configs={
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_COLUMN, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FILTER, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_ATTRIBUTE_DESCRIPTION, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM_TYPE, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_SHOW_ATTRIBUTES_ONE_TO_MANY, value = "false"),
								}
						)
				}
		),
		@Target(
				name = "entityList.xhtml",
				description = "Generate View entityList.xhtml",
				filename = "${app.dirs.base}/${app.dirs.web}/pages/${entity.nameFlc}/${entity.nameFlc}List.xhtml", 
				template = "/templates/com.github.cg.example.jsf.list.xhtml.vm",
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
		),
		@Target(
				name = "EntityListCtrl.java",
				description = "Generate Class EntityListCtrl.java",
				filename = "${app.dirs.base}/${app.dirs.src}/${stringUtils.pkgToDir($app.pkgs.controller)}/${entity.name}ListCtrl.java", 
				template = "/templates/com.github.cg.example.jsf.listCtrl.java.vm",
				allowOverwrite = true,
				tasksToExecuteBefore={
						@TargetTask(
								task=FrmEntityTask.class,
								configs={
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_COLUMN, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FILTER, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_ATTRIBUTE_DESCRIPTION, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM_TYPE, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_SHOW_ATTRIBUTES_ONE_TO_MANY, value = "false"),
								}
						)
				}
		),
		@Target(
				name = "entityView.xhtml",
				description = "Generate View entityView.xhtml",
				filename = "${app.dirs.base}/${app.dirs.web}/pages/${entity.nameFlc}/${entity.nameFlc}View.xhtml", 
				template = "/templates/com.github.cg.example.jsf.view.xhtml.vm",
				allowOverwrite = true,
				tasksToExecuteBefore={
						@TargetTask(
								task=FrmEntityTask.class,
								configs={
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_COLUMN, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FILTER, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_ATTRIBUTE_DESCRIPTION, value = "true"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM_TYPE, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_SHOW_ATTRIBUTES_ONE_TO_MANY, value = "false"),
								}
						)
				}
		),
		@Target(
				name = "EntityViewCtrl.java",
				description = "Generate Class EntityViewCtrl.java",
				filename = "${app.dirs.base}/${app.dirs.src}/${stringUtils.pkgToDir($app.pkgs.controller)}/${entity.name}ViewCtrl.java", 
				template = "/templates/com.github.cg.example.jsf.viewCtrl.java.vm",
				allowOverwrite = true,
				tasksToExecuteBefore={
						@TargetTask(
								task=FrmEntityTask.class,
								configs={
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_COLUMN, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FILTER, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_ATTRIBUTE_DESCRIPTION, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_RENDER_FORM_TYPE, value = "false"),
									@TaskConfig(name = DlgFrmEntity.CONFIG_SHOW_ATTRIBUTES_ONE_TO_MANY, value = "false"),
								}
						)
				}
		)
})
public interface JsfExamplePlugin {}