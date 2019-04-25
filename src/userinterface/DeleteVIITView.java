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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import model.Vendor;
import model.VendorCollection;
import model.VendorInventoryItemType;

import java.util.Properties;
import java.util.Vector;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class DeleteVIITView extends View
{

	// GUI components
	protected TextField InventoryItemTypeName;
	protected TextField VendorPrice;
	protected TextField DateLastUpdated;
	protected TextField VendorName;
	public static VendorCollection VENDOR_COLLECTION = new VendorCollection("Vendor");
	protected ComboBox<String> SearchResult;
	
	protected String vndrName;

	protected Button cancelButton, submitButton, searchButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public DeleteVIITView(IModel account)
	{
		super(account, "DeleteVIITView");

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
        
        TableView tableView = new TableView();

        TableColumn<String, VendorInventoryItemType> column1 = new TableColumn<>("First Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));


        TableColumn<String, VendorInventoryItemType> column2 = new TableColumn<>("Last Name");
        column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));


        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        
        grid.add(tableView, 1, 1);
        
        Text vndrName = new Text(" Vendor Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		vndrName.setFont(myFont);
		vndrName.setWrappingWidth(150);
		vndrName.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vndrName, 0, 1);
		

		VendorName = new TextField();
		grid.add(VendorName, 7, 1);
		
		
		searchButton = new Button("Search");
		searchButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	processAccountSelected();
            	  }
        	});
		
		grid.add(searchButton, 7, 2);

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

  		    	addVIIT(fetchVendorId());
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
	
	
	private void addVIIT(String id) {
		// add the properties
		Properties p3 = new Properties();
		//p2.setProperty("Id", ""); // this field is auto incremented, dont touch
		p3.setProperty("InventoryItemTypeName", InventoryItemTypeName.getText());
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


