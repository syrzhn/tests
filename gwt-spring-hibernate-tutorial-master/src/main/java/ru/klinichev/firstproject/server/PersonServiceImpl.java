package ru.klinichev.firstproject.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.klinichev.firstproject.client.PersonDAO;
import ru.klinichev.firstproject.client.PersonService;
import ru.klinichev.firstproject.shared.Person;

@Service("personService")
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDAO personDao;

	@Override
	public List<Person> getAllPersons() {
		List<Person> persons = personDao.getAllPersons();
		return persons;
	}

	@Override
	public void insert(Person person) {
		personDao.insertPerson(person);		
	}

}
