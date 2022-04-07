package com.safetynet.alerts.service;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.Firestation;

class FirestationServiceTest {

	private static FirestationService firestationService;
	
	
	@BeforeEach
	private void setUp() throws Exception {
		JsonReaderService jsonReader = new JsonReaderService();
		MedicalrecordService medicalrecordService = new MedicalrecordService(jsonReader);
		firestationService = new FirestationService(jsonReader, medicalrecordService);
	}
	
	@Test
	public void testPersonsCovered() throws ParseException {
		int b = firestationService.getPersonsCovered(1).getAdults();
		assertEquals(5, b);
	}
	
	@Test
	public void testPhoneNumbersList() {
		assertEquals(6, firestationService.getPhoneNumberList(1).size());
	}
	
	@Test
	public void testFireAlert() {
		assertEquals(5, firestationService.fireAlert("1509 Culver St").getPersonList().size());
	}
	
	@Test
	public void testFloodAlert() throws ParseException {
		List<Integer> listFstNumber = new ArrayList<Integer>();
		listFstNumber.add(1);
		List<Firestation> firestationList = firestationService.floodAlert(listFstNumber);
		assertEquals(1, firestationList.size());
	}
	
	@Test
	public void testCreateFirestation() {
		Firestation fs = new Firestation();
		fs.setAddress("New York");
		fs.setStation(46);
		
		assertEquals("New York", firestationService.createFirestation(fs).getAddress());
		int b = firestationService.createFirestation(fs).getStation();
		assertEquals(46, b);
	}
	
	@Test
	public void testGetFirestation() {
		assertNotNull(firestationService.getFirestation("1509 Culver St"));
	}
	
	@Test
	public void testUpdateFirestation() {
		Firestation fs = new Firestation();
		fs.setAddress("1509 Culver St");
		fs.setStation(46);
		int b = firestationService.updateFirestation(fs).getStation();
		assertEquals(46, b);
	}
	
	@Test
	public void testDeleteFirestation() {
		assertNull(firestationService.deleteFirestation("1509 Culver St"));
	}


}
