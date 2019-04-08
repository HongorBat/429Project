package model;

import java.util.ArrayList;
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

public class SearchBook implements IView, IModel {

	
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;
	private AccountHolder myAccountHolder;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage	myStage;
	private BookCollection bc;

	private String loginErrorMessage = "";
	private String transactionErrorMessage = "";
		
	public static final String SEARCH_BOOK_BACK = "SearchBookBack";
	public static final String SEARCH_BOOK_SEARCH = "SearchBookSearch";
	public static final String SEARCH_BOOK_VIEW = "SearchBookView";
	
	public SearchBook() {
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
		dependencies.setProperty("TitleLike", "TitleLikeError");
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
		Scene currentScene = (Scene)myViews.get("SearchBookView");
		if (currentScene == null) {
			// Create our initial view
			View newView = ViewFactory.createView("SearchBookView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchBookView", currentScene);
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
			myRegistry.setDependencies(dependencies);
			//System.out.println("Updating field: " + key + " with " + str);
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
		if (key.equals(SEARCH_BOOK_BACK)) {
			createAndShowTellerView();
		} else if (key.equals(SEARCH_BOOK_SEARCH)) {
			getBookCollection();
		} else if (dependencies.containsKey(key)) {
			updateState(key, value);
		}
	}
	private void getBookCollection() {
		if (dependencies.getProperty("TitleLike").equals("TitleLikeError")) {
			return;
		} else if (bc == null) {
			bc = new BookCollection("Book");
		}
		bc.findBooksWithTitleLike(dependencies.getProperty("TitleLike"));
	}
	
	/**
	 * Returns a list to of books suitable for the table view in the gui
	 * @return the arraylist of maleable books
	 */
	public ArrayList<MutableBook> getCollectionOfBooksForTable() {
		return bc.toArrayList();
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

