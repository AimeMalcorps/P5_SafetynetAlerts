package com.safetynet.alerts.model;

import java.util.List;

public class Fire {
	
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
