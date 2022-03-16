package com.safetynet.alerts.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Childalert;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping(value="/person-info")
	public List<Person> getPerson(@RequestParam String firstName, String lastName) throws JSONException, IOException {
		return personService.getPersonInfo(firstName, lastName);
	}
	
	@GetMapping(value="/childAlert")
	public Childalert getChildAlert(@RequestParam String adress) throws JSONException, IOException, ParseException {
		return personService.getChildAlert(adress);
	}
	
	@GetMapping(value="/communityEmail")
	public List<String> getPersonsEmail(@RequestParam String city) throws JSONException, IOException, ParseException {
		return personService.getEmail(city);
	}
}
