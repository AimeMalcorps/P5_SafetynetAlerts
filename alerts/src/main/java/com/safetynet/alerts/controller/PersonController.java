package com.safetynet.alerts.controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping(value="/personInfo")
	public List<Person> getPerson(@RequestParam String firstName, String lastName) throws JSONException, IOException {
		return personService.getPersonInfo(firstName, lastName);
	}
}
