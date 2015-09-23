package com.github.cg.example.jsf.component;

import java.util.List;

import com.github.cg.annotation.Component;
import com.github.cg.example.jsf.model.PluginFormTypes;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeOneToMany;
import com.github.cg.model.Entity;

@Component
public class JsfComponent extends BaseComponent {

	public boolean hasAttributeWithFormTypeSubFrmInside(Entity entity) {
		return hasAttributeWithFormType(entity.getAttributesOneToMany(), PluginFormTypes.SUB_FRM_INSIDE);
	}
	
	public boolean hasAttributeWithFormTypeSubFrmInTable(Entity entity) {
		return hasAttributeWithFormType(entity.getAttributesOneToMany(), PluginFormTypes.SUB_FRM_IN_TABLE);
	}
	
	public boolean hasAttributeWithFormTypeSubFrmExternalPage(Entity entity) {
		return hasAttributeWithFormType(entity.getAttributesOneToMany(), PluginFormTypes.SUB_FRM_EXTERNAL_PAGE);
	}
	
	private boolean hasAttributeWithFormType(List<AttributeOneToMany> attributes, String formType) {
		
		boolean result = false;
		
		for (AttributeOneToMany attributeOneToMany : attributes) {
			if (isFormType(formType, attributeOneToMany.getFormType())) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public boolean isFormTypeSubFrmInTable(String formType) {
		return isFormType(PluginFormTypes.SUB_FRM_IN_TABLE, formType);
	}
	
	public boolean isFormTypeSubFrmInside(String formType) {
		return isFormType(PluginFormTypes.SUB_FRM_INSIDE, formType);
	}
	
	public boolean isFormTypeSubFrmExternalPage(String formType) {
		return isFormType(PluginFormTypes.SUB_FRM_EXTERNAL_PAGE, formType);
	}
	
	private boolean isFormType(String pluginFormType, String formType) {
		return pluginFormType.equals(formType);
	}

	public String renderOutputText(int tabs, String path, Attribute attribute) {
		
		StringBuilder sb = new StringBuilder();
		String tab = tab(tabs);
		
		printot(sb, tab, path, attribute, false);
		
		return sb.toString();
	}
	
	public String renderInput(int tabs, String path, Attribute attribute) {
		
		StringBuilder sb = new StringBuilder();
		String tab = tab(tabs);		
		
		printin(sb, tab, path, attribute);
		
		return sb.toString();
	}
}