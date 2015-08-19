package com.github.cg.example.core.util;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentBag;

public class HibernateUtils {
	
	public static void initializeCollections(Object entidade) {
	
		List<Field> fields = ReflectionUtils.getFieldsRecursive(entidade.getClass());
				
		for (Field field : fields) {			
			try {
				field.setAccessible(true);
				Object fieldValue = field.get(entidade);
				if (fieldValue instanceof PersistentBag) {									
					Hibernate.initialize(fieldValue);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				field.setAccessible(false);
			}
		}	
	}
}
