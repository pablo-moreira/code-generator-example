package com.github.cg.example.jsf.component;

import com.github.cg.annotation.Component;
import com.github.cg.model.AttributeFormType;
import com.github.cg.model.AttributeOneToMany;

@Component
public class WinFrmJavaAttributesComponent extends BaseComponent {

	public String render() {
		
		StringBuilder sb = new StringBuilder();
		
		for (AttributeOneToMany atributo : getTargetContext().getEntity().getAttributesOneToMany()) {
			if (AttributeFormType.INTERNAL.equals(atributo.getFormType())) {
				println(sb, "\tprivate FrmAssociationOneToMany<WinFrm{0}, {0}, {1}> association{2};", getTargetContext().getEntity().getName(), atributo.getAssociationClassName(), atributo.getNameFuc());
			}
			else {
				println(sb, "\t@Inject");
				println(sb, "\t@New");
				println(sb, "\tprivate WinFrm{0} winFrm{0};", atributo.getAssociationClassName());	
				println(sb, "\tprivate WinFrmAssociationOneToMany<WinFrm{0}, WinFrm{1}, {0}, {1}> association{2};", getTargetContext().getEntity().getName(), atributo.getAssociationClassName(), atributo.getNameFuc());
			}
		}
		
		return sb.toString();
	}
}
