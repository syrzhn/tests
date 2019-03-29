package ru.syrzhn.test1r.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contracts_full_view")
public class ContractFullView {
	private float tender; 
	public float getTender() {
		return tender;
	}
	public void setTender(float tender) {
		this.tender = tender;
	}
	public String getDate_of_creation() {
		return date_of_creation;
	}
	public void setDate_of_creation(String date_of_creation) {
		this.date_of_creation = date_of_creation;
	}
	public String getDate_of_actual() {
		return date_of_actual;
	}
	public void setDate_of_actual(String date_of_actual) {
		this.date_of_actual = date_of_actual;
	}
	public String getRealty_factor_name() {
		return realty_factor_name;
	}
	public void setRealty_factor_name(String realty_factor_name) {
		this.realty_factor_name = realty_factor_name;
	}
	public int getOld_year() {
		return old_year;
	}
	public void setOld_year(int old_year) {
		this.old_year = old_year;
	}
	public int getSquare() {
		return square;
	}
	public void setSquare(int square) {
		this.square = square;
	}
	public String getDate_of_calculate() {
		return date_of_calculate;
	}
	public void setDate_of_calculate(String date_of_calculate) {
		this.date_of_calculate = date_of_calculate;
	}
	public float getPrize() {
		return prize;
	}
	public void setPrize(float prize) {
		this.prize = prize;
	}
	@Id
	public int getContract_id() {
		return contract_id;
	}
	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
	}
	public String getDate_of_creation1() {
		return date_of_creation1;
	}
	public void setDate_of_creation1(String date_of_creation1) {
		this.date_of_creation1 = date_of_creation1;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getPassport_serial() {
		return passport_serial;
	}
	public void setPassport_serial(String passport_serial) {
		this.passport_serial = passport_serial;
	}
	public String getPassport_number() {
		return passport_number;
	}
	public void setPassport_number(String passport_number) {
		this.passport_number = passport_number;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	private String date_of_creation; 
	private String date_of_actual; 
	private String realty_factor_name; 
	private int old_year; 
	private int square; 
	private String date_of_calculate; 
	private float prize; 
	private int contract_id; 
	private String date_of_creation1; 
	private String fio; 
	private String birth_date; 
	private String passport_serial; 
	private String passport_number; 
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
	private String comment; 
}
