package ru.syrzhn.test1r.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "old_factors")
public class OldFactor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getOld_factor_id() {
		return old_factor_id;
	}
	public void setOld_factor_id(int old_factor_id) {
		this.old_factor_id = old_factor_id;
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
	private int old_factor_id;
	private String name;
	private float multiplier;
}
