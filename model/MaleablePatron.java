package model;

import javafx.beans.property.SimpleStringProperty;

public class MaleablePatron implements Comparable {
	
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

	public String getPatronId() {
		return patronId.get();
	}

	public void setPatronId(String x) {
		patronId.set(x);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String x) {
		name.get();
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String x) {
		address.get();
	}

	public String getCity() {
		return city.get();
	}

	public void setCity(String x) {
		city.get();
	}

	public String getStateCode() {
		return stateCode.get();
	}

	public void setStateCode(String x) {
		this.stateCode.get();
	}

	public String getZip() {
		return zip.get();
	}

	public void setZip(String x) {
		this.zip.set(x);
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.get();
	}

	public String getDateOfBirth() {
		return dateOfBirth.get();
	}

	public void setDateOfBirth(String x) {
		this.dateOfBirth.get();
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String x) {
		this.status.get();
	}

	@Override
	public int compareTo(Object arg0) {
		MaleablePatron p = (MaleablePatron)arg0;
		return this.getName().compareTo(p.getName());
	}
}