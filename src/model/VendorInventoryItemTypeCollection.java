package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import database.JDBCBroker;

public class VendorInventoryItemTypeCollection extends EntityBase {
	
	private Vector<VendorInventoryItemType> viitList;
    private String updateStatusMessage = "";

    public VendorInventoryItemTypeCollection(String tableName) {
    	super(tableName);
        viitList = new Vector<VendorInventoryItemType>();
    }
    
    public void getAllVendorInventoryItemTypes() {
    	try {
    		viitList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM VendorInventoryItemType";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		viitList.addElement(new VendorInventoryItemType(rs.getString("Id")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED... ");
    	}
    }
    
    public void getAllVendorInventoryItemTypessWithNameLike(String name) {
    	try {
    		viitList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();/*
    		String query = "SELECT VendorsId, InventoryItemTypeName, Vendor.Name FROM VendorInventoryItemType " +
					"JOIN Vendor ON VendorInventoryItemType.VendorsId = Vendor.Id " +
					"WHERE VendorInventoryItemType.Name LIKE '%" + name + "%'";*/
        	
    		String query = " SELECT * FROM VendorInventoryItemType WHERE InventoryItemTypeName LIKE '%" + name + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		viitList.addElement(new VendorInventoryItemType(rs.getString("Id")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED... ");
    	}
    }
    
    public void getAllVendorInventoryItemTypesWithVendorsIdLike(String id) {
    	try {
    		viitList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
    		String query = "SELECT * FROM VendorInventoryItemType WHERE VendorsId = " + id;
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		viitList.addElement(new VendorInventoryItemType(rs.getString("Id")));
        	}
        	
    	} catch (Exception ex) {
    		System.err.println("FAILED...");
    	}
    }

    public void deleteVendorInventoryItemTypeWithId(String id) {
		StringBuilder sb = new StringBuilder();
		try {
			viitList.removeAllElements();
			Connection con = JDBCBroker.getInstance().getConnection();
			sb.append("DELETE FROM VendorInventoryItemType WHERE Id ='" + id + "'");
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sb.toString());
		} catch (Exception ex) {
			System.err.println("Failed delete for " + sb.toString());
		}
	}
    
    
    /**
     * JUST FOR DEBUGGING PURPOSES
     * @return
     */
    public String print() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < viitList.size(); i++) {
    		VendorInventoryItemType b = (VendorInventoryItemType)viitList.get(i);
    		sb.append(b.toString() + "\n");
    	}
    	return sb.toString();
    }
    
    /**
     * @return the array of InventoryItemType
     */
    public Vector<VendorInventoryItemType> getVendorInventoryItemTypeList() {
    	return viitList;
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
