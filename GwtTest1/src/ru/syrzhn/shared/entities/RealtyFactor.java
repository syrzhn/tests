package ru.syrzhn.shared.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "realty_factors")
public class RealtyFactor {
	public RealtyFactor(){}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int realty_factor_id;
	private String name;
	private float multiplier;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "realtyFactor")
	private Set<Contract> contracts;

	public Set<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}
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

