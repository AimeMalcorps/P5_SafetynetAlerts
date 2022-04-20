package com.safetynet.alerts.dto;

import java.util.List;

import com.safetynet.alerts.model.Child;
import com.safetynet.alerts.model.Person;

public class ChildalertDto {
	
	private List<Child> listChildren;
	private List<Person> others;
	
	public List<Child> getListChildren() {
		return listChildren;
	}
	public void setListChildren(List<Child> listChildren) {
		this.listChildren = listChildren;
	}
	public List<Person> getOthers() {
		return others;
	}
	public void setOthers(List<Person> others) {
		this.others = others;
	}
}
