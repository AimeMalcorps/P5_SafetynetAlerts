package com.safetynet.alerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Person;

@Service
public class PersonService {
	
	@Autowired
	JsonReaderService jsonReader;
	
	@Autowired
	MedicalrecordService medicalrecordService;
	
	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
	
	/* Get persons based on name */
	public List<Person> getPersonInfo(String firstName, String lastName) {
		
		List<Person> personList = null;
		
		try {
			
			Data data = jsonReader.getData();
			
			if (!firstName.isEmpty() && !lastName.isEmpty()) {
				personList = data.getPersons().stream()
						.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
						.collect(Collectors.toList());
			}
			
			if (firstName.isEmpty()) {
				personList = data.getPersons().stream()
						.filter(item -> item.getLastName().equals(lastName))
						.collect(Collectors.toList());
			} 
			
			if (lastName.isEmpty()) {
				personList = data.getPersons().stream()
						.filter(item -> item.getFirstName().equals(firstName))
						.collect(Collectors.toList());
			}
			
			for (Person person : personList) {
				person.setBirthdate(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()).getBirthdate());
				person.setMedications(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()).getMedications());
				person.setAllergies(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()).getAllergies());
			}
			
			return personList;
					
		} catch (Exception ure) {
			logger.info("ERROR");
			return personList;
		}	
	}
	
}
