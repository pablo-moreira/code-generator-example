package com.github.cg.example.jsf.component;

import com.github.cg.annotation.Component;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeOneToMany;

@Component
public class GridXhtmlColumnsComponent extends BaseComponent {

	public String render() {
		
		StringBuilder sb = new StringBuilder();
		
		String path = "entity";
		
		for (Attribute attribute : getCg().getEntity().getAttributes()) {
			
			// Verificar se e para renderizar o filtro
			if (attribute.isRenderColumn()) {
			
				// Ignora as associacoes OneToMany
				if (!AttributeOneToMany.class.isInstance(attribute)) {
					println(sb, "\t\t\t");
					println(sb, "\t\t\t<p:column sortBy=\"#'{'{0}.{1}'}'\">", path, getValue(attribute));
					println(sb, "\t\t\t\t<f:facet name=\"header\"><h:outputText value=\"{0}\" /></f:facet>", attribute.getLabel());
					printot(sb, "\t\t\t\t", path, attribute);
					println(sb, "\t\t\t</p:column>");
				}
			}
		}
		
		return sb.toString();
	}
}