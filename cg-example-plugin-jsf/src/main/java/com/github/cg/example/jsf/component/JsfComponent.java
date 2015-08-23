package com.github.cg.example.jsf.component;

import com.github.cg.annotation.Component;
import com.github.cg.model.Attribute;

@Component
public class JsfComponent extends BaseComponent {

	public String renderOutputText(int tabs, String path, Attribute attribute) {
		
		StringBuilder sb = new StringBuilder();
		String tab = tab(tabs);
		
		printot(sb, tab, path, attribute, false);
		
		return sb.toString();
	}
}