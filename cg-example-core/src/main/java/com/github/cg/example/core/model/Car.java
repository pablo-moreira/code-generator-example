package com.github.cg.example.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="car")
@SequenceGenerator(name="seq_car", sequenceName="seq_car")
public class Car extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_car")
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="model_id")
	@ForeignKey(name="fk_car_model_id")
	private Model model;
	
	@Column(name="license_plate", nullable=false)
	private String licensePlate;

	@Temporal(TemporalType.DATE)
	@Column(name="fabrication_date", nullable=false)
	private Date fabricationDate;

	public Car() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public Date getFabricationDate() {
		return fabricationDate;
	}

	public void setFabricationDate(Date fabricationDate) {
		this.fabricationDate = fabricationDate;
	}
}
