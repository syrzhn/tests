package ru.testJr1.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contracts")
public class Contracts {
	@Id
	public int getContract_id() {
		return contract_id;
	}
	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getActual_date() {
		return actual_date;
	}
	public void setActual_date(String actual_date) {
		this.actual_date = actual_date;
	}
	public float getTender() {
		return tender;
	}
	public void setTender(float tender) {
		this.tender = tender;
	}
	public float getPrize() {
		return prize;
	}
	public void setPrize(float prize) {
		this.prize = prize;
	}
	public int getRealty_factor_id() {
		return realty_factor_id;
	}
	public void setRealty_factor_id(int realty_factor_id) {
		this.realty_factor_id = realty_factor_id;
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
	public String getCalculate_date() {
		return calculate_date;
	}
	public void setCalculate_date(String calculate_date) {
		this.calculate_date = calculate_date;
	}
	public int getFio_id() {
		return fio_id;
	}
	public void setFio_id(int fio_id) {
		this.fio_id = fio_id;
	}
	public int getAdress_id() {
		return adress_id;
	}
	public void setAdress_id(int adress_id) {
		this.adress_id = adress_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}	

	private int contract_id;
	private float tender;
	private String create_date;
	private String actual_date;
	private float prize;
	private int realty_factor_id;
	private int old_year;
	private int square;
	private String calculate_date;
	private int fio_id;
	private int adress_id;
	private String comment;
}
