package com.safetynet.alerts.model;

import java.util.List;

public class Childalert {
	
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
