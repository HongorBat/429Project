package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import database.JDBCBroker;

public class InventoryItemCollection extends EntityBase {
	
	private Vector<InventoryItem> inventoryItemList;
    private String updateStatusMessage = "";

    public InventoryItemCollection(String tableName) {
    	super(tableName);
        inventoryItemList = new Vector<InventoryItem>();
    }

    public void getAllItemCollections() {
    	try {
    		inventoryItemList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM Book";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new InventoryItem(rs.getString("Barcode")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    /*
    public void findBooksOlderThanDay(String year) { 
    	try {
    		inventoryItemList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE pubYear < " + year;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    public void findBooksNewerThanDate(String year) { 
    	try {
    		inventoryItemList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE pubYear > " + year;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    public void findBooksWithTitleLike(String title) {
    	try {
    		inventoryItemList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE title LIKE '%" + title + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    public void findBooksWithAuthorLike(String author) {
        try {
    		inventoryItemList.removeAllElements();
        	Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT bookId FROM Book WHERE author LIKE '%" + author + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new Book(rs.getString("bookId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    /**
     * JUST FOR DEBUGGING PURPOSES
     * @return
     */
    public String print() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < inventoryItemList.size(); i++) {
    		InventoryItem b = (InventoryItem)inventoryItemList.get(i);
    		sb.append(b.toString() + "\n");
    	}
    	return sb.toString();
    }
    
    /**
     * @return the array of InventoryItemType
     */
    public ArrayList<InventoryItem> toArrayList() {
    	ArrayList<InventoryItem> list = new ArrayList<>();
    	for (int i = 0; i < inventoryItemList.size(); i++) {
    		InventoryItem b = (InventoryItem)inventoryItemList.get(i);
    		list.add(b);
    	}
    	return list;
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
