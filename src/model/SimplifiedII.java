package model;

public class SimplifiedII {
	
	private String id;
	private String barcode;
	private String iitn;
	private String vid;
	private String dateReceived;
	private String dateOfLastUse;
	private String notes;
	private String status;
	
	public SimplifiedII(String id, String barcode, String iitn, String vid, String dateReceived, String dateOfLastUse, String notes, String status) {
		this.id = id;
		this.barcode = barcode;
		this.iitn = iitn;
		this.vid = vid;
		this.dateReceived = dateReceived;
		this.dateOfLastUse = dateOfLastUse;
		this.notes = notes;
		this.status = status;
	}
	

	public String getId() {
		return id;
	}

	public String getBarcode() {
		return barcode;
	}

	public String getIitn() {
		return iitn;
	}

	public String getVid() {
		return vid;
	}

	public String getDateReceived() {
		return dateReceived;
	}

	public String getDateOfLastUse() {
		return dateOfLastUse;
	}

	public String getNotes() {
		return notes;
	}

	public String getStatus() {
		return status;
	}
}
