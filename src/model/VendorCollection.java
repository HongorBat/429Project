package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import database.JDBCBroker;

public class VendorCollection extends EntityBase {
	
	private Vector<Vendor> vendorList;
    private String updateStatusMessage = "";

    public VendorCollection(String tableName) {
    	super(tableName);
        vendorList = new Vector<Vendor>();
    }
    
    /**
     * Retrieves all the inventory item types from the db
     */
    public void getAllVendors() {
    	try {
    		vendorList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM Vendor;";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		vendorList.addElement(new Vendor(rs.getString("Id")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED... ");
    	}
    }
    
    public void getAllVendorsLike(String name) {
    	try {
    		vendorList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM Vendor WHERE Name LIKE '%" + name + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		vendorList.addElement(new Vendor(rs.getString("Id")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED... ");
    	}
    }
    
    
    /**
     * 
     * @param inventoryItemTypeName Name of the InventoryItem
     * @param fieldName name of the field that you want to update
     * @param newValue value to set that field to
     */
    public void updateVendorWithName(String vendorName, String fieldName, String newValue) {
    	StringBuilder sb = new StringBuilder();
    	try {
    		vendorList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
    		sb.append("UPDATE Vendor SET ");
    		sb.append(fieldName + " = '" + newValue + "' ");
    		sb.append("WHERE Name = '" + vendorName + "'");
        	Statement stmt = con.createStatement();
        	stmt.executeUpdate(sb.toString());
    	} catch (Exception ex) {
    		System.err.println("Faild query for " + sb.toString() );
    	}
    }
    
    /**
     * JUST FOR DEBUGGING PURPOSES
     * @return
     */
    public String print() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < vendorList.size(); i++) {
    		Vendor b = (Vendor)vendorList.get(i);
    		sb.append(b.toString() + "\n");
    	}
    	return sb.toString();
    }
    
    /**
     * @return the array of InventoryItemType
     */
    public Vector<Vendor> getVendorList() {
    	return vendorList;
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
