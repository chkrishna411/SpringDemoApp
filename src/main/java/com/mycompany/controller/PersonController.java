package com.mycompany.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.domain.AddResponse;
import com.mycompany.domain.DeleteResponse;
import com.mycompany.domain.Person;
import com.mycompany.service.PersonService;

@RestController
public class PersonController {

	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;

	/**
	 * Get Person By Id
	 * @param id
	 * @return
	 */
	@RequestMapping("/person/{id}")
	public Person getPerson(@PathVariable("id") Long id) {
		
		logger.info("Person Retrieval request received with id: "+id);
		return personService.getPersonDetails(id);
	}
	
	/**
	 * Get List of Persons
	 * 
	 * @return
	 */
	@RequestMapping("/persons")
	public List<Person> getPersons() {
		
		logger.info("Retrieving All Persons");
		return personService.getAllPersons();
	}
	
	/**
	 * Post All Persons
	 * 
	 */
	@PostMapping("/persons")
	public void loadPersons() {
		personService.loadPersons();
	}
	
	/**
	 * Put Person 
	 * 
	 * @param person
	 * @return
	 */
	@PutMapping("/person")
	public AddResponse addPerson(@RequestBody Person person) {
		return personService.addNewPerson(person);
	}
	
	/**
	 * Delete person by Id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/person/{id}")
	public DeleteResponse deletePerson(@PathVariable("id") Long id) {
		return personService.deletePerson(id);
	}
	
}
