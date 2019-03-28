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
	 * @param iitn
	 * @throws InvalidPrimaryKeyException
	 */
	public InventoryItemType(String iitn) throws InvalidPrimaryKeyException {
		super(iitn);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (bookId = " + iitn + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		// You must get one account at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one id of this book. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple books matching id : " + iitn + " found.");
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
			throw new InvalidPrimaryKeyException("No patron matching id : " + iitn + " found.");
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
	
	public void update() {
		try {
			if (persistentState.getProperty("bookId") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("bookId", persistentState.getProperty("bookId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Book data for book id : " + persistentState.getProperty("bookId") + " updated successfully in database!";
			} else {
				Integer bookId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("bookId", "" + bookId.intValue());
				updateStatusMessage = "Book id for new book : " +  persistentState.getProperty("bookId") + "installed successfully in database!";
			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing book data in database!";
		}
	}

}
