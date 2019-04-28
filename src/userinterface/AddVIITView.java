// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import javafx.stage.Stage;
import model.InventoryItemType;
import model.InventoryItemTypeCollection;
import model.Vendor;
import model.VendorCollection;
import model.VendorInventoryItemType;

import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class AddVIITView extends View
{

	// GUI components
	protected TextField InventoryItemTypeName;
	protected TextField VendorPrice;
	protected TextField DateLastUpdated;
	protected TextField VendorName;
	public static VendorCollection VENDOR_COLLECTION = new VendorCollection("Vendor");
	public static InventoryItemTypeCollection IIVT_COLLECTION = new InventoryItemTypeCollection("InventoryItemType");
	protected ComboBox<String> SearchResult;
	protected ComboBox<String> SearchResultIIT;
	
	protected String vndrName, iitName;

	protected Button cancelButton, submitButton, searchButton, searchButtonIIT;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddVIITView(IModel account)
	{
		super(account, "AddVIITView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

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
        
        Text vndrName = new Text(" Vendor Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		vndrName.setFont(myFont);
		vndrName.setWrappingWidth(150);
		vndrName.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vndrName, 0, 1);
		

		VendorName = new TextField();
		grid.add(VendorName, 1, 1);
		
		
		searchButton = new Button("Search");
		searchButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	processAccountSelected();
            	  }
        	});
		
		grid.add(searchButton, 2, 1);
		

		Text inventoryUnits = new Text(" Search Result : ");
		inventoryUnits.setFont(myFont);
		inventoryUnits.setWrappingWidth(150);
		inventoryUnits.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryUnits, 0, 2);

		SearchResult = new ComboBox<String>();
		SearchResult.getSelectionModel().selectFirst();
		grid.add(SearchResult, 1, 2);

        Text iITNLabel = new Text(" Inventory Item Type Name : ");
		iITNLabel.setFont(myFont);
		iITNLabel.setWrappingWidth(150);
		iITNLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(iITNLabel, 0, 3);

		InventoryItemTypeName = new TextField();
		grid.add(InventoryItemTypeName, 1, 3);
		
		searchButtonIIT = new Button("Search");
		searchButtonIIT.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		searchButtonIIT.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	processSelected();
            	  }
        	});
		
		grid.add(searchButtonIIT, 2, 3);
		
		Text iit = new Text(" Search Result : ");
		iit.setFont(myFont);
		iit.setWrappingWidth(150);
		iit.setTextAlignment(TextAlignment.RIGHT);
		grid.add(iit, 0, 4);
		
		SearchResultIIT = new ComboBox<String>();
		SearchResultIIT.getSelectionModel().selectFirst();
		grid.add(SearchResultIIT, 1, 4);

		Text priceLabel = new Text(" Vendor Price : ");
		priceLabel.setFont(myFont);
		priceLabel.setWrappingWidth(150);
		priceLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(priceLabel, 0, 5);

		VendorPrice = new TextField();
		grid.add(VendorPrice, 1, 5);

		Text dateUpdatedLabel = new Text(" Date Last Updated : ");
		dateUpdatedLabel.setFont(myFont);
		dateUpdatedLabel.setWrappingWidth(150);
		dateUpdatedLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dateUpdatedLabel, 0, 6);
		
		DateLastUpdated = new TextField();
		grid.add(DateLastUpdated, 1, 6);

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
		
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  		    	
  		    	if (InventoryItemTypeName.getText().length() == 0 || VendorPrice.getText().length() == 0 || DateLastUpdated.getText().length() == 0) {
  		    		displayErrorMessage("Field(s) have been left blank!");
  		    		return;
  		    	}
  		    	if (!(VendorPrice.getText().matches("\\$\\d+(.)?(\\d)*"))) {
  		    		displayErrorMessage("Please enter $ sign and amount only in numbers");
  		    		return;
  		    	}
  		    	if(!(DateLastUpdated.getText().matches("\\d{4}(\\/)\\d{2}(\\/)\\d{2}"))) {
  		    		displayErrorMessage("Please enter date using YYYY/MM/DD form");
  		    		return;
  		    	}

  		    	addVIIT(fetchVendorId(), fetchIITId());
  		    	Alert a = new Alert(AlertType.INFORMATION);
  		    	a.setContentText("Vendor Inventory Item Type was added.");
  		    	a.show();
       	  }
   	});
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(submitButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	private String fetchVendorId() {
		String vnderName = SearchResult.getSelectionModel().getSelectedItem();
	    VENDOR_COLLECTION.getAllVendorsWithNameLike(vnderName);
		Vendor v = VENDOR_COLLECTION.getVendorList().get(0);
		return v.getField("Id");
	}
	
	private String fetchIITId() {
		String iitN = SearchResultIIT.getSelectionModel().getSelectedItem();
	    IIVT_COLLECTION.getInventoryItemTypeName(iitN);
		InventoryItemType v = IIVT_COLLECTION.getInventoryItemTypeList().get(0);
		return v.getField("ItemTypeName");
	}
	
	
	private void addVIIT(String id, String iitName) {
		// add the properties
		Properties p3 = new Properties();
		//p2.setProperty("Id", ""); // this field is auto incremented, dont touch
		p3.setProperty("InventoryItemTypeName", iitName);
		p3.setProperty("VendorPrice", VendorPrice.getText());
		p3.setProperty("DateLastUpdated", DateLastUpdated.getText());   
		p3.setProperty("VendorsId", id);
		
		// create item using properties, then add to db
		VendorInventoryItemType ve = new VendorInventoryItemType(p3);
		ve.update();
	}

	protected void processAccountSelected()
	{
		vndrName = VendorName.getText();
		
		if((vndrName == null ) || (vndrName.length() == 0)) {
			displayErrorMessage("Please enter a name!");
			VendorName.requestFocus();
			return;
		} 
		displayErrorMessage("");
		getEntryTableModelValues(vndrName);
	}
	
	protected void processSelected()
	{
		iitName = InventoryItemTypeName.getText();
		
		if((iitName == null ) || (iitName.length() == 0)) {
			displayErrorMessage("Please enter a name!");
			InventoryItemTypeName.requestFocus();
			return;
		} 
		displayErrorMessage("");
		getEntryTableModelValuesIIVT(iitName);
	}
	
	protected void getEntryTableModelValuesIIVT(String _iivt)
	{
		ObservableList<String> Result = FXCollections.observableArrayList();
		IIVT_COLLECTION.getInventoryItemTypeName(_iivt);
		Vector<InventoryItemType> items = IIVT_COLLECTION.getInventoryItemTypeList();
		for (int i = 0; i < items.size(); i++) {
			InventoryItemType ivt = items.get(i);
			Result.add(ivt.getField("ItemTypeName"));
		}
		
		try
		{
			
			SearchResultIIT.setItems(Result);
		}
		catch (Exception e) {//SQLException e) {
			// Need to handle this exception
		}
	}
	
	
	protected void getEntryTableModelValues(String _vendor)
	{
		ObservableList<String> Result = FXCollections.observableArrayList();
		VENDOR_COLLECTION.getAllVendorsWithNameLike(_vendor);
		Vector<Vendor> items = VENDOR_COLLECTION.getVendorList();
		for (int i = 0; i < items.size(); i++) {
			Vendor vnd = items.get(i);
			Result.add(vnd.getField("Name"));
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
		//	serviceCharge.setText(val);
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

//---------------------------------------------------------------
//	Revision History:
//

