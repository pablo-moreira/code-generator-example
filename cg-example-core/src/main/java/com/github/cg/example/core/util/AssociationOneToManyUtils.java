package com.github.cg.example.core.util;

import java.util.Collection;
import java.util.List;

import com.github.cg.example.core.model.IBaseEntity;

public class AssociationOneToManyUtils {

	public static <E extends IBaseEntity<?>> void updateAssociations(E associationNew, Collection<E> associations) {

		IBaseEntity<?> associationOld = null;
		
		for (IBaseEntity<?> associationItem : associations) {
			if (associationItem.getId().equals(associationNew.getId())) {					
				associationOld = associationItem;
				break;
			}
		}

		if (associationOld != null) {
			
			if (associations instanceof List) {
				List<E> associationsList = (List<E>) associations;
				int index = associationsList.indexOf(associationOld);
				associationsList.set(index, associationNew);	
			}
			else {
				associations.add(associationNew);	
			}
		}
		else {				
			associations.add(associationNew);
		}
	}
}
