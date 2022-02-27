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

			personList = data
					.getPersons().stream().filter(
							item -> (item.getFirstName().equalsIgnoreCase(firstName) && lastName.isEmpty())
									|| (item.getLastName().equalsIgnoreCase(lastName) && firstName.isEmpty())
									|| (item.getFirstName().equalsIgnoreCase(firstName)
											&& item.getLastName().equalsIgnoreCase(lastName)))
					.collect(Collectors.toList());

			for (Person person : personList) {
				person.setBirthdate(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName())
						.getBirthdate());
				person.setMedications(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName())
						.getMedications());
				person.setAllergies(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName())
						.getAllergies());
			}

			return personList;

		} catch (Exception ure) {
			logger.info("ERROR");
			return personList;
		}
	}

	public Childalert getChildAlert(String adress) throws ParseException {
		logger.info(" " + adress);
		List<Person> persons = jsonReader.getData().getPersons();

		List<Child> childList = new ArrayList<Child>();
		List<Person> othersList = new ArrayList<Person>();
		List<Person> personsAtAdress = persons.stream().filter(item -> (item.getAddress().equalsIgnoreCase(adress)))
				.collect(Collectors.toList());
		
		//Iterator<String> iterator = list.iterator(); iterator.hasNext();
		for (Person person : personsAtAdress) {

			person.setBirthdate(medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()).getBirthdate());
			Date birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(person.getBirthdate());
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
				child.setBirthdate(person.getBirthdate());
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
	
}
