package userinterface;

import java.util.HashMap;
import java.util.Vector;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Dater;
import model.InventoryItem;
import model.InventoryItemCollection;
import model.InventoryItemType;
import model.Vendor;
import model.VendorCollection;

public class TakeOutInventoryView extends View{

	// GUI components
	//Restaurant owner provides the Inventory Manager 
	//with the Item Type Name, Units, Unit Measure, Validity Days, 
	//Reorder Point, and Notes of the Inventory Item Type
	// GUI components
	protected TextField inventoryItemName;
	protected ComboBox<String> SearchResult;
	public static InventoryItemCollection INVENTORY_ITEM_COLLECTION = new InventoryItemCollection("InventoryItem");
	public static String SELECTED_ITEM;
	private HashMap<String, InventoryItem> map = new HashMap<String, InventoryItem>();
	protected TextField serviceCharge;
	
	protected String invItem;


	protected Button cancelButton, updateButton, deleteButton;

	// For showing error message
	protected MessageView statusLog;
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public TakeOutInventoryView(IModel account)
	{
		super(account, "TakeoutInventoryView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		//populateFields();

		myModel.subscribe("ServiceCharge", this);
		myModel.subscribe("UpdateStatusMessage", this);
	}


	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Take Out Inventory Item ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text prompt = new Text("Take Out Inventory Item");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text nameLabel = new Text(" Inventory Item : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		nameLabel.setFont(myFont);
		nameLabel.setWrappingWidth(150);
		nameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(nameLabel, 0, 1);
		

		inventoryItemName = new TextField();
		grid.add(inventoryItemName, 1, 1);
		
		
		updateButton = new Button("Search");
		updateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateButton.setOnAction(new EventHandler<ActionEvent>() {
       		     @Override
       		     public void handle(ActionEvent e) {
       		    	processAccountSelected();
       		     }	
		});
		
		grid.add(updateButton, 2, 1);
		

		Text result = new Text(" Search Result : ");
		result.setFont(myFont);
		result.setWrappingWidth(150);
		result.setTextAlignment(TextAlignment.RIGHT);
		grid.add(result, 0, 2);

		SearchResult = new ComboBox<String>();
		grid.add(SearchResult, 1, 2);


		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Back");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("TellerView", null);   
            	  }
        	});
		
		deleteButton = new Button("Delete");
		deleteButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
            	  }
        	});
		
		
		updateButton = new Button("Update");
		updateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateButton.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  				SELECTED_ITEM = SearchResult.getSelectionModel().getSelectedItem().toString();
  				InventoryItem itm = map.get(SELECTED_ITEM);
  				if (isExpired(itm) == 2) {
  					Alert a = new Alert(AlertType.INFORMATION);
  	  				a.setContentText(SELECTED_ITEM + " doesnt exist.");
  	  				a.show();
  				} else if (isExpired(itm) == 1) {
  					INVENTORY_ITEM_COLLECTION.updateInventoryItemWithName(SELECTED_ITEM, "Status", "Expired");
  					Alert a = new Alert(AlertType.INFORMATION);
  	  				a.setContentText(SELECTED_ITEM + " is expired.");
  	  				a.show();
  				} else {
  					INVENTORY_ITEM_COLLECTION.updateInventoryItemWithName(SELECTED_ITEM, "Status", "Used");
  	  				Alert a = new Alert(AlertType.INFORMATION);
  	  				a.setContentText(SELECTED_ITEM + " has been taken out.");
  	  				a.show();
  				}
  		     }
		});
	
		
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(updateButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	protected void processAccountSelected()
	{
		invItem = inventoryItemName.getText();
		
		if((invItem == null ) || (invItem.length() == 0)) {
			displayErrorMessage("Please enter an Inventory Item!");
			inventoryItemName.requestFocus();
			return;
		} 
		displayErrorMessage("");
		getEntryTableModelValues(invItem);
	}
	
	// this returns true if and only if the item is available and not expired, else it is
	// not there or it is expired
	// 0 is false 1 is true
	private int isExpired(InventoryItem itm1) {
		String str = itm1.getField("InventoryItemTypeName");
		UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeName(str);
		//System.out.println(str + " = " +  UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeList().size()); DEBUG
		// sets to used
		// sets to expired
		// cant find inv item type
		
		if (UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeList().size() < 1) { return 2; } // in case no such entry
		InventoryItemType iitn = UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeList().get(0);
		
		int validDays = Integer.valueOf(iitn.getField("ValidityDays"));
		Dater d1 = new Dater("/", itm1.getField("DateReceived"));
		Dater d2 = new Dater("/", itm1.getField("DateOfLastUse"));
		if (d1.daysBetween(d2) > validDays) {
			return 0;
		} else { 
			return 1;
		}
	}
	
	protected void getEntryTableModelValues(String invItem)
	{
		
		ObservableList<String> Result = FXCollections.observableArrayList();
		INVENTORY_ITEM_COLLECTION.getInventoryItemNamesLike(invItem);
		Vector<InventoryItem> items = INVENTORY_ITEM_COLLECTION.getInventoryItemList();
		map.clear();
		for (int i = 0; i < items.size(); i++) {
			InventoryItem itm = items.get(i);
			String name = itm.getField("InventoryItemTypeName");
			Result.add(itm.getField("InventoryItemTypeName"));
			map.put(name, itm);
		}
		
		try
		{
			//System.out.println(_vendor);
			//PatronCollection patronCollection = new PatronCollection();
			//patronCollection.findPatronsAtZipCode(zipcode);
			//patronCollection.printResults();
			
			//Vector<Patron> entryList = (Vector<Patron>)patronCollection.getState("Patrons");

			//Enumeration<Patron> entries = entryList.elements();
			
			

			//while (entries.hasMoreElements() == true)
			//{
				//Patron nextAccount = (Patron)entries.nextElement();
				//Vector<String> view = nextAccount.getEntryListView();

				// add this list entry to the list
				//PatronTableModel nextTableRowData = new PatronTableModel(view);
				//tableData.add(nextTableRowData);
				
			//}
			
			SearchResult.setItems(Result);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}

	
	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}


	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("ServiceCharge") == true)
		{
			String val = (String)value;
			serviceCharge.setText(val);
			displayMessage("Service Charge Imposed: $ " + val);
		}
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
	
}
