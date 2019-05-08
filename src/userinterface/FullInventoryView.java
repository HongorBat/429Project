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
import model.InventoryItem;
import model.InventoryItemCollection;
import model.InventoryItemType;
import model.InventoryItemTypeCollection;
import model.SimplifiedII;
import model.SimplifiedIIT;
import model.SimplifiedVIIT;
import model.Vendor;
import model.VendorCollection;
import model.VendorInventoryItemType;
import model.VendorInventoryItemTypeCollection;

import java.util.Properties;
import java.util.Vector;

// project imports
//BIG OOF
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class FullInventoryView extends View
{

	// GUI components
	public static InventoryItemCollection II_COLLECTION  = new InventoryItemCollection("InventoryItemCollection");
	protected ComboBox<String> SearchResult = new ComboBox<String>();
	
	protected String vndrName;

	protected Button cancelButton, deleteButton, searchButton;
	private TableView view;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public FullInventoryView(IModel account)
	{
		super(account, "FullInventoryView");

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

        TableColumn c1 = new TableColumn("InventoryItemTypeId");
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn c2 = new TableColumn("Barcode");
        c2.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        
        TableColumn c3 = new TableColumn("InventoryItemTypeName");
        c3.setCellValueFactory(new PropertyValueFactory<>("iitn"));
        
        TableColumn c4 = new TableColumn("VendorId");
        c4.setCellValueFactory(new PropertyValueFactory<>("vid"));
        
        TableColumn c5 = new TableColumn("DateReceived");
        c5.setCellValueFactory(new PropertyValueFactory<>("dateReceived"));
        
        TableColumn c6 = new TableColumn("DateOfLastUse");
        c6.setCellValueFactory(new PropertyValueFactory<>("dateOfLastUse"));
        
        TableColumn c7 = new TableColumn("Notes");
        c7.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        TableColumn c8 = new TableColumn("Status");
        c8.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        tableView.getColumns().setAll(c1, c2, c3, c4, c5, c6, c7, c8);
        
        view = tableView;
        
        grid.add(tableView, 0, 0);
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
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

		doneCont.getChildren().add(cancelButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);
        processItemTypeSelected();
		return vbox;
	}
	

	protected void processItemTypeSelected()
	{
		getEntryTableModelValues();
	}
	
	protected void getEntryTableModelValues()
	{
		ObservableList<String> Result = FXCollections.observableArrayList();

		//VIIT_COLLECTION.getAllVendorInventoryItemTypessWithNameLike(_vendor);
		II_COLLECTION.getAvailableInventoryItems();

		Vector<InventoryItem> items = II_COLLECTION.getInventoryItemList();
		for (int i = 0; i < items.size(); i++) {
			InventoryItem ii = items.get(i);
			SimplifiedII sii = new SimplifiedII(ii.getField("InventoryItemId"), ii.getField("Barcode"), ii.getField("InventoryItemTypeName"), 
					ii.getField("VendorId"), ii.getField("DateReceived"), ii.getField("DateOfLastUse"), ii.getField("Notes"), ii.getField("Status"));
			/*SimplifiedVIIT sv = new SimplifiedVIIT(vnd.getField("Id"), 
					vnd.getField("InventoryItemTypeName"), vnd.getField("VendorPrice"), vnd.getField("DateLastUpdated"));*/
			view.getItems().add(sii);
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


