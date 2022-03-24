package com.safetynet.alerts.service;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.safetynet.alerts.model.Medicalrecord;

class MedicalrecordServiceTest {
	
	private static MedicalrecordService medicalrecordService;
	
	@BeforeEach
	private void setUp() throws Exception {
		JsonReaderService jsonReader = new JsonReaderService();
		medicalrecordService = new MedicalrecordService(jsonReader);
	}
	
	@Test
	public void testCreateMedicalrecord() {
		Medicalrecord medicalrecord = new Medicalrecord();
		medicalrecord.setFirstName("Kurt");
		medicalrecord.setLastName("Cobain");
		medicalrecord.setBirthdate("20/02/1967");
		assertNotNull(medicalrecordService.createMedicalrecord(medicalrecord));
	}
	
	@Test
	public void testGetMedicalrecord() {
		assertNotNull(medicalrecordService.getMedicalrecord("John", "Boyd"));
	}

}
