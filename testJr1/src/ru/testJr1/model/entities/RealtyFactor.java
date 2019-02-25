package ru.testJr1.model.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "realty_factors")
public class RealtyFactor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int realty_factor_id;
	private String name;
	private float multiplier;
	@OneToMany(mappedBy = "realtyFactor")
	private Collection<Contract> contracts;

	public int getRealty_factor_id() {
		return realty_factor_id;
	}
	public void setRealty_factor_id(int realty_factor_id) {
		this.realty_factor_id = realty_factor_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
}

