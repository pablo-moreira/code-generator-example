package com.github.cg.example.core.model;


public interface IBaseEntity<I> {
	
	abstract public I getId();
	
	abstract public boolean isTransient();
}
