package com.github.cg.example.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<I> implements IBaseEntity<I>, Serializable {
		
	private static final long serialVersionUID = 1L;

	@Version
	@Column(nullable=false)
	private Integer version;
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
			
		BaseEntity<?> other = (BaseEntity<?>) obj;
		
		if (getId() == null) {
			return false;
		} 
		else if (!getId().equals(other.getId()))
			return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean isTransient() {
		return getId() == null;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}