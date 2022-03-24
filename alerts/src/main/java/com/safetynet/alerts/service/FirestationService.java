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
import com.safetynet.alerts.model.Fire;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.model.PhoneNumber;

@Service
public class FirestationService {

	JsonReaderService jsonReader;

	MedicalrecordService medicalrecordService;
	
	
	@Autowired
	public FirestationService(JsonReaderService jsonReader, MedicalrecordService medicalrecordService) {
		super();
		this.jsonReader = jsonReader;
		this.medicalrecordService = medicalrecordService;
	}

	/** PERSONNES COUVERTES PAR LA CASERNE **/
	public Firestation getPersonsCovered(Integer stationNumber) throws ParseException {

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

	/** LIST PHONE NUMBERS **/
	public List<PhoneNumber> getPhoneNumberList(Integer stationNumber) {

		List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
		Data data = jsonReader.getData();
		List<String> addresses = new ArrayList<String>();

		for (Firestation firestation : data.getFirestations()) {
			if (firestation.getStation() == stationNumber) {
				addresses.add(firestation.getAddress());
			}
		}

		List<Person> personList = data.getPersons().stream().filter(item -> addresses.contains(item.getAddress()))
				.collect(Collectors.toList());

		for (Person person : personList) {
			PhoneNumber phoneNumber = new PhoneNumber();
			phoneNumber.setFirstName(person.getFirstName());
			phoneNumber.setLastName(person.getLastName());
			phoneNumber.setPhone(person.getPhone());
			phoneNumberList.add(phoneNumber);
		}

		return phoneNumberList;
	}

	/** INCENDIE **/
	public Fire fireAlert(String address) {

		Fire fire = new Fire();
		Data data = jsonReader.getData();
		int firestationNumber = 0;

		for (Firestation firestation : data.getFirestations()) {
			if (firestation.getAddress().equals(address))
				firestationNumber = firestation.getStation();
		}

		List<Person> personList = data.getPersons().stream().filter(item -> item.getAddress().equals(address))
				.collect(Collectors.toList());

		fire.setFirestationNumber(firestationNumber);
		fire.setPersonList(personList);
		return fire;
	}

	/** INONDATIONS **/
	public List<Firestation> floodAlert(List<Integer> stationNumberList) throws ParseException {

		List<Firestation> allPersonsList = new ArrayList<Firestation>();

		for (int stationNumber : stationNumberList) {
			allPersonsList.add(getPersonsCovered(stationNumber));
		}

		return allPersonsList;
	}

	/*********************** CRUD *****************************/

	/** CREATE **/
	public Firestation createFirestation(Firestation fsToCreate) {
		
		if (fsToCreate.getAddress() != null && fsToCreate.getStation() != null) {
			if(jsonReader.getData().getFirestations().stream()
				.filter(fs -> fs.getAddress().equalsIgnoreCase(fsToCreate.getAddress())).collect(Collectors.toList()).isEmpty()) {
				jsonReader.getData().getFirestations().add(fsToCreate);
			}
		}

		return getFirestation(fsToCreate.getAddress());
	}

	/** READ **/
	public Firestation getFirestation(String address) {

		List<Firestation> fsList = jsonReader.getData().getFirestations().stream()
				.filter(fs -> fs.getAddress().equalsIgnoreCase(address)).collect(Collectors.toList());

		if (!fsList.isEmpty()) {
			return fsList.get(0);
		} else {
			return null;
		}
	}

	/** UPDATE **/
	public Firestation updateFirestation(Firestation fsToUpdate) {
		
		jsonReader.getData().getFirestations()
				.removeIf(fs -> fs.getAddress().equalsIgnoreCase(fsToUpdate.getAddress()));
		
		jsonReader.getData().getFirestations().add(fsToUpdate);

		return getFirestation(fsToUpdate.getAddress());
	}

	/** DELETE **/
	public Firestation deleteFirestation(String address) {

		jsonReader.getData().getFirestations().removeIf(fs -> fs.getAddress().equalsIgnoreCase(address));

		return getFirestation(address);
	}

}
