package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import database.JDBCBroker;

public class InventoryItemTypeCollection extends EntityBase {
	
	private Vector<InventoryItemType> inventoryItemTypeList;
    private String updateStatusMessage = "";
    private String mm = " kmfqlkmkmewfq";

    public InventoryItemTypeCollection(String tableName) {
    	super(tableName);
        inventoryItemTypeList = new Vector<InventoryItemType>();
    }
    
    /**
     * Retrieves all the inventory item types from the db
     */
    public void deleteInventoryItemTypes(String itemTypeId) {
    	try {
    		inventoryItemTypeList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "DELETE FROM InventoryItemType WHERE ItemTypeId = '" + itemTypeId + "'";
        	Statement stmt = con.createStatement();
        	stmt.executeUpdate(query);
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    /**
     * Retrieves all the inventory item types from the db
     */
    public void getAllInventoryItemTypes() {
    	try {
    		inventoryItemTypeList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM InventoryItemType;";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemTypeList.addElement(new InventoryItemType(rs.getString("ItemTypeId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    /**
     * Gets particular inventory items with a name like the given name
     * @param name inventory items like this name
     */
    public void getInventoryItemTypeName(String name) {
    	try {
    		inventoryItemTypeList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM InventoryItemType WHERE ItemTypeName LIKE '%" + name + "%'";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		inventoryItemTypeList.addElement(new InventoryItemType(rs.getString("ItemTypeId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED");
    	}
    }
    
    ///
    public void getActiveInventoryItemsWhereUnitsLessThanReorderPoint() {
    	try {
    		inventoryItemTypeList.removeAllElements();
    		Vector<InventoryItemType> temp = new Vector<InventoryItemType>();
    		Connection con = JDBCBroker.getInstance().getConnection();
        	String query = "SELECT * FROM InventoryItemType WHERE STATUS = 'Active';";
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while (rs.next()) {
        		temp.addElement(new InventoryItemType(rs.getString("ItemTypeId")));
        	}
        	
        	for (int i = 0; i < temp.size(); i++) {
        		InventoryItemType t = temp.get(i);
        		int units = Integer.valueOf(t.getField("Units"));
        		int reorderPoint = Integer.valueOf(t.getField("ReorderPoint"));
        		if (units <= reorderPoint) { inventoryItemTypeList.add(t); }
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED ... retreiving active iiit for reordering.");
    	}
    }
    
    /**
     * 
     * @param inventoryItemTypeName Name of the InventoryItem
     * @param fieldName name of the field that you want to update
     * @param newValue value to set that field to
     */
    public void updateInventoryItemTypeWithName(String inventoryItemTypeName, String fieldName, String newValue) {
    	StringBuilder sb = new StringBuilder();
    	try {
    		inventoryItemTypeList.removeAllElements();
    		Connection con = JDBCBroker.getInstance().getConnection();
    		sb.append("UPDATE InventoryItemType SET ");
    		sb.append(fieldName + " = '" + newValue + "' ");
    		sb.append("WHERE ItemTypeName = '" + inventoryItemTypeName + "'");
        	Statement stmt = con.createStatement();
        	stmt.executeUpdate(sb.toString());
    	} catch (Exception ex) {
    		System.err.println("Failed query for " + sb.toString() );
    	}
    }
    
    /**
     * This returns the validity days quantity
     * @param name the name of the inventoryitemtype to reduce by 1
     * 
     * Takes the inventory item type name, and reduces the units by 1, returning the 
     * validity of days
     */
    public int reduceInventoryItemTypeUnits(String name) {
    	getInventoryItemTypeName(name);
    	InventoryItemType it = inventoryItemTypeList.get(0);
    	int currentValOfUnitsField = Integer.valueOf(it.getField("Units"));
    	if (currentValOfUnitsField > 0) {
    		currentValOfUnitsField--;
    		updateInventoryItemTypeWithName(name, "Units", currentValOfUnitsField + "");
    	} else {
    		System.err.println("Failed to reduce the units of an InventoryItemType... ");
    	}
    	return Integer.valueOf(it.getField("ValidityDays"));
    }
    
    /**
     * JUST FOR DEBUGGING PURPOSES
     * @return
     */
    public String print() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < inventoryItemTypeList.size(); i++) {
    		InventoryItemType b = (InventoryItemType)inventoryItemTypeList.get(i);
    		sb.append(b.toString() + "\n");
    	}
    	return sb.toString();
    }
    
    /**
     * @return the array of InventoryItemType
     */
    public Vector<InventoryItemType> getInventoryItemTypeList() {
    	return inventoryItemTypeList;
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
