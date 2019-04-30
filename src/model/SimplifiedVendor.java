package model;

public class SimplifiedVendor {
	
	private String id;
	private String name;
	private String phoneNumber;
	private String status;
	
	public SimplifiedVendor(String i, String n, String p, String s) {
		id = i;
		name = n;
		phoneNumber = p;
		status = s;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getStatus() {
		return status;
	}
}
