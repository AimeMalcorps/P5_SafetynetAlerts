package com.safetynet.alerts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Child;
import com.safetynet.alerts.model.Childalert;
import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Person;

@Service
public class PersonService {

	private JsonReaderService jsonReader;

	private MedicalrecordService medicalrecordService;
	
	@Autowired
	public PersonService(JsonReaderService jsonReader, MedicalrecordService medicalrecordService) {
		super();
		this.jsonReader = jsonReader;
		this.medicalrecordService = medicalrecordService;
	}

	private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

	/* Get persons based on name */
	public List<Person> getPersons(String firstName, String lastName) {

		List<Person> personList = null;

		try {

			Data data = jsonReader.getData();

			personList = data
					.getPersons().stream().filter(
							item -> (item.getFirstName().equalsIgnoreCase(firstName) && lastName.isEmpty())
									|| (item.getLastName().equalsIgnoreCase(lastName) && firstName.isEmpty())
									|| (item.getFirstName().equalsIgnoreCase(firstName)
											&& item.getLastName().equalsIgnoreCase(lastName)))
					.collect(Collectors.toList());

			for (Person person : personList) {
				if (medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()) != null) {
					person.setMedicalrecord(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()));
				}
			}
			return personList;

		} catch (Exception ure) {
			logger.error("ERROR : " + personList);
			return personList;
		}
	}

	public Childalert getChildAlert(String adress) throws ParseException {
		
		List<Person> persons = jsonReader.getData().getPersons();

		List<Child> childList = new ArrayList<Child>();
		List<Person> othersList = new ArrayList<Person>();
		List<Person> personsAtAdress = persons.stream().filter(item -> (item.getAddress().equalsIgnoreCase(adress)))
				.collect(Collectors.toList());
		
		for (Person person : personsAtAdress) {

			person.setMedicalrecord(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()));
			Date birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(person.getMedicalrecord().getBirthdate());
			Date date = new Date();
			int age = jsonReader.calculateAge(birthdate, date);

			if (age < 18) {
				Child child = new Child();
				child.setAddress(person.getAddress());
				child.setCity(person.getCity());
				child.setEmail(person.getEmail());
				child.setFirstName(person.getFirstName());
				child.setLastName(person.getLastName());
				child.setZip(person.getZip());
				child.setPhone(person.getPhone());
				child.setMedicalrecord(person.getMedicalrecord());
				child.setAge(age);
				childList.add(child);
			} else {
				othersList.add(person);
			}	
		}
		
		if (childList.isEmpty()) {
			return null;
		} else {
			Childalert childalert = new Childalert();
			childalert.setListChildren(childList);
			childalert.setOthers(othersList);
			return childalert;
		}		
	}
	
	public List<String> getEmail(String city) {
		
		List<String> emailList = new ArrayList<String>();
		
		for(Person person : jsonReader.getData().getPersons()) {
			if (person.getCity().equals(city))
				emailList.add(person.getEmail());
		}
		
		return emailList;
	}
	
	/** CUD **/
	
	public Person createPerson(Person person) {
		
		if (person.getFirstName() != null && person.getLastName() != null) {
			if (getPersons(person.getFirstName(), person.getLastName()).size() == 0) {
				jsonReader.getData().getPersons().add(person);
			}
		}
		return getPersons(person.getFirstName(), person.getLastName()).get(0);
	}
	
	public Person updatePerson(Person person) {
		
		if (person.getFirstName() != null && person.getLastName() != null) {
			Person personToUpdate = getPersons(person.getFirstName(), person.getLastName()).get(0);
			
			jsonReader.getData().getPersons().remove(personToUpdate);
			
			if (person.getAddress() != null)
				personToUpdate.setAddress(person.getAddress());
			if (person.getCity() != null)
				personToUpdate.setCity(person.getCity());
			if (person.getZip() != 0)
				personToUpdate.setZip(person.getZip());
			if (person.getPhone() != null)
				personToUpdate.setPhone(person.getPhone());
			if (person.getEmail() != null)
				personToUpdate.setEmail(person.getEmail());
			
			jsonReader.getData().getPersons().add(person);
		}
		return getPersons(person.getFirstName(), person.getLastName()).get(0);
	}
	
	public List<Person> deletePerson(String firstName, String lastName) {
		
		if (firstName != null && lastName != null) {
			if(getPersons(firstName, lastName).get(0) != null)
				jsonReader.getData().getPersons().remove(getPersons(firstName, lastName).get(0));
		}
		return getPersons(firstName, lastName);
	}
	
}
