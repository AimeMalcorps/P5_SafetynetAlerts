package com.safetynet.alerts.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.Person;

class PersonServiceTest {
	
	private static PersonService personService;
	
	@BeforeEach
	private void setUp() throws Exception {
		JsonReaderService jsonReader = new JsonReaderService();
		MedicalrecordService medicalrecordService = new MedicalrecordService(jsonReader);
		personService = new PersonService(jsonReader, medicalrecordService);
	}
	

	@Test
	public void testGetPersons() {
		assertEquals(1, personService.getPersons("john", "boyd").size());
	}
	
	@Test
	public void testGetChildAlert() throws ParseException {
		assertEquals(3, personService.getChildAlert("1509 Culver St").getOthers().size());
	}
	
	@Test
	public void testEmailList() {
		assertEquals(23, personService.getEmail("Culver").size());
	}
	
	@Test
	public void testCreatePerson() {
		Person person = new Person();
		person.setFirstName("Kurt");
		person.setLastName("Cobain");
		personService.createPerson(person);
		assertEquals(1, personService.getPersons("Kurt", "Cobain").size());
	}
	
	@Test
	public void testUpdatePerson() {
		Person person = new Person();
		person.setFirstName("John");
		person.setLastName("Boyd");
		person.setAddress("Times Square");
		person.setCity("New York");
		person.setZip(6666);
		person.setPhone("010101");
		person.setEmail("fake@email.fr");
		personService.updatePerson(person);
		assertEquals("Times Square", personService.getPersons("John", "Boyd").get(0).getAddress());
		assertEquals("New York", personService.getPersons("John", "Boyd").get(0).getCity());
		assertEquals(6666, personService.getPersons("John", "Boyd").get(0).getZip());
		assertEquals("010101", personService.getPersons("John", "Boyd").get(0).getPhone());
		assertEquals("fake@email.fr", personService.getPersons("John", "Boyd").get(0).getEmail());
	}
	
	@Test
	public void testDeletePerson() {
		personService.deletePerson("John", "Boyd");
		assertTrue(personService.getPersons("John", "Boyd").isEmpty());
	}
	
	

}
