package com.github.cg.example.jsf.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.github.cg.example.core.model.IBaseEntity;
import com.github.cg.example.core.util.FacesConvertUtil;
import com.github.cg.example.core.util.StringUtils;

@FacesConverter(value="lazyEntityConverter")
public class LazyEntityConverter implements Converter {
	
	public static final String VALUE_NULL = "__NULL__";

	public Object getAsObject(FacesContext context, UIComponent component, String valueKey) {

		if (!StringUtils.isNullOrEmpty(valueKey) && !valueKey.equals(VALUE_NULL)) { 

			Object value = component.getAttributes().get(valueKey);
			
			IBaseEntity<?> entity = (IBaseEntity<?>) value;
			
			FacesConvertUtil facesConvertUtil = context.getApplication().evaluateExpressionGet(context, "#{facesConvertUtil}", FacesConvertUtil.class);
			
			return facesConvertUtil.findEntityById(entity);
		}
		else {
			return null;
		}
	}

	public String getAsString(FacesContext ctx, UIComponent component, Object value) {

		if (value != null && !"".equals(value)) {

			if (!(value instanceof IBaseEntity)) {
				throw new ConverterException("LazyEntityConverter - Conversion failed because the object is not an instance of IBaseEntity!");
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