package ru.syrzhn.test1r.model.entities;

import static ru.syrzhn.test1r.model.Utils.stringToSqliteDate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "persons")
public class Person {
	public Person() {}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int person_id;
	private String fio;
	private String birth_date;
	private String passport_serial;
	private String passport_number;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
	private Set<Contract> contracts = new HashSet<>();
	
	public Set<Contract> getContracts() {
		return contracts;
	}
	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}
	public int getPerson_id() {
		return person_id;
	}
	public void setPerson_id(int person_id) {
		this.person_id = person_id;
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
		this.birth_date = stringToSqliteDate(birth_date);
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
}
