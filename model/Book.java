package model;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import exception.InvalidPrimaryKeyException;
import impresario.ModelRegistry;

public class Book extends EntityBase {
	
	private static final String myTableName = "Book";
	protected Properties dependencies;
	private String updateStatusMessage = "";
	
	public Book(String bookId) throws InvalidPrimaryKeyException {
		super(bookId);
		setDependencies();
		String query = "SELECT * FROM " + myTableName + " WHERE (bookId = " + bookId + ")";
		Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
		// You must get one account at least
		if (allDataRetrieved != null) {
			int size = allDataRetrieved.size();

			// There should be EXACTLY one id of this book. More than that is an error
			if (size != 1) {
				throw new InvalidPrimaryKeyException("Multiple books matching id : " + bookId + " found.");
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
			throw new InvalidPrimaryKeyException("No patron matching id : " + bookId + " found.");
		}
	}
	
	public Book(Properties props) {
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
	
	private void setDependencies() {
		dependencies = new Properties();
		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
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
		if (mySchema == null) {
			mySchema = getSchemaInfo(tableName);
		}	
	}

	public void update() { 
		try {
			if (persistentState.getProperty("bookId") != null) {
				Properties whereClause = new Properties();
				whereClause.setProperty("bookId", persistentState.getProperty("bookId"));
				System.out.println(mySchema.toString() + " <<");
				updatePersistentState(mySchema, persistentState, whereClause);
				updateStatusMessage = "Book data for book id : " + persistentState.getProperty("bookId") + " updated successfully in database!";
			} else {
				System.out.println(mySchema.toString() + " <<");
				Integer bookId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("bookId", "" + bookId.intValue());
				updateStatusMessage = "Book id for new book : " +  persistentState.getProperty("bookId") + "installed successfully in database!";
			}
		} catch (SQLException ex) {
			updateStatusMessage = "Error in installing book data in database!";
		}
	}
	
	public String toString() {
		return "bookId=" + persistentState.getProperty("bookId") + 
				" author=" + persistentState.getProperty("author") + 
				" title=" + persistentState.getProperty("title") + 
				" pubYear=" + persistentState.getProperty("pubYear") + 
				" status=" + persistentState.getProperty("status");
	}
}