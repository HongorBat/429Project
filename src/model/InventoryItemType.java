package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;

public class InventoryItemType extends EntityBase {
	
	private static final String myTableName = "InventoryItemType";
	protected Properties dependencies;
	private String updateStatusMessage = "";

	/**
	 * Constructor that can manually create Inventory Item Type object
	 * @param props 
	 */
	public InventoryItemType(Properties props) {
		super(myTableName);
		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true) {
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			if (nextValue != null) {
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}
	
	/**
	 * Contructor gets the inventory item type from the constructor
	 * @param itemTypeId
	 * @throws InvalidPrimaryKeyException
	 */
	public InventoryItemType(String itemTypeId) throws InvalidPrimaryKeyException {
		super(itemTypeId);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (ItemTypeId = " + itemTypeId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		// You must get one account at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one id of this book. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple InventoryItemTypes matching id : " + itemTypeId + " found.");
			} else {
				// copy all the retrieved data into persistent state
				Properties retrievedBookData = allDataRetrieved.elementAt(0);
				persistentState = new Properties();
				Enumeration allKeys = retrievedBookData.propertyNames();
				while (allKeys.hasMoreElements() == true) {
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedBookData.getProperty(nextKey);
					if (nextValue != null) {
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		} else {
			// if no book is found for this book id, throw an exception
			throw new InvalidPrimaryKeyException("No InventoryItemType matching id : " + itemTypeId + " found.");
		}
	}
	
	/**
	 * Sets up the dependencies of the db
	 */
	private void setDependencies() {
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which to get
	 * @return	Value associated with the field
	 */
	@Override
	public Object getState(String key) {
		this.persistentState.getProperty(key);
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
		// This will change the particular data within this object
	}

	@Override
	protected void initializeSchema(String tableName) {
		if (mySchema == null) {
			mySchema = getSchemaInfo(tableName);
		}	
	}
	
	/**
	 * Gets the value of the given field
	 * @param fieldName field to get the value from
	 * @return
	 */
	public String getField(String fieldName) {
		if (persistentState.getProperty(fieldName) != null) {
			return persistentState.getProperty(fieldName);
		}
		return "EMPTY FIELD";
	}
	
	public void update() {
		try {
			if (persistentState.getProperty("ItemTypeId") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("ItemTypeId", persistentState.getProperty("ItemTypeId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "InventoryItemType matching  : " + persistentState.getProperty("ItemTypeId") + " updated successfully in database!";
			} else {
				Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("ItemTypeId",  "" + id.intValue());
				updateStatusMessage = "InventoryItemType matching : " +  persistentState.getProperty("ItemTypeId") + "installed successfully in database!";
			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing InventoryItemType data in database!";
		}
	}
}