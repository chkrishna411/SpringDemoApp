package com.mycompany.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.domain.AddResponse;
import com.mycompany.domain.DeleteResponse;
import com.mycompany.domain.Person;
import com.mycompany.exception.InvalidException;
import com.mycompany.provider.PersonDataProvider;
import com.mycompany.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonDataProvider client;
	
	private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

	public void loadPersons() {		
		client.loadPersons();
	}
	
	@Override
	public Person getPersonDetails(Long personId) {
		Map<Long, Person> personMap = client.getPersonData();
		logger.info("Checking for person in the request map: "+personId);
		if(personMap.get(personId) == null) {
			throw new InvalidException(400, "Key unavailable", "Key Not available in the list, call /persons to check the list");
		}
		return personMap.get(personId);
	}

	public AddResponse addNewPerson(Person person) {
		
		Map<Long, Person> personMap = client.getPersonData();
		if(personMap == null) {
			personMap = new HashMap<>();
			personMap.put(person.getId(), person);
		} else {
			Person existingPerson = personMap.get(person.getId());
			if(existingPerson != null) {
				logger.info("Person already available");
				long maxId = this.getMaxId(personMap.keySet());
				throw new InvalidException(404, "Key Exists", "Enter Id greater than "+maxId);
			}
			personMap.put(person.getId(), person);
		}
		client.updatePersons(personList(personMap));
		AddResponse response = new AddResponse(person.getId(), "Added Successfully");
		return response;
	}
	
	public long getMaxId(Set<Long> keys) {
		
		Optional<Long> maxKey = keys.stream().max((k1,k2) -> Long.compare(k1, k2));
		return maxKey.get();
	}
	
	public List<Person> getAllPersons() {
		Map<Long, Person> personMap = client.getPersonData();
		System.out.println("person list: "+personMap);
		return personList(personMap);
	}

	private List<Person> personList(Map<Long, Person> personMap) {
		return personMap.values()
		.stream().collect(Collectors.toList());
	}

	@Override
	public DeleteResponse deletePerson(Long id) {
		Map<Long, Person> personMap = client.getPersonData();
		if(personMap == null || personMap.get(id) == null) {
			throw new InvalidException(400, "Key unavailable", "Key Not available in the list, call /persons to check the list");
		}
		Person person = personMap.remove(id);
		logger.info("person list after removing: "+personMap);
		client.updatePersons(personList(personMap));
		DeleteResponse response = new DeleteResponse();
		response.setId(person.getId());
		response.setMessage("Deleted Successfully");
		return response;
	}

}
