package model;

public class SimplifiedVIIT {
	
	private String id;
	private String name;
	private String price;
	private String dlu;
	
	public SimplifiedVIIT(String i, String n, String p, String s) {
		id = i;
		name = n;
		price = p;
		dlu = s;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public String getDlu() {
		return dlu;
	}
}
