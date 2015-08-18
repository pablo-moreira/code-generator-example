package com.github.cg.example.jsf.component;

import com.github.cg.annotation.Component;
import com.github.cg.model.AttributeFormType;
import com.github.cg.model.AttributeOneToMany;

import br.com.atos.utils.StringUtils;

@Component
public class WinFrmXhtmlAssociationsComponent extends BaseComponent {
	
	public String render() {
		
		StringBuilder sb = new StringBuilder();
		
		for (AttributeOneToMany attribute : getCg().getEntity().getAttributesOneToMany()) {
			if (AttributeFormType.EXTERNAL.equals(attribute.getFormType())) {
				println(sb, "\t\t<app:winFrm{0} winFrm=\"#'{'cc.attrs.winFrm.association{1}.winFrmAssociation'}'\" saveAction=\"#'{'cc.attrs.winFrm.association{1}.save'}'\" deleteAction=\"#'{'cc.attrs.winFrm.association{1}.delete'}'\" />", attribute.getAssociationClassSimpleName(), StringUtils.firstToUpperCase(attribute.getName()));
			}
		}
		
		return sb.toString();
	}
}