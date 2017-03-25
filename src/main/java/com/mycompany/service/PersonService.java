package com.mycompany.service;

import java.util.List;

import com.mycompany.domain.AddResponse;
import com.mycompany.domain.DeleteResponse;
import com.mycompany.domain.Person;


public interface PersonService {

	public Person getPersonDetails(Long personId);
	
	public List<Person> getAllPersons();

	public void loadPersons();
	
	public AddResponse addNewPerson(Person person);

	public DeleteResponse deletePerson(Long id);
	
}
