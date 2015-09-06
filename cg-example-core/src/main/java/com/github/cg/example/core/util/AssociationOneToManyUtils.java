package com.github.cg.example.core.util;

import java.util.List;

import com.github.cg.example.core.model.IBaseEntity;

public class AssociationOneToManyUtils {

	public static <E extends IBaseEntity<?>> void updateAssociations(E associationNew, List<E> associations) {

		IBaseEntity<?> associationOld = null;
		
		for (IBaseEntity<?> associationItem : associations) {
			if (associationItem.getId().equals(associationNew.getId())) {					
				associationOld = associationItem;
				break;
			}
		}

		if (associationOld != null) {
			int index = associations.indexOf(associationOld);
			associations.set(index, associationNew);
		}
		else {				
			associations.add(associationNew);
		}
	}
}
