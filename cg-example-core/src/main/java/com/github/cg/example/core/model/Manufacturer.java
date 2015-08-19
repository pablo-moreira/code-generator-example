package com.github.cg.example.core.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="manufacturer")
@SequenceGenerator(name="seq_manufacturer", sequenceName="seq_manufacturer")
public class Manufacturer extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_manufacturer")
	private Long id;
	
	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String email;

	@Column(nullable=false)
	private String phone;

	@OneToMany(cascade=CascadeType.ALL, mappedBy="manufacturer")
	private List<Model> models = new ArrayList<Model>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}
}
