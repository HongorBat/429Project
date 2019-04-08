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
	
	public void updateState(String key, String val) {
		
	}
	public void showView() { createAndShowInsertBookView(); }
	
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
	
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub

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
		if (key == null || value == null) { return; }
		if (dependencies.containsKey("key")) {
			String str = (String)value;
			dependencies.setProperty(key, str);
			myRegistry.setDependencies(dependencies);
		}
		
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