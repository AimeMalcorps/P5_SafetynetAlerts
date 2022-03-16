package com.safetynet.alerts.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Data;

@Service
public class JsonReaderService {

	Data data;

	// Constructeur
	JsonReaderService() throws StreamReadException, DatabindException, MalformedURLException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		Data alert = objectMapper.readValue(
				new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json"),
				Data.class);
		data = alert;
		
	}

	public Data getData() {
		
		return data;
	}
	
	public int calculateAge(Date birthDate, Date currentDate) {
		
	    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
	    int d1 = Integer.parseInt(formatter.format(birthDate));                            
	    int d2 = Integer.parseInt(formatter.format(currentDate));
	    int age = ((d2 - d1) / 10000) + 1;
	    return age;                                                                        
	}
}
