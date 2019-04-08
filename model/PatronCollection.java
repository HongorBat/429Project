package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import database.JDBCBroker;

public class PatronCollection extends EntityBase {
	

	private Vector<Patron> patronList;
    
    public PatronCollection(String tablename) {
		super(tablename);
		patronList = new Vector<Patron>();
	}

    public void findPatronsOlderThan(String date) {
    	try {
    		patronList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT patronId FROM Patron WHERE dateOfBirth > " + date;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		patronList.addElement(new Patron(rs.getString("patronId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    public void findPatronsYoungerThan(String date) { 
    	try {
    		patronList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT patronId FROM Patron WHERE dateOfBirth > " + date;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		patronList.addElement(new Patron(rs.getString("patronId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    	System.out.println("gggg");
    }
    public void findPatronsAtZipCode(String zip) {
    	try {
    		patronList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT patronId FROM Patron WHERE zip = " + zip;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		patronList.addElement(new Patron(rs.getString("patronId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    public void findPatronsWithNameLike(String name) { 
    	try {
    		patronList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT patronId FROM Patron WHERE name LIKE '%" + name + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		patronList.addElement(new Patron(rs.getString("patronId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
	public ArrayList<MaleablePatron> toArrayList() {
		ArrayList<MaleablePatron> list = new ArrayList<>();
    	for (int i = 0; i < patronList.size(); i++) {
    		Patron p = (Patron)patronList.get(i);
    		System.out.println(p.toString() + " MMMM");
    		list.add(new MaleablePatron(p));
    	}
    	return list;
	}
    
    /**
     * JUST FOR DEBUGGING PURPOSES
     * @return
     */
    public String print() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < patronList.size(); i++) {
    		Patron p = (Patron)patronList.get(i);
    		sb.append(p.toString() + "\n");
    	}
    	return sb.toString();
    }

	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initializeSchema(String tableName) {
		// TODO Auto-generated method stub
	}
}