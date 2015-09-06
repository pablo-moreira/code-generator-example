package com.github.cg.example.jsf.controller;

import java.util.List;
import java.util.TreeMap;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import static com.github.cg.example.jsf.util.SelectItemUtils.getEnumSelectItems;
import com.github.cg.example.core.model.Classification;
import com.github.cg.example.jsf.util.PrimefacesThemes;

@Named
@ApplicationScoped
public class SelectItemsCtrl {

	public TreeMap<String, String> getPrimefacesThemes() {
		return PrimefacesThemes.getThemes();
	}
	
	public List<SelectItem> getClassificationItems() {
		return getEnumSelectItems(Classification.class);
	}
}
