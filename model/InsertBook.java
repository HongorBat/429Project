package model;

import java.util.Hashtable;
import java.util.Properties;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class InsertBook implements IView, IModel {

	
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;
	private AccountHolder myAccountHolder;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	myStage;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";
	
	public static final String INSERT_BOOK_BACK = "InsertBookBack";
	public static final String INSERT_BOOK_INSERT = "InsertBookInsert";
	public static final String INSERT_BOOK_VIEW = "InsertBookView";
	
	public InsertBook() {
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();
		myRegistry = new ModelRegistry("InsertBook");
		setDependencies();
	}
	
	/**
	 * Sets the default fields and values
	 */
	private void setDependencies() {
		dependencies = new Properties();
		dependencies.setProperty("BookId", "BookIdError");
		dependencies.setProperty("Author", "AuthorError");
		dependencies.setProperty("Title", "TitleError");
		dependencies.setProperty("PubYear", "PubYearError");
		dependencies.setProperty("Status", "StatusError");
		myRegistry.setDependencies(dependencies);
	}
	
	public void showView() { createAndShowInsertBookView(); }
	public void closeView() { createAndShowTellerView(); }
	
	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key) {
		if (dependencies.containsKey(key)) {
			return dependencies.get(key);
		}
		return null;
	}
	
	/**
	 * Creates the InsertBookView if not already created. Then shows 
	 * the book view.
	 */
	private void createAndShowInsertBookView() {
		Scene currentScene = (Scene)myViews.get("InsertBookView");
		if (currentScene == null) {
			// Create our initial view
			View newView = ViewFactory.createView("InsertBookView", this);
			currentScene = new Scene(newView);
			myViews.put("InsertBookView", currentScene);
		}
		swapToView(currentScene);
	}
	
	private void createAndShowTellerView() {
		Scene currentScene = (Scene)myViews.get("TellerView");
		if (currentScene == null) {
			// Create our initial view
			View newView = ViewFactory.createView("TellerView", this);
			currentScene = new Scene(newView);
			myViews.put("TellerView", currentScene);
		}
		swapToView(currentScene);
	}
	
	
	@Override
	/**
	 * Sets the key value pairs within the models, respective to the field
	 */
	public void updateState(String key, Object value) {
		if (dependencies.containsKey(key)) {
			String str = (String) value;
			dependencies.setProperty(key, str);
		}
	}
	
	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}
	
	/**
	 * Depending on the key, handles various actions
	 */
	@Override
	public void stateChangeRequest(String key, Object value) {
		// get the values and update the model
		if (key.equals(INSERT_BOOK_BACK)) {
			createAndShowTellerView();
		} else if (key.equals(INSERT_BOOK_INSERT)) {
			insertBook();
		} else if (dependencies.containsKey(key)) {
			updateState(key, value);
		}
	}
	private void insertBook() {
		Properties p = new Properties();
		
		// For some reasons, this will prevent the book from beings added, removed temporarily
		//p.setProperty("bookId", dependencies.getProperty("BookId"));
		
		p.setProperty("author",  dependencies.getProperty("Author"));
		p.setProperty("title", dependencies.getProperty("Title"));
		p.setProperty("pubYear", dependencies.getProperty("PubYear"));
		p.setProperty("status", dependencies.getProperty("Status"));
		Book b = new Book(p);
		b.update();
	}
	
	/**
	 * Sets the given scene to the stage
	 * @param newScene
	 */
	public void swapToView(Scene newScene) {
		if (newScene == null) {
			System.out.println("Insert.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
		//Place in center
		WindowPosition.placeCenter(myStage);
	}
	public String toString() {
		return dependencies.toString();
	}
}
