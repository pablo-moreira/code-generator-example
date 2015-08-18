package com.github.cg.example.jsf.component;

import java.util.List;

import br.com.atos.utils.StringUtils;

import com.github.cg.annotation.Component;
import com.github.cg.model.AttributeFormType;
import com.github.cg.model.AttributeOneToMany;

@Component
public class WinFrmJavaMethodsComponent extends BaseComponent {

	public String render() {

		StringBuilder sb = new StringBuilder();
		
		List<AttributeOneToMany> atributosOneToMany = getCg().getEntity().getAttributesOneToMany();
		
		if (!atributosOneToMany.isEmpty()) {
		
			println(sb, "\t@PostConstruct");
			println(sb, "\tpublic void initialize() {");
			
			for (AttributeOneToMany atributo : atributosOneToMany) {						
				
				println(sb, "\t\t");
				
				if (AttributeFormType.INTERNAL.equals(atributo.getFormType())) {
					println(sb, "\t\tthis.association{2} = new FrmAssociationOneToMany<WinFrm{0}, {0}, {1}>(this, {1}.class, {1}Manager.class, \"tbView:dt{2}\") '{'", getCg().getAttributeValue("EntityName"), atributo.getAssociationClassSimpleName(), StringUtils.firstToUpperCase(atributo.getName()));				
				}
				else {	
					println(sb, "\t\tthis.association{2} = new WinFrmAssociationOneToMany<WinFrm{0}, WinFrm{1}, {0}, {1}>(this, winFrm{1}, \"tbView:dt{2}\") '{'", getCg().getAttributeValue("EntityName"), atributo.getAssociationClassSimpleName(), StringUtils.firstToUpperCase(atributo.getName()));
				}
				
				println(sb, "\t\t\t");
				println(sb, "\t\t\t@Override");
				println(sb, "\t\t\tpublic void connect({0} association, {1} entity) '{'", atributo.getAssociationClassSimpleName(), getCg().getAttributeValue("EntityName"));			
				println(sb, "\t\t\t\tassociation.set{0}(entity);", StringUtils.firstToUpperCase(atributo.getAssociationMappedBy()));
				println(sb, "\t\t\t}");
				println(sb, "\t\t\t");
				println(sb, "\t\t\t@Override");
				println(sb, "\t\t\tpublic List<{0}> getAssociations({1} entity) '{'", atributo.getAssociationClassSimpleName(), getCg().getAttributeValue("EntityName"));
				println(sb, "\t\t\t\treturn entity.get{0}();", StringUtils.firstToUpperCase(atributo.getName()));
				println(sb, "\t\t\t}");
				println(sb, "\t\t};");
			}
			
			println(sb, "\t}");
			println(sb, "\t");
			
			for (AttributeOneToMany atributo : atributosOneToMany) {
				if (AttributeFormType.INTERNAL.equals(atributo.getFormType())) {
					println(sb, "\tpublic FrmAssociationOneToMany<WinFrm{0}, {0}, {1}> getAssociation{2}() '{'", getCg().getAttributeValue("EntityName"), atributo.getAssociationClassSimpleName(), StringUtils.firstToUpperCase(atributo.getName()));
					println(sb, "\t\treturn association{0};", StringUtils.firstToUpperCase(atributo.getName()));
					println(sb, "\t}");
				}
				else {
					println(sb, "\tpublic WinFrmAssociationOneToMany<WinFrm{0}, WinFrm{1}, {0}, {1}> getAssociation{2}() '{'", getCg().getAttributeValue("EntityName"), atributo.getAssociationClassSimpleName(), StringUtils.firstToUpperCase(atributo.getName()));
					println(sb, "\t\treturn association{0};", StringUtils.firstToUpperCase(atributo.getName()));
					println(sb, "\t}");
				}
			}
		}	
		
		return sb.toString();
	}
}