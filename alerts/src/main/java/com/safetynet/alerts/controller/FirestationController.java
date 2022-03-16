package com.safetynet.alerts.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Fire;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.PhoneNumber;
import com.safetynet.alerts.service.FirestationService;

@RestController
public class FirestationController {
	
	@Autowired
	private FirestationService firestationService;
	
	@GetMapping(value="/firestation")
	public Firestation getPersonCovered(@RequestParam Integer stationNumber) throws JSONException, IOException, ParseException {
		return firestationService.getPersonsCovered(stationNumber);
	}
	
	@GetMapping(value="/phoneAlert")
	public List<PhoneNumber> getPhoneNumbers(@RequestParam Integer firestationNumber) throws JSONException, IOException, ParseException {
		return firestationService.getPhoneNumberList(firestationNumber);
	}
	
	@GetMapping(value="/fire")
	public Fire getPhoneNumbers(@RequestParam String address) throws JSONException, IOException, ParseException {
		return firestationService.fireAlert(address);
	}
	
	@GetMapping(value="/flood/stations")
	public List<Firestation> getPhoneNumbers(@RequestParam List<Integer> stations) throws JSONException, IOException, ParseException {
		return firestationService.floodAlert(stations);
	}
	
	/****** CRUD *****/
	
	@PostMapping(value = "/create/firestation")
	public Firestation createFirestation(@RequestBody Firestation fs) throws JSONException, IOException {
		return firestationService.createFirestation(fs);
	}
	
	@GetMapping("/get/firestation")
	public Firestation get(@RequestParam String address) throws JSONException, IOException {
		return firestationService.getFirestation(address);
	}
	
	@PutMapping(value = "/update/firestation")
	public Firestation updateFirestation(@RequestBody Firestation fs) throws JSONException, IOException {
		return firestationService.updateFirestation(fs);
	}
	
	@DeleteMapping("/delete/firestation")
	public Firestation delete(@RequestParam String address) throws JSONException, IOException {
		return firestationService.deleteFirestation(address);
	}
	
	

}
