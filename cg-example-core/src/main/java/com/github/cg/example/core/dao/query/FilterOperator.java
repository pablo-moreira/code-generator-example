package com.github.cg.example.core.dao.query;

public enum FilterOperator {
		
	EQUALS,	
	NOT_EQUALS,

	GT,
	GE,
	LT,
	LE,
	
	IS_NULL,
	IS_NOT_NULL,
	
	BETWEEN,
	
	CONTAINS,
	START_WITH,
	END_WITH,
	NOT_CONTAINS,
	NOT_START_WITH,
	NOT_END_WITH,

	IN,
	NOT_IN,
	
	LIKE,
	ILIKE,	
	NOT_LIKE,
	NOT_ILIKE	
}