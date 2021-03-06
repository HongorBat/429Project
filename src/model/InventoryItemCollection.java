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
    
    /**
     * Retrieves all the inventory items from the db
     */
    public void getAllInventoryItems() {
    	try {
    		inventoryItemList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM InventoryItem;";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new InventoryItem(rs.getString("InventoryItemId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    /**
     * Gets particular inventory items with a name like the given name
     * @param name inventory items like this name
     */
    public void getInventoryItemNamesLike(String name) {
    	try {
    		inventoryItemList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM InventoryItem WHERE InventoryItemTypeName LIKE '%" + name + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemList.addElement(new InventoryItem(rs.getString("InventoryItemId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    /**
     * 
     * @param inventoryItemTypeName Name of the InventoryItem
     * @param fieldName name of the field that you want to update
     * @param newValue value to set that field to
     */
    public void updateInventoryItemWithName(String inventoryItemTypeName, String fieldName, String newValue) {
    	try {
    		inventoryItemList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
    		StringBuilder sb = new StringBuilder();
    		sb.append("UPDATE InventoryItem SET ");
    		sb.append(fieldName + " = " + newValue + " ");
    		sb.append("WHERE InventoryItemTypeName = '" + inventoryItemTypeName + "'");
        	Statement stmt = con.createStatement();
        	stmt.executeUpdate(sb.toString());
    	} catch (Exception ex) {
    		System.err.println("FAILED ...");
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
    public Vector<InventoryItem> getInventoryItemList() {
    	return inventoryItemList;
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
