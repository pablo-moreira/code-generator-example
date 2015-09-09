package com.github.cg.example.jsf.component;

import static br.com.atos.utils.StringUtils.firstToLowerCase;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.cg.component.Component;
import com.github.cg.example.core.model.BaseEnum;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeManyToOne;

abstract public class BaseComponent extends Component {

	protected void printot(StringBuilder sb, String tab, String path, Attribute attribute) {
		printot(sb, tab, path, attribute, true);		
	}
	
	protected void printot(StringBuilder sb, String tab, String path, Attribute attribute, boolean renderTabOnFirst) {

		Class<?> type = getType(attribute);
		String value = path + "." + getValue(attribute);

		if (BaseEnum.class.isAssignableFrom(type)) {
			print(sb, "{0}<h:outputText value=\"#'{'{1}.description'}'\" />", renderTabOnFirst ? tab : "", value);
		}
		else if (Date.class.isAssignableFrom(type)) {
							
			Temporal annotation;
			
			if (AttributeManyToOne.class.isInstance(attribute)) {
				annotation = ((AttributeManyToOne) attribute).getAnnotationOfDescriptionAttributeOfAssociation(Temporal.class);
			}
			else {
				annotation = attribute.getAnnotation(Temporal.class);
			}

			String temporalType;
			
			if (annotation != null && annotation.value() == TemporalType.DATE) {
				temporalType = "date";
			}
			else if (annotation != null && annotation.value() == TemporalType.TIME) {
				temporalType = "type";
			}
			else {
				temporalType = "both";
			}
			
			println(sb, "{0}<h:outputText value=\"#'{'{1}'}'\">", renderTabOnFirst ? tab : "", value);
			println(sb, "{0}\t<f:convertDateTime locale=\"#'{'localeCtrl.locale'}'\" type=\"{1}\" />", tab, temporalType);			
			println(sb, "{0}</h:outputText>", tab);
		}
		else {
			println(sb, "{0}<h:outputText value=\"#'{'{1}'}'\" />", renderTabOnFirst ? tab : "", value);
		}	
	}
	
	protected void printin(StringBuilder sb, String tab, String path, Attribute attribute) {
		printin(sb, tab, path, attribute, false);
	}
	
	protected void printin(StringBuilder sb, String tab, String path, Attribute attribute, boolean entityTab) {
			
		String id = attribute.getName();
		String label = attribute.getLabel();
		Class<?> type = attribute.getType();
		String value = path + "." + attribute.getName();
		
		String required = "false";
	
		if (attribute.getAnnotation(Column.class) != null) {
			required = attribute.getAnnotation(Column.class).nullable() ? "false" : "true";
		}
		else if (attribute.getAnnotation(OneToOne.class) != null) {
			required = attribute.getAnnotation(OneToOne.class).optional() ? "false" : "true";
		}
		else if (attribute.getAnnotation(ManyToOne.class) != null) {
			required = attribute.getAnnotation(ManyToOne.class).optional() ? "false" : "true";
		}
		else if (attribute.getAnnotation(JoinColumn.class) != null) {
			required = attribute.getAnnotation(JoinColumn.class).nullable() ? "false" : "true";
		}
		
		if (BaseEnum.class.isAssignableFrom(type)) {
			println(sb, tab + "<p:selectOneMenu id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" effectDuration=\"0\" required=\"{3}\">", id, label, value, required);
			println(sb, tab + "\t<f:selectItems value=\"#'{'selectItems.{0}Itens'}'\" />", firstToLowerCase(type.getSimpleName()));
			println(sb, tab + "</p:selectOneMenu>");
		}
		else if (Date.class.isAssignableFrom(type) || Calendar.class.isAssignableFrom(type)) {

			Temporal annotation = attribute.getAnnotation(Temporal.class);

			String locale = "#{localeCtrl.locale}";
			String pattern;
			String othersAttributes = "";
			
			if (annotation != null && annotation.value() == TemporalType.DATE) {
				pattern = "#{localeCtrl.datePattern}";
			}
			else if (annotation != null && annotation.value() == TemporalType.TIME) {
				pattern = "#{localeCtrl.timePattern}";
				othersAttributes = "timeOnly=\"true\" size=\"4\"";
			}
			else {
				pattern = "#{localeCtrl.dataTimePattern}";
			}
			
			println(sb, tab + "<p:calendar id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" showOn=\"button\" locale=\"{3}\" pattern=\"{4}\" required=\"{5}\" mask=\"true\" {6} />", id, label, value, locale, pattern, required, othersAttributes);						
		}
		else if (IBaseEntity.class.isAssignableFrom(type)) {
			
			AttributeManyToOne atributoManyToOne = (AttributeManyToOne) attribute;
		
			// Imprime um autocomplete
			println(sb, tab + "<p:autoComplete id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" forceSelection=\"true\"", id, label, value, required);	
			println(sb, tab + "\tcompleteMethod=\"#'{'{0}.onComplete{1}'}'\" dropdown=\"true\" converter=\"lazyEntityConverter\"", path, type.getSimpleName());
			println(sb, tab + "\tvar=\"{0}\" itemValue=\"#'{'{0}'}'\" itemLabel=\"#'{'{0}.{1}'}'\"", firstToLowerCase(type.getSimpleName()), atributoManyToOne.getDescriptionAttributeOfAssociation());
			println(sb, tab + "\tsize=\"{0}\" scrollHeight=\"200\">", entityTab ? "40" : "35");
			println(sb, tab + "\t<p:column><h:outputText value=\"#'{'{0}.{1}'}'\" /></p:column>", firstToLowerCase(type.getSimpleName()), atributoManyToOne.getEntity().getAttributeId().getName());
			println(sb, tab + "\t<p:column><h:outputText value=\"#'{'{0}.{1}'}'\" /></p:column>", firstToLowerCase(type.getSimpleName()), atributoManyToOne.getDescriptionAttributeOfAssociation());
			println(sb, tab + "\t<p:ajax event=\"query\" global=\"false\" />");
			println(sb, tab + "</p:autoComplete>");
		}
		else {
			println(sb, tab + "<p:inputText id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" style=\"width: {4}px\" />", id, label, value, required, entityTab ? "300" : "100");
		}
	}
}