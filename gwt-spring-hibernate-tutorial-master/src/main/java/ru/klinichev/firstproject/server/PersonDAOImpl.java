package ru.klinichev.firstproject.server;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.klinichev.firstproject.client.PersonDAO;
import ru.klinichev.firstproject.shared.Person;

@Repository
@Transactional(readOnly = true)
public class PersonDAOImpl implements PersonDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Person> getAllPersons() {
		Session session = sessionFactory.openSession();
		String hql = "FROM Person";
		Query query = session.createQuery(hql);
		List<Person> persons = query.list();
		return persons;
	}

	@Override
	@Transactional(readOnly = false)
	public void insertPerson(Person person) {
		Session session = sessionFactory.openSession();
		session.save(person);
	}

}
