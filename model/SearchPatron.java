package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import event.Event;
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.TellerView;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class SearchPatron implements IView, IModel {
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

	private AccountHolder myAccountHolder;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	myStage;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";
	private PatronCollection pc;
	
	public final static String SEARCH_FOR_PATRON = "SearchForPatron";
	public final static String INSERT_PATRON_VIEW= "InsertPatronView";
	private Vector<Patron> patronList;
		
	public SearchPatron() {
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();
		myRegistry = new ModelRegistry("SearchPatron");
		setDependencies();
	}
	
	/**
	 * Sets the default fields and values
	 */
	private void setDependencies() {
		dependencies = new Properties();
		dependencies.setProperty("PatronId", "PatronIdError");
		dependencies.setProperty("Name", "NameError");
		dependencies.setProperty("Address", "AddressError");
		dependencies.setProperty("City", "CityError");
		dependencies.setProperty("StateCode", "StateCodeError");
		dependencies.setProperty("Zip", "ZipError");
		dependencies.setProperty("Email", "EmailError");
		dependencies.setProperty("DateOfBirth", "DateOfBirthError");
		dependencies.setProperty("Status", "StatusError");
		myRegistry.setDependencies(dependencies);
	}
	
	public void showView() { createAndShowSearchPatronView(); }
	public void cancelView() {createAndShowTellerView();}
	
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
	
	private void createAndShowSearchPatronView() {
		Scene currentScene = (Scene)myViews.get("SearchPatronView");
		if (currentScene == null) {
			// Create our initial view
			View newView = ViewFactory.createView("SearchPatronView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchPatronView", currentScene);
		}
		swapToView(currentScene);
	}
	
	private void createAndShowTellerView()
	{
		Scene currentScene = (Scene)myViews.get("TellerView");
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("TellerView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("TellerView", currentScene);
		}
		swapToView(currentScene);
	}
	
	@Override
	public void updateState(String key, Object value) {
		if (dependencies.containsKey(key)) {
			String str = (String) value;
			dependencies.setProperty(key, str);
			myRegistry.setDependencies(dependencies);
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
	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
		// get the values and update the model
		if (key.equals(SEARCH_FOR_PATRON)) {
			getPatronCollection();
			System.out.println("Patrons Values: " + toString());}
		else if (dependencies.containsKey(key)) {
			updateState(key,value);
		}
		
	}
	
	private void getPatronCollection() {
		if (dependencies.getProperty("Zip").equals("ZipErro")) { //TODO
			
			return;
		} else if (pc == null) {
			pc = new PatronCollection("Patron");
			System.out.println("SETTING");
		}
		pc.findPatronsAtZipCode(dependencies.getProperty("Zip"));
}
	
	public ArrayList<MaleablePatron> getCollectionOfPatronsForTable() {
		return pc.toArrayList(); // this is empty
}
	


	public void swapToView(Scene newScene)
	{
		if (newScene == null)
		{
			System.out.println("Teller.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
		//Place in center
		WindowPosition.placeCenter(myStage);
	}
}