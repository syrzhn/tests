package ru.testJr1.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "square_factors")
public class SquareFactor {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getSquare_factor_id() {
		return square_factor_id;
	}
	public void setSquare_factor_id(int square_factor_id) {
		this.square_factor_id = square_factor_id;
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
	private int square_factor_id;
	private String name;
	private float multiplier;
}
