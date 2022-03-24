package com.safetynet.alerts.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.model.Medicalrecord;

@Service
public class MedicalrecordService {

	private JsonReaderService jsonReader;
	
	@Autowired
	public MedicalrecordService(JsonReaderService jsonReader) {
		super();
		this.jsonReader = jsonReader;
	}
	
	/****** CRUD ******/

	public Medicalrecord createMedicalrecord(Medicalrecord mr) {

		if (mr.getFirstName() != null && mr.getLastName() != null) {
			if (jsonReader.getData().getMedicalrecords().stream()
					.filter(item -> item.getFirstName().equals(mr.getFirstName())
							&& item.getLastName().equals(mr.getLastName()))
					.collect(Collectors.toList()).isEmpty()) {
				jsonReader.getData().getMedicalrecords().add(mr);
			}
		}
		return getMedicalrecord(mr.getFirstName(), mr.getLastName());
	}

	public Medicalrecord getMedicalrecord(String firstName, String lastName) {

		try {
			List<Medicalrecord> medicalrecord = jsonReader.getData().getMedicalrecords().stream()
					.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
					.collect(Collectors.toList());

			return medicalrecord.get(0);

		} catch (Exception ure) {
			return null;
		}
	}

	public Medicalrecord updateMedicalrecord(Medicalrecord mrToUpdate) {

		if (mrToUpdate.getFirstName() != null && mrToUpdate.getLastName() != null) {
			Medicalrecord mr = getMedicalrecord(mrToUpdate.getFirstName(), mrToUpdate.getLastName());
			jsonReader.getData().getMedicalrecords().remove(mr);

			if (mrToUpdate.getBirthdate() != null)
				mr.setBirthdate(mrToUpdate.getBirthdate());
			if (mrToUpdate.getAllergies() != null)
				mr.setAllergies(mrToUpdate.getAllergies());
			if (mrToUpdate.getMedications() != null)
				mr.setMedications(mrToUpdate.getMedications());

			jsonReader.getData().getMedicalrecords().add(mr);
		}

		return getMedicalrecord(mrToUpdate.getFirstName(), mrToUpdate.getLastName());
	}

	public Medicalrecord deleteMedicalrecord(String firstName, String lastName) {

		jsonReader.getData().getMedicalrecords().removeIf(
				fs -> fs.getFirstName().equalsIgnoreCase(firstName) && fs.getLastName().equalsIgnoreCase(lastName));
		
		return getMedicalrecord(firstName, lastName);
	}

}
