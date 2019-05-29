package ru.klinichev.firstproject.client;

import java.util.List;

import ru.klinichev.firstproject.shared.Person;

public interface PersonDAO {
	
	public List<Person> getAllPersons();
	 
	public void insertPerson (Person person);

}
