package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import database.JDBCBroker;
import userinterface.UpdateInventoryView;

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
    
    public void getAllWithBarcode(String barcode) {
    	try {
    		inventoryItemList.removeAllElements();
    		Integer.parseInt(barcode);
    		Connection con = JDBCBroker.getInstance().getConnection();
    		StringBuilder sb = new StringBuilder();
    		sb.append("SELECT * FROM InventoryItem WHERE Barcode = '" + barcode + "'");
        	Statement stmt = con.createStatement();
        	ResultSet rs = stmt.executeQuery(sb.toString());
        	while (rs.next()) {
        		inventoryItemList.addElement(new InventoryItem(rs.getString("InventoryItemId")));
        	}
    	} catch (Exception ex) {
    		System.err.println("FAILED to get barcode...");
    	}
    }
    
    // TODO
    public void setAsUsed(String barcode, boolean isExpired) {
    	getAllWithBarcode(barcode);
    	InventoryItem it = inventoryItemList.get(0);
    	// no such barcode exists, it this returns here
    	if (it == null || inventoryItemList.size() == 0) { return; }
    	String nameOfGivenItem = it.getField("Name");
    	//UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeName(nameOfGivenItem);
    	int validityDays = UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.reduceInventoryItemTypeUnits(nameOfGivenItem);
    	
    	
    	
    	
    	
    	
    	if (isExpired) {
        	updateInventoryItemWithName(nameOfGivenItem, "Status", "Expired");
    	} else {
        	updateInventoryItemWithName(nameOfGivenItem, "Status", "Used");
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
    		sb.append(fieldName + " = '" + newValue + "' ");
    		sb.append("WHERE InventoryItemTypeName = '" + inventoryItemTypeName + "'");
        	Statement stmt = con.createStatement();
        	stmt.executeUpdate(sb.toString());
    	} catch (Exception ex) {
    		System.err.println("Failed to update Inventory Item with name " + inventoryItemTypeName);
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
