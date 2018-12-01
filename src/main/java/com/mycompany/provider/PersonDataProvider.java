package com.mycompany.provider;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.mycompany.domain.Person;

public interface PersonDataProvider {

	static String DEFAULT_TEST_BUCKET = "krish-json-bucket";
	
	static String KEY_NAME = "json/person.json";
	
	void loadPersons();

	Map<Long,Person> getPersonData();

	String updatePersons(List<Person> personList);

	default Map<Long, Person> getPersonMap(String personData) {
		
		Gson gson = new Gson();
		Person[] personArray = gson.fromJson(personData, Person[].class);
		List<Person> personList = Arrays.asList(personArray);
		Map<Long,Person> personMap = convertPersonToMap(personList);
		return personMap;
	}
	
	default  Map<Long, Person>  convertPersonToMap(List<Person> personList) {
		
		return personList.stream()
				.collect(Collectors.toMap(person -> person.getId(), person -> person));
	}
	
}