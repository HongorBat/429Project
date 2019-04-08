package model;

import javafx.beans.property.SimpleStringProperty;

public class MaleablePatron {
	
	private SimpleStringProperty patronId;
	private SimpleStringProperty name;
	private SimpleStringProperty address;
	private SimpleStringProperty city;
	private SimpleStringProperty stateCode;
	private SimpleStringProperty zip;
	private SimpleStringProperty email;
	private SimpleStringProperty dateOfBirth;
	private SimpleStringProperty status;
	
	public MaleablePatron(Patron p) {
		patronId = new SimpleStringProperty((String)p.getState("patronId"));
		name = new SimpleStringProperty((String)p.getState("name"));
		address = new SimpleStringProperty((String)p.getState("address"));
		city = new SimpleStringProperty((String)p.getState("city"));
		stateCode = new SimpleStringProperty((String)p.getState("stateCode"));
		zip = new SimpleStringProperty((String)p.getState("zip"));
		email = new SimpleStringProperty((String)p.getState("email"));
		dateOfBirth = new SimpleStringProperty((String)p.getState("dateOfBirth"));
		status = new SimpleStringProperty((String)p.getState("status"));
	}

	public SimpleStringProperty getPatronId() {
		return patronId;
	}

	public void setPatronId(SimpleStringProperty patronId) {
		this.patronId = patronId;
	}

	public SimpleStringProperty getName() {
		return name;
	}

	public void setName(SimpleStringProperty name) {
		this.name = name;
	}

	public SimpleStringProperty getAddress() {
		return address;
	}

	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}

	public SimpleStringProperty getCity() {
		return city;
	}

	public void setCity(SimpleStringProperty city) {
		this.city = city;
	}

	public SimpleStringProperty getStateCode() {
		return stateCode;
	}

	public void setStateCode(SimpleStringProperty stateCode) {
		this.stateCode = stateCode;
	}

	public SimpleStringProperty getZip() {
		return zip;
	}

	public void setZip(SimpleStringProperty zip) {
		this.zip = zip;
	}

	public SimpleStringProperty getEmail() {
		return email;
	}

	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	public SimpleStringProperty getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(SimpleStringProperty dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public SimpleStringProperty getStatus() {
		return status;
	}

	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

}
