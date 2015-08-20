package com.github.cg.example.jsf.component;

import static br.com.atos.utils.StringUtils.firstToLowerCase;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.github.cg.component.Component;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeManyToOne;

import br.com.atos.core.model.BaseEnum;
import br.com.atos.core.model.IBaseEntity;
import br.com.atos.core.util.JpaReflectionUtils;

abstract public class BaseComponent extends Component {
		
	protected void printot(StringBuilder sb, String indentation, String path, Attribute attribute) {

		Class<?> type = getType(attribute);
		String value = path + "." + getValue(attribute);

		if (BaseEnum.class.isAssignableFrom(type)) {
			println(sb, "{0}<h:outputText value=\"#'{'{1}.description'}'\" />", indentation, value);
		}
		else if (Date.class.isAssignableFrom(type)) {
			
			println(sb, "{0}<h:outputText value=\"#'{'{1}'}'\">", indentation, value);
			
			Temporal annotation;
			
			if (AttributeManyToOne.class.isInstance(attribute)) {
				annotation = ((AttributeManyToOne) attribute).getAnnotationOfDescriptionAttributeOfAssociation(Temporal.class);
			}
			else {
				annotation = attribute.getAnnotation(Temporal.class);
			}

			if (annotation == null || annotation.value() == TemporalType.TIMESTAMP) {
				println(sb, "{0}\t<f:convertDateTime locale=\"pt_BR\" type=\"both\" />", indentation);
			}
			else if (annotation.value() == TemporalType.DATE) {
				println(sb, "{0}\t<f:convertDateTime locale=\"pt_BR\" type=\"date\" />", indentation);
			}
			else if (annotation.value() == TemporalType.TIME) {
				println(sb, "{0}\t<f:convertDateTime locale=\"pt_BR\" type=\"time\" />", indentation);
			}
			
			println(sb, "{0}</h:outputText>", indentation);
		}
		else {
			println(sb, "{0}<h:outputText value=\"#'{'{1}'}'\" />", indentation, value);
		}
	}
	
	protected void printin(StringBuilder sb, String indentation, String path, Attribute attribute) {
		printin(sb, indentation, path, attribute, false);
	}
	
	protected void printin(StringBuilder sb, String indentation, String path, Attribute attribute, boolean entityTab) {
			
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
			println(sb, indentation + "<p:selectOneMenu id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" effectDuration=\"0\" required=\"{3}\">", id, label, value, required);
			println(sb, indentation + "\t<f:selectItems value=\"#'{'selectItems.{0}Itens'}'\" />", firstToLowerCase(type.getSimpleName()));
			println(sb, indentation + "</p:selectOneMenu>");
		}
		else if (Date.class.isAssignableFrom(type) || Calendar.class.isAssignableFrom(type)) {

			Temporal annotation = attribute.getAnnotation(Temporal.class);
			
			if (annotation == null || annotation.value() == TemporalType.TIMESTAMP) {
				println(sb, indentation + "<p:calendar id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" showOn=\"button\" locale=\"pt_BR\" pattern=\"dd/MM/yyyy HH:mm\" required=\"{3}\" onkeypress=\"Mask.valid(this, ''dataHorario'')\" />", id, label, value, required);
			}
			else if (annotation.value() == TemporalType.DATE) {
				println(sb, indentation + "<p:calendar id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" showOn=\"button\" locale=\"pt_BR\" pattern=\"dd/MM/yyyy\" required=\"{3}\" onkeypress=\"Mask.valid(this, ''data'')\" />", id, label, value, required);
			}
			else if (attribute.getAnnotation(Temporal.class).value() == TemporalType.TIME) {
				println(sb, indentation + "<p:calendar id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" size=\"4\" showOn=\"button\" locale=\"pt_BR\" pattern=\"HH:mm\" timeOnly=\"true\" required=\"{3}\" onkeypress=\"Mask.valid(this, ''horario'')\" />", id, label, value, required);
			}
		}
		else if (IBaseEntity.class.isAssignableFrom(type)) {
			
			AttributeManyToOne atributoManyToOne = (AttributeManyToOne) attribute;
		
			Field associacaoFieldId = JpaReflectionUtils.getFieldId(type);

			// Imprime um autocomplete
			println(sb, indentation + "<p:autoComplete id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" forceSelection=\"true\"", id, label, value, required);

			if (entityTab) {
				println(sb, indentation + "\tdisabled=\"#'{'cc.attrs.winFrm.entityAssociated == cc.attrs.winFrm.entity.{0}'}'\"", attribute.getName());
			}
			
			println(sb, indentation + "\tcompleteMethod=\"#'{'autoCompleteCtrl.onComplete{0}'}'\" dropdown=\"true\" converter=\"lazyEntityConverter\"", type.getSimpleName());
			println(sb, indentation + "\tvar=\"{0}\" itemValue=\"#'{'{0}'}'\" itemLabel=\"#'{'{0}.{1}'}'\"", firstToLowerCase(type.getSimpleName()), atributoManyToOne.getDescriptionAttributeOfAssociation());
			println(sb, indentation + "\tsize=\"{0}\" scrollHeight=\"200\">", entityTab ? "40" : "35");
			println(sb, indentation + "\t<p:column><h:outputText value=\"#'{'{0}.{1}'}'\" /></p:column>", firstToLowerCase(type.getSimpleName()), associacaoFieldId.getName());
			println(sb, indentation + "\t<p:column><h:outputText value=\"#'{'{0}.{1}'}'\" /></p:column>", firstToLowerCase(type.getSimpleName()), atributoManyToOne.getDescriptionAttributeOfAssociation());
			println(sb, indentation + "\t<p:ajax event=\"query\" global=\"false\" />");
			println(sb, indentation + "</p:autoComplete>");
		}
		else {
			println(sb, indentation + "<p:inputText id=\"{0}\" label=\"{1}\" value=\"#'{'{2}'}'\" required=\"{3}\" style=\"width: {4}px\" />", id, label, value, required, entityTab ? "300" : "100");
		}
	}
}