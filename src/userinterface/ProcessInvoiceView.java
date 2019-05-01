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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import model.InventoryItemCollection;
import model.Vendor;
import model.VendorCollection;
import model.VendorInventoryItemType;
import model.VendorInventoryItemTypeCollection;


public class ProcessInvoiceView extends View{
	
	// GUI components
	//Restaurant owner provides the Inventory Manager 
	//with the Item Type Name, Units, Unit Measure, Validity Days, 
	//Reorder Point, and Notes of the Inventory Item Type
	// GUI components
	protected TextField vendorName, vendorNumber;
	
	public static InventoryItemCollection INVENTORY_ITEM_COLLECTION = new InventoryItemCollection("InventoryItem");
	public static VendorInventoryItemTypeCollection VIIT_COLLECTION = new VendorInventoryItemTypeCollection("VendorInventoryItemType");
	public static VendorCollection VENDOR_COLLECTION = new VendorCollection("VendorCollection");
	public static String SELECTED_ITEM = "";
	protected ComboBox<String> SearchResult;

	protected TextField serviceCharge;
	
	protected String vndrName, vndrNumber;
	
	Alert confirmation;


	protected Button cancelButton, processButton;

	// For showing error message
	protected MessageView statusLog;
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ProcessInvoiceView(IModel account)
	{
		super(account, "ProcessInvoiceView");

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
        
        Text prompt = new Text("PROCESS INVOICE");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text vName = new Text(" Vendor Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		vName.setFont(myFont);
		vName.setWrappingWidth(150);
		vName.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vName, 0, 1);
		

		vendorName = new TextField();
		grid.add(vendorName, 1, 1);
		
		Text vNumber = new Text(" Vendor Phone Number : ");
		vNumber.setFont(myFont);
		vNumber.setWrappingWidth(150);
		vNumber.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vNumber, 0, 2);
		

		vendorNumber = new TextField();
		grid.add(vendorNumber, 1, 2);
		
		
		processButton = new Button("Search");
		processButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		processButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	processAccountSelected();
            	  }
        	});
		
		grid.add(processButton, 1, 3);
		

		Text inventoryUnits = new Text(" Search Result : ");
		inventoryUnits.setFont(myFont);
		inventoryUnits.setWrappingWidth(150);
		inventoryUnits.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryUnits, 0, 4);

		SearchResult = new ComboBox<String>();
		SearchResult.getSelectionModel().selectFirst();
		grid.add(SearchResult, 1, 4);


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
		

		
		
		processButton = new Button("Update");
		processButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		processButton.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  		    	if(SearchResult.getSelectionModel().getSelectedItem() == null)
  		    	{
  		    		Alert a = new Alert(AlertType.NONE);
  		    		a.setAlertType(AlertType.ERROR);
  		    		a.setContentText("Please select an item from the dropdown box before updating.");
  		    		a.show(); 
  		    	}
  		    	else {
  				SELECTED_ITEM = SearchResult.getSelectionModel().getSelectedItem().toString();
  				myModel.stateChangeRequest("ModifyInvoiceView", null);
  		    	}
  		     }
		});
	
		
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(processButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	protected void processAccountSelected()
	{
		vndrName = vendorName.getText();
		vndrNumber = vendorNumber.getText();
		
		if((vndrName == null ) || (vndrName.length() == 0)) {
			displayErrorMessage("Please enter a name!");
			vendorName.requestFocus();
			return;
		} 
		
		if((vndrNumber == null ) || (vndrNumber.length() == 0)) {
			displayErrorMessage("Please enter a phone number!");
			vendorName.requestFocus();
			return;
		}
		displayErrorMessage("");
		getEntryTableModelValues(vndrName);
	}
	
	protected void getEntryTableModelValues(String _vnderName)
	{
		VENDOR_COLLECTION.getAllVendorsWithNameLike(_vnderName);
		Vendor v = VENDOR_COLLECTION.getVendorList().get(0);
		
		VIIT_COLLECTION.getAllVendorInventoryItemTypesWithVendorsIdLike(v.getField("Id"));
		
		ObservableList<String> Result = FXCollections.observableArrayList();
		Vector<VendorInventoryItemType> items = VIIT_COLLECTION.getVendorInventoryItemTypeList();
		
		for (int i = 0; i < items.size(); i++) {
			VendorInventoryItemType viit = items.get(i);
			Result.add(viit.getField("InventoryItemTypeName"));
		}
		
		SearchResult.setItems(Result);
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
