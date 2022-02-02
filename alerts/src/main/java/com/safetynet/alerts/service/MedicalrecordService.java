package com.safetynet.alerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Data;
import com.safetynet.alerts.model.Medicalrecord;

@Service
public class MedicalrecordService {
	
	@Autowired
	JsonReaderService jsonReader;
	
	public Medicalrecord getMedicalrecord(String firstName, String lastName) {
		
		try {
			
			Data data = jsonReader.getData();
			List<Medicalrecord> medicalrecord = data.getMedicalrecords().stream()
					.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
					.collect(Collectors.toList());
			
			return medicalrecord.get(0);
			
		} catch (Exception ure) {
			return null;
		}	
	}

}
