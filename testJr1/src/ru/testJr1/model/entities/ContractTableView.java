package ru.testJr1.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contracts_table_view")
public class ContractTableView {
	@Id
	public int getContract_id() {
		return contract_id;
	}
	public void setContract_id(int contract_id) {
		this.contract_id = contract_id;
	}
	public String getDate_of_creation() {
		return date_of_creation;
	}
	public void setDate_of_creation(String date_of_creation) {
		this.date_of_creation = date_of_creation;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	public float getPrize() {
		return prize;
	}
	public void setPrize(float prize) {
		this.prize = prize;
	}
	public String getActual_time() {
		return actual_time;
	}
	public void setActual_time(String actual_time) {
		this.actual_time = actual_time;
	}
	private int contract_id;
	private String date_of_creation;
	private String fio;
	private float prize;
	private String actual_time;
}
