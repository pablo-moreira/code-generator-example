package com.github.cg.example.jsf.view.converter;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.FacesConvertUtil;
import com.github.cg.example.core.util.StringUtils;

@FacesConverter(value="smartEntityConverter")
public class SmartEntityConverter implements Converter {
	
	public static final String VALUE_NULL = "__NULL__"; 
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		if (!StringUtils.isNullOrEmpty(value) && !value.equals(VALUE_NULL)) { 

			IBaseEntity<?> newValue = (IBaseEntity<?>) component.getAttributes().get(value);
			
			ValueExpression valueExpression = component.getValueExpression("value");
			
			IBaseEntity<?> currentValue = (IBaseEntity<?>) valueExpression.getValue(context.getELContext());
			
			if (currentValue != null && newValue != null && currentValue.getId().equals(newValue.getId())) {
				return currentValue;
			}
			else {
				FacesConvertUtil facesConvertUtil = context.getApplication().evaluateExpressionGet(context, "#{facesConvertUtil}", FacesConvertUtil.class);
				
				return facesConvertUtil.findEntityById(newValue);
			}
		}
		else {
			return null;
		}
	}	

	public String getAsString(FacesContext ctx, UIComponent component, Object value) {

		if (value != null && !"".equals(value)) {

			if (!(value instanceof IBaseEntity)) {
				throw new ConverterException("SmartEntityConverter - Conversion failed because the object is not an instance of IBaseEntity!");
			}
							
			IBaseEntity<?> entity = (IBaseEntity<?>) value;

			String id;
			
			if (entity.getId() != null) {
				id = String.valueOf(entity.getId());
			}
			else {
				id = "";
			}

			component.getAttributes().put(id, entity);
			
			return id;
		}
		else {
			return VALUE_NULL;
		}
	}
}