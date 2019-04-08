package model;

import javafx.beans.property.SimpleStringProperty;

public class MutableBook implements Comparable {

	private SimpleStringProperty id;
	private SimpleStringProperty author;
	private SimpleStringProperty title;
	private SimpleStringProperty pubYear;
	private SimpleStringProperty status;
	
	public MutableBook(Book b) {
		String temp = (String) b.getState("bookId");
		id = new SimpleStringProperty(temp);
		
		temp = (String) b.getState("author");
		author = new SimpleStringProperty(temp);
		
		temp = (String) b.getState("title");
		title = new SimpleStringProperty(temp);
	
		temp = (String) b.getState("pubYear");
		pubYear = new SimpleStringProperty(temp);
		
		temp = (String) b.getState("status");
		status = new SimpleStringProperty(temp);
	}
	
	public String getId() {
		return id.get();
	}

	public void setId(String x) {
		id.set(x);
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String x) {
		author.set(x);
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String x) {
		title.set(x);
	}

	public String getPubYear() {
		return pubYear.get();
	}

	public void setPubYear(String x) {
		pubYear.set(x);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String x) {
		status.set(x);
	}
	
	@Override
	public int compareTo(Object arg0) {
		MutableBook mb = (MutableBook) arg0;
		return getAuthor().compareTo(mb.getAuthor());
	}
}
