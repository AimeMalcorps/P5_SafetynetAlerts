package com.safetynet.alerts.service;

import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Data;

@Service
public class JsonReaderService {
	
	public Data getData() throws JSONException, IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();	
		Data alert = objectMapper.readValue(new URL("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json"), Data.class);
	    return alert;
	    }
}
