package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;

public class Patron extends EntityBase {

	private static final String myTableName = "Patron";
	protected Properties dependencies;
	private String updateStatusMessage = "";
	
	public Patron(String patronId) throws InvalidPrimaryKeyException {
		super(patronId);
		// TODO Auto-generated constructor stub
		
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		// You must get one account at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one id of this book. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple patrons matching id : " + patronId + " found.");
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
			throw new InvalidPrimaryKeyException("No patron matching id : " + patronId + " found.");
		}
	}
	
	public Patron(Properties props)
	{
		super(myTableName);

		setDependencies();
		persistentState = new Properties();
		Enumeration allKeys = props.propertyNames();
		while (allKeys.hasMoreElements() == true)
		{
			String nextKey = (String)allKeys.nextElement();
			String nextValue = props.getProperty(nextKey);
			if (nextValue != null)
			{
				persistentState.setProperty(nextKey, nextValue);
			}
		}
	}
	
	public void update() { 
		try {
			if (persistentState.getProperty("patronId") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("patronId", persistentState.getProperty("patronId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Patron data for patron id : " + persistentState.getProperty("patronId") + " updated successfully in database!";
			} else {
				Integer patronId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("patronId", "" + patronId.intValue());
				updateStatusMessage = "Patron id for new Patron : " +  persistentState.getProperty("patronId") + "installed successfully in database!";
			}			
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing patron data in database!";
		}
	}
	
	private void setDependencies() {
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	@Override
	public Object getState(String key) {
		return persistentState.getProperty(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initializeSchema(String tableName) {
		// TODO Auto-generated method stub
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
	
	public String toString() {
		return "patronId=" + persistentState.getProperty("patronId") + 
				" name=" + persistentState.getProperty("name") + 
				" address=" + persistentState.getProperty("address") + 
				" city=" + persistentState.getProperty("city") + 
				" stateCode=" + persistentState.getProperty("stateCode") + 
				" zip=" + persistentState.getProperty("zip") + 
				" email=" + persistentState.getProperty("email") + 
				" dateOfBirth=" + persistentState.getProperty("dateOfBirth") + 
				" status=" + persistentState.getProperty("status");
	}
}