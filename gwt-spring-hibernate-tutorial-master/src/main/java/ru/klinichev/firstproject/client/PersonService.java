package ru.klinichev.firstproject.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ru.klinichev.firstproject.shared.Person;

@RemoteServiceRelativePath("springGwtServices/personService")
public interface PersonService extends RemoteService {
	
	public List<Person> getAllPersons();
	 
	public void insert(Person person);

}
