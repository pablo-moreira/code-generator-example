package com.github.cg.example.core.model;


public interface IBaseEntityCloneable {
	abstract public <T extends IBaseEntityCloneable> T copy();
}