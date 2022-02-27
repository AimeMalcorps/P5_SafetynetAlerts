package com.safetynet.alerts.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;

@Service
public class FirestationService {

	@Autowired
	JsonReaderService jsonReader;

	@Autowired
	MedicalrecordService medicalrecordService;

	public Firestation getPersons(Integer stationNumber) throws ParseException {

		Data data = jsonReader.getData();
		List<String> addresses = new ArrayList<String>();

		for (Firestation firestation : data.getFirestations()) {
			if (firestation.getStation() == stationNumber) {
				addresses.add(firestation.getAddress());
			}
		}

		List<Person> personList = data.getPersons().stream().filter(item -> addresses.contains(item.getAddress()))
				.collect(Collectors.toList());

		Date date = new Date();
		int adults = 0;
		int children = 0;

		for (Person person : personList) {
			person.setBirthdate(
					medicalrecordService.getMedicalrecord(person.getFirstName(), person.getLastName()).getBirthdate());

			Date birthdate = new SimpleDateFormat("dd/MM/yyyy").parse(person.getBirthdate());
			int age = jsonReader.calculateAge(birthdate, date);
			
			if (age >= 18) {
				adults++;
			} else {
				children++;
			}
		}
		
		Firestation firestation = new Firestation();
		firestation.setAddress(String.join(" / ", addresses));
		firestation.setStation(stationNumber);
		firestation.setPersons(personList);
		firestation.setAdults(adults);
		firestation.setChildren(children);

		return firestation;
	}

	public Firestation getFirestationFromNumber(Integer number) {

		Data data = jsonReader.getData();

		Firestation firestation = new Firestation();

		for (Firestation item : data.getFirestations()) {
			if (item.getStation().equals(number))
				firestation = item;
		}
		return firestation;
	}

}
