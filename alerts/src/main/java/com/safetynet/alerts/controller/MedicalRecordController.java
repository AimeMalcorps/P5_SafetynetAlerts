package com.safetynet.alerts.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.service.MedicalrecordService;

@RestController
public class MedicalRecordController {
	
	@Autowired
	private MedicalrecordService medicalrecordService;
	
	/****** CRUD *****/
	
	@PostMapping(value = "/create/medicalrecord")
	public Medicalrecord createFirestation(@RequestBody Medicalrecord mr) throws JSONException, IOException {
		return medicalrecordService.createMedicalrecord(mr);
	}
	
	@GetMapping("/get/medicalrecord")
	public Medicalrecord get(@RequestParam String firstName, String lastName) throws JSONException, IOException {
		return medicalrecordService.getMedicalrecord(firstName, lastName);
	}
	
	@PutMapping(value = "/update/medicalrecord")
	public Medicalrecord updateFirestation(@RequestBody Medicalrecord mr) throws JSONException, IOException {
		return medicalrecordService.updateMedicalrecord(mr);
	}
	
	@DeleteMapping("/delete/medicalrecord")
	public Medicalrecord delete(@RequestParam String firstName, String lastName) throws JSONException, IOException {
		return medicalrecordService.deleteMedicalrecord(firstName, lastName);
	}
}
