package userinterface;

import java.util.Vector;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.InventoryItem;
import model.InventoryItemCollection;
import model.InventoryItemType;
import model.InventoryItemTypeCollection;

public class UpdateInventoryView extends View{

	// GUI components
	//Restaurant owner provides the Inventory Manager 
	//with the Item Type Name, Units, Unit Measure, Validity Days, 
	//Reorder Point, and Notes of the Inventory Item Type
	// GUI components
	protected TextField InventoryName;
	
	public static InventoryItemTypeCollection INVENTORY_ITEM_TYPE_COLLECTION = new InventoryItemTypeCollection("InventoryItemType");
	public static String SELECTED_ITEM = "";
	protected ComboBox<String> SearchResult;

	protected TextField serviceCharge;
	
	protected String inventory;


	protected Button cancelButton, updateButton, deleteButton;

	// For showing error message
	protected MessageView statusLog;
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public UpdateInventoryView(IModel account)
	{
		super(account, "UpdateInventoryView");

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

		Text titleText = new Text(" Brockport Restaurant ");
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
        
        Text prompt = new Text("UPDATE ITEM INVENTORY");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text inventoryTypeName = new Text(" Inventory Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		inventoryTypeName.setFont(myFont);
		inventoryTypeName.setWrappingWidth(150);
		inventoryTypeName.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryTypeName, 0, 1);
		

		InventoryName = new TextField();
		grid.add(InventoryName, 1, 1);
		
		
		updateButton = new Button("Search");
		updateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	processAccountSelected();
            	  }
        	});
		
		grid.add(updateButton, 2, 1);
		

		Text inventoryUnits = new Text(" Search Result : ");
		inventoryUnits.setFont(myFont);
		inventoryUnits.setWrappingWidth(150);
		inventoryUnits.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryUnits, 0, 2);

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
       		    	myModel.stateChangeRequest("TellerView", null);   
            	  }
        	});
		
		
		updateButton = new Button("Update");
		updateButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		updateButton.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  				SELECTED_ITEM = SearchResult.getSelectionModel().getSelectedItem().toString();
  				myModel.stateChangeRequest("UpdateFieldView", null);
  		     }
		});
	
		
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(updateButton);
		doneCont.getChildren().add(deleteButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	protected void processAccountSelected()
	{
		inventory = InventoryName.getText();
		
		if((inventory == null ) || (inventory.length() == 0)) {
			displayErrorMessage("Please enter a name!");
			InventoryName.requestFocus();
			return;
		} 
		displayErrorMessage("");
		getEntryTableModelValues(inventory);
	}
	
	protected void getEntryTableModelValues(String _inventory)
	{
		ObservableList<String> Result = FXCollections.observableArrayList();
		INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeName(_inventory);
		Vector<InventoryItemType> items = INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeList();
		for (int i = 0; i < items.size(); i++) {
			InventoryItemType iit = items.get(i);
			Result.add(iit.getField("ItemTypeName"));
		}
		
		try
		{
			System.out.println(_inventory);
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
