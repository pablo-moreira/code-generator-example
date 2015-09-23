package com.github.cg.example.jsf.component;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.cg.component.Component;
import com.github.cg.component.StringUtils;
import com.github.cg.example.core.model.BaseEnum;
import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.jsf.model.PluginPatterns;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeManyToOne;
import com.github.cg.model.Patterns;

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
			print(sb, "{0}</h:outputText>", tab);
		}
		else {
			
			if (Patterns.CURRENCY.equals(attribute.getPattern())) {
				println(sb, "{0}<h:outputText value=\"#'{'{1}'}'\">", renderTabOnFirst ? tab : "", value);
				println(sb, "{0}\t<f:convertNumber locale=\"#'{'localeCtrl.locale'}'\" type=\"currency\" />", tab);			
				print(sb, "{0}</h:outputText>", tab);
			}
			else {
				print(sb, "{0}<h:outputText value=\"#'{'{1}'}'\" />", renderTabOnFirst ? tab : "", value);
			}
		}	
	}
		
	protected void printin(StringBuilder sb, String tab, String path, Attribute attribute, boolean tabEntity) {
			
		String id = attribute.getName();
		String label = attribute.getLabel();
		Class<?> type = attribute.getType();
		String value = path + "." + attribute.getName();
		
		String required = attribute.isRequired().toString().toLowerCase();
		
		if (BaseEnum.class.isAssignableFrom(type)) {
			println(sb, "<p:selectOneMenu id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" effectDuration=\"0\" required=\"{3}\">", id, label, value, required);
			println(sb, tab + "\t<f:selectItems value=\"#'{'selectItemsCtrl.{0}Items'}'\" />", flc(type.getSimpleName()));
			print(sb, tab + "</p:selectOneMenu>");
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
			
			print(sb, "<p:calendar id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" showOn=\"button\" locale=\"{3}\" pattern=\"{4}\" required=\"{5}\" mask=\"true\" {6}/>", id, label, value, locale, pattern, required, othersAttributes);						
		}
		else if (IBaseEntity.class.isAssignableFrom(type)) {
			
			AttributeManyToOne atributoManyToOne = (AttributeManyToOne) attribute;		
			
			println(sb, "<p:autoComplete id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" forceSelection=\"true\"", id, label, value, required);
			println(sb, tab + "\tdisabled=\"#'{'cc.attrs.frm.verifyEntityAssociated({0})'}'\"", value);					
			println(sb, tab + "\tcompleteMethod=\"#'{'cc.attrs.frm.onComplete{1}'}'\" dropdown=\"true\" converter=\"lazyEntityConverter\"", path, type.getSimpleName());
			println(sb, tab + "\tvar=\"{0}\" itemValue=\"#'{'{0}'}'\" itemLabel=\"#'{'{0}.{1}'}'\"", flc(type.getSimpleName()), atributoManyToOne.getDescriptionAttributeOfAssociation());
			println(sb, tab + "\tsize=\"{0}\" scrollHeight=\"200\">", tabEntity ? "40" : "35");
			println(sb, tab + "\t<p:column><h:outputText value=\"#'{'{0}.{1}'}'\" /></p:column>", flc(type.getSimpleName()), atributoManyToOne.getEntity().getAttributeId().getName());
			println(sb, tab + "\t<p:column><h:outputText value=\"#'{'{0}.{1}'}'\" /></p:column>", flc(type.getSimpleName()), atributoManyToOne.getDescriptionAttributeOfAssociation());
			println(sb, tab + "\t<p:ajax event=\"query\" global=\"false\" />");
			print(sb, tab + "</p:autoComplete>");
		}
		else {
			
			String pattern = attribute.getPattern();
			
			if (Patterns.CURRENCY.equals(pattern)) {
				println(sb, "<p:inputText id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" style=\"width: {4}px\">", id, label, value, required, tabEntity ? "300" : "100");
				println(sb, tab + "\t<f:convertNumber type=\"currency\" locale=\"#{localeCtrl.locale}\" />");
				print(sb, tab + "</p:inputText>");
			}
			else if (PluginPatterns.LICENSE_PLATE.equals(pattern)) {
				print(sb, "<p:inputMask id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" style=\"width: {4}px\" mask=\"{5}\" />", id, label, value, required, tabEntity ? "300" : "100", "#{maskCtrl.licensePlate}");
			}
			else if (PluginPatterns.YEAR.equals(pattern)) {
				print(sb, "<p:inputMask id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" style=\"width: {4}px\" mask=\"{5}\" />", id, label, value, required, tabEntity ? "300" : "100", "#{maskCtrl.year}");
			}
			else {
				print(sb, "<p:inputText id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" style=\"width: {4}px\" />", id, label, value, required, tabEntity ? "300" : "100");
			}
		}
	}

	private String flc(String str) {
		return StringUtils.getInstance().firstToLowerCase(str);
	}
}