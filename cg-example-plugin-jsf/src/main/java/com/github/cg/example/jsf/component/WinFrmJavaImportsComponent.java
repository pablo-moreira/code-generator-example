package com.github.cg.example.jsf.component;

import java.util.List;

import com.github.cg.annotation.Component;
import com.github.cg.model.AttributeFormType;
import com.github.cg.model.AttributeOneToMany;

@Component
public class WinFrmJavaImportsComponent extends BaseComponent {

	public String render() {
		
		StringBuilder sb = new StringBuilder();
		
		List<AttributeOneToMany> atributosOneToMany = getTargetContext().getEntity().getAttributesOneToMany();
		
		if (atributosOneToMany.size() > 0) {
		
			println(sb, "import javax.annotation.PostConstruct;");
			println(sb, "import java.util.List;");
			
			boolean temTipoFormularioExterno = false;
			boolean temTipoFormularioEmbutido = false;
			
			for (AttributeOneToMany atributo : atributosOneToMany) {
				if (AttributeFormType.EXTERNAL.equals(atributo.getFormType())) {
					temTipoFormularioExterno = true;						
				}
				else {
					temTipoFormularioEmbutido = true;
					println(sb, "import {0}.{1}Manager;", getTargetContext().getCg().getApp().get("pkgs.manager"), atributo.getAssociationClassName());
				}

				println(sb, "import {0}.{1};", getTargetContext().getCg().getApp().get("pkgs.model"), atributo.getAssociationClassName());
			}
			
			if (temTipoFormularioExterno) {
				println(sb, "import javax.enterprise.inject.New;");
				println(sb, "import javax.inject.Inject;");
				println(sb, "import br.com.atos.faces.controller.component.WinFrmAssociationOneToMany;");
			}
			
			if (temTipoFormularioEmbutido) {
				println(sb, "import br.com.atos.faces.controller.component.FrmAssociationOneToMany;");
			}
		}	
		
		return sb.toString();
	}
}