
package com.github.cg.example.jsf.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.atos.core.model.IBaseEntity;
import br.com.atos.utils.StringUtils;

@FacesConverter(value="simpleEntityConverter")
public class SimpleEntityConverter implements Converter {
	
	public static final String VALUE_NULL = "__NULL__";

	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		
		if (!StringUtils.isNullOrEmpty(value) && !value.equals(VALUE_NULL)) { 
			Object v = component.getAttributes().get(value); 
			return v;
		}
		else {
			return null;
		}
	}

	public String getAsString(FacesContext ctx, UIComponent component, Object value) {

		if (value != null && !"".equals(value)) {

			if (!(value instanceof IBaseEntity)) {
				throw new ConverterException("SimpleEntityConverter - Conversion failed because the object is not an instance of IBaseEntity!");
			}
							
			IBaseEntity<?> entity = (IBaseEntity<?>) value;

			String id;
			
			if (entity.getId() != null) {
				id = String.valueOf(entity.getId());
			}
			else {
				id = "";
			}
	
			component.getAttributes().put(id, value);
			
			return id;
		}
		else {
			return VALUE_NULL;
		}
	}
}