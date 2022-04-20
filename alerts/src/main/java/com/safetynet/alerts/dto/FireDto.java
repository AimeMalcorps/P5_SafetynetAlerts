package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.Person;

public class FireDto {
	
	private int firestationNumber;
	private List<Person> personList;
	
	public int getFirestationNumber() {
		return firestationNumber;
	}
	public void setFirestationNumber(int firestationNumber) {
		this.firestationNumber = firestationNumber;
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

}
