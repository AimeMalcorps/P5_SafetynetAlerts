package com.safetynet.alerts.controller;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationService;

@RestController
public class FirestationController {
	
	@Autowired
	private FirestationService firestationService;
	
	@GetMapping(value="/firestation")
	public Firestation getPersonCovered(@RequestParam Integer stationNumber) throws JSONException, IOException, ParseException {
		return firestationService.getPersons(stationNumber);
	}

}
