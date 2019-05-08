package model;

public class SimplifiedIIT {
	
	private String itid;
	private String units;
	private String unitMeasure;
	private String validityDays;
	private String reorderPoint;
	private String notes;
	private String status;
	private String itn;
	
	public SimplifiedIIT(String id, String un, String um, String vd, String rp, String n, String st, String itna) {
		itid = id;
		units = un;
		unitMeasure = um;
		validityDays = vd;
		reorderPoint = rp;
		notes = n;
		status = st;
		itn = itna;
		System.out.println(itna + " >> " + notes + " >> " + reorderPoint);
	}

	public String getItid() {
		return itid;
	}

	public String getUnits() {
		return units;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public String getValidityDays() {
		return validityDays;
	}

	public String getReorderPoint() {
		return reorderPoint;
	}

	public String getNotes() {
		return notes;
	}

	public String getStatus() {
		return status;
	}

	public String getItn() {
		return itn;
	}
}
