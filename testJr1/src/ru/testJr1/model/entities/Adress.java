package ru.testJr1.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "adresses")
public class Adress {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getAdress_id() {
		return adress_id;
	}
	
	@OneToOne(mappedBy = "adress")
	private Contract owner;

	public void setAdress_id(int adress_id) {
		this.adress_id = adress_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getStatecount() {
		return statecount;
	}
	public void setStatecount(String statecount) {
		this.statecount = statecount;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	
	private int adress_id;
	private String state;
	private String idx;
	private String statecount;
	private String district;
	private String city;
	private String street;
	private String building;
	private String corp;
	private String structure;
	private String house;
}
