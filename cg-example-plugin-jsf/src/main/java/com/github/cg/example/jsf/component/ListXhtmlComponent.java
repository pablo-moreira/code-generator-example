package com.github.cg.example.jsf.component;

import static br.com.atos.utils.StringUtils.firstToLowerCase;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.atos.core.model.BaseEnum;
import br.com.atos.faces.component.model.FilterDate;

import com.github.cg.annotation.Component;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeManyToOne;
import com.github.cg.model.AttributeOneToMany;

@Component
public class ListXhtmlComponent extends BaseComponent {

	public String renderFilters() {

		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		
		for (Attribute attribute : getTargetContext().getEntity().getAttributes()) {
			
			// Verificar se e para renderizar o filtro
			if (attribute.isRenderFilter()) {
				
				// Ignora as associacoes OneToMany
				if (!AttributeOneToMany.class.isInstance(attribute)) {
					
					if (AttributeManyToOne.class.isInstance(attribute)) {
						AttributeManyToOne atributoManyToOne =  (AttributeManyToOne) attribute;
						printFilter(sb, attribute, atributoManyToOne.getDescriptionAttributeOfAssociationType(), atributoManyToOne.getAnnotationOfDescriptionAttributeOfAssociation(Temporal.class), attribute.getName() + "." + atributoManyToOne.getDescriptionAttributeOfAssociation());
					}
					else {												
						printFilter(sb, attribute, attribute.getType(), attribute.getAnnotation(Temporal.class), attribute.getName());
					}
				}
			}
		}
		
		return sb.toString();
	}
	
	private void printFilter(StringBuilder sb, Attribute attribute, Class<?> type, Temporal annotation, String attributePath) {
		
		if (Number.class.isAssignableFrom(type)) {				
			println(sb, "\t\t\t<atos:filterNumeric operatorDefault=\"=\" attribute=\"{0}\" label=\"{1}\" type=\"{2}\" />", attributePath, attribute.getLabel(), type.getSimpleName());	
		}
		else if (BaseEnum.class.isAssignableFrom(type)) {
			println(sb, "\t\t\t<atos:filterEnum operatorDefault=\"=\" attribute=\"{0}\" label=\"{1}\" options=\"#'{'selectItems.{2}Itens'}'\" />", attributePath, attribute.getLabel(), firstToLowerCase(type.getSimpleName()));
		}
		else if (Date.class.isAssignableFrom(type)) {
				
			if (annotation == null || annotation.value() == TemporalType.TIMESTAMP) {
				println(sb, "\t\t\t<atos:filterDate operatorDefault=\"contains\" attribute=\"{0}\" label=\"{1}\" datePattern=\"{2}\" />", attributePath, attribute.getLabel(), FilterDate.DATE_PATTERN_DATA_HORARIO);
			}
			else if (annotation.value() == TemporalType.DATE) {
				println(sb, "\t\t\t<atos:filterDate operatorDefault=\"contains\" attribute=\"{0}\" label=\"{1}\" datePattern=\"{2}\" />", attributePath, attribute.getLabel(), FilterDate.DATE_PATTERN_DATA);
			}
			else {
				// Nao faz nada...
			}
		}
		else {
			println(sb, "\t\t\t<atos:filterString operatorDefault=\"contains\" attribute=\"{0}\" label=\"{1}\" />", attributePath, attribute.getLabel());
		}
	}
		
	public String renderColumns(int tabs) {
		
		StringBuilder sb = new StringBuilder();
		
		String path = "entity";
		String tab = tab(tabs);
		
		for (Attribute attribute : getTargetContext().getEntity().getAttributes()) {
			
			// Verificar se e para renderizar o filtro
			if (attribute.isRenderColumn()) {
			
				// Ignora as associacoes OneToMany
				if (!AttributeOneToMany.class.isInstance(attribute)) {
					println(sb, tab);
					println(sb, tab + "<p:column sortBy=\"#'{'{0}.{1}'}'\">", path, getValue(attribute));
					println(sb, tab + "\t<f:facet name=\"header\"><h:outputText value=\"{0}\" /></f:facet>", attribute.getLabel());
					printot(sb, tab + "\t", path, attribute);
					println(sb, tab + "</p:column>");
				}
			}
		}
		
		return sb.toString();
	}
}