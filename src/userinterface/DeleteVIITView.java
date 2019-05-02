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
import javafx.scene.control.ButtonType;
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
import model.SimplifiedVIIT;
import model.Vendor;
import model.VendorCollection;
import model.VendorInventoryItemType;
import model.VendorInventoryItemTypeCollection;

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
	public static VendorInventoryItemTypeCollection VIIT_COLLECTION = new VendorInventoryItemTypeCollection("VendorInventoryItemType");
	public static VendorCollection VENDOR_COLLECTION = new VendorCollection("Vendor");
	protected ComboBox<String> SearchResult = new ComboBox<String>();
	
	protected String vndrName;

	protected Button cancelButton, deleteButton, searchButton;
	private TableView view;

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
        
        TableView tableView = new TableView();/*
        tableView.setMaxWidth(1000);
        tableView.setMinWidth(1000);*/

        TableColumn c1 = new TableColumn("Id");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn c2 = new TableColumn("VIIT");
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn c3 = new TableColumn("Price");
        c3.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        TableColumn c4 = new TableColumn("DateLastUpdated");
        c4.setCellValueFactory(new PropertyValueFactory<>("dlu"));
        
        tableView.getColumns().setAll(c1, c2, c3, c4);
        
        view = tableView;
        
        grid.add(tableView, 1, 0);
        
        Text vndrName = new Text(" VIIT Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		vndrName.setFont(myFont);
		vndrName.setWrappingWidth(150);
		vndrName.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vndrName, 0, 1);
		

		VendorName = new TextField();
		grid.add(VendorName, 1, 2);
		
		searchButton = new Button("Search");
		searchButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		     	view.getItems().clear();
       		    	processVendorSelected();
            	 }
        	});
		
		grid.add(searchButton, 2, 1);

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
		
		deleteButton = new Button(" Delete ");
		deleteButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  		    	
  		    	SimplifiedVIIT sv = (SimplifiedVIIT)view.getSelectionModel().getSelectedItem();
  		    	if (sv == null) { return; }
  		    	
  		    	if (VendorName.getText() == null || VendorName.getText().length() == 0) {
  		    		displayErrorMessage("Field(s) have been left blank!");
  		    		return;
  		    	}
  		    	
  		    	Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + sv.getName() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
   		    	alert.showAndWait();
   		    	
   		    	if (alert.getResult() == ButtonType.NO) {
   		    		System.out.println("DeleteVIITView.deleteButton.NO");
   		    		return;
   		    	} else {
   		    		VIIT_COLLECTION.deleteVendorInventoryItemTypeWithId(sv.getId());
   	  		    	Alert a = new Alert(AlertType.INFORMATION);
   	  		    	a.setContentText("Vendor Inventory Item Type was deleted.");
   	  		    	a.show();
   		    	}
       	  }
   	});
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(deleteButton);
	
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
	

	protected void processVendorSelected()
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

		VIIT_COLLECTION.getAllVendorInventoryItemTypessWithNameLike(_vendor);

		Vector<VendorInventoryItemType> items = VIIT_COLLECTION.getVendorInventoryItemTypeList();
		for (int i = 0; i < items.size(); i++) {
			VendorInventoryItemType vnd = items.get(i);
			Result.add(vnd.getField("Name"));
			SimplifiedVIIT sv = new SimplifiedVIIT(vnd.getField("Id"), 
					vnd.getField("InventoryItemTypeName"), vnd.getField("VendorPrice"), vnd.getField("DateLastUpdated"));
			view.getItems().add(sv);
		}
		
		System.out.println( SearchResult.toString());
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


