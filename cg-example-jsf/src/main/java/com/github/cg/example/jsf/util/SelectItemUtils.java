package com.github.cg.example.jsf.util;


import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.github.cg.example.core.model.BaseEnum;

public class SelectItemUtils {
	
	public static List<SelectItem> getEnumSelectItems(Class<? extends BaseEnum> baseEnumClazz) {
		return getEnumSelectItems(baseEnumClazz.getEnumConstants());
	}
	
	public static List<SelectItem> getEnumSelectItems(BaseEnum[] baseEnums) {
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>(baseEnums.length);
		
		for (BaseEnum baseEnum : baseEnums) {
			selectItems.add(new SelectItem(baseEnum, baseEnum.getDescription()));
		}
		
		return selectItems;
	}			

	public static List<SelectItem> getEnumSelectItems(Class<? extends BaseEnum> baseEnumClazz, String noSelectionOptionLabel) {
		return getEnumSelectItems(baseEnumClazz.getEnumConstants(), noSelectionOptionLabel);
	}

	public static List<SelectItem> getEnumSelectItems(BaseEnum[] baseEnums, String noSelectionOptionLabel) {
		List<SelectItem> selectItems = getEnumSelectItems(baseEnums);
		SelectItem selectItem = new SelectItem(null, noSelectionOptionLabel);
		selectItem.setNoSelectionOption(true);
		selectItems.add(0,selectItem);
		return selectItems;
	}
}