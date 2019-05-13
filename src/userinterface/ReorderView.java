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
import model.InventoryItemCollection;
import model.InventoryItemType;
import model.InventoryItemTypeCollection;
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
public class ReorderView extends View
{

	// GUI components
	public static InventoryItemTypeCollection IIT_COLLECTION  = new InventoryItemTypeCollection("InventoryItemTypeCollection");
	protected ComboBox<String> SearchResult = new ComboBox<String>();
	
	protected String vndrName;

	protected Button cancelButton, deleteButton, searchButton;
	private TableView view;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ReorderView(IModel account)
	{
		super(account, "ReorderView");

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

        TableColumn c1 = new TableColumn("ItemTypeId");
        c1.setCellValueFactory(new PropertyValueFactory<>("itid"));
        
        TableColumn c2 = new TableColumn("Units");
        c2.setCellValueFactory(new PropertyValueFactory<>("units"));
        
        TableColumn c3 = new TableColumn("UnitMeasure");
        c3.setCellValueFactory(new PropertyValueFactory<>("unitMeasure"));
        
        TableColumn c4 = new TableColumn("ValidityDays");
        c4.setCellValueFactory(new PropertyValueFactory<>("validityDays"));
        
        TableColumn c5 = new TableColumn("ReorderPoint");
        c5.setCellValueFactory(new PropertyValueFactory<>("reorderPoint"));
        
        TableColumn c6 = new TableColumn("Notes");
        c6.setCellValueFactory(new PropertyValueFactory<>("notes"));
        
        TableColumn c7 = new TableColumn("Status");
        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        TableColumn c8 = new TableColumn("ItemTypeName");
        c8.setCellValueFactory(new PropertyValueFactory<>("itn"));
        
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

		IIT_COLLECTION.getActiveInventoryItemsWhereUnitsLessThanReorderPoint();

		Vector<InventoryItemType> items = IIT_COLLECTION.getInventoryItemTypeList();
		for (int i = 0; i < items.size(); i++) {
			InventoryItemType iit = items.get(i);
			SimplifiedIIT siit = new SimplifiedIIT(iit.getField("ItemTypeId"), iit.getField("Units"), iit.getField("UnitMeasure"), 
					iit.getField("ValidityDays"), iit.getField("ReorderPoint"), iit.getField("Notes"), iit.getField("Status"), iit.getField("ItemTypeName"));
			/*SimplifiedVIIT sv = new SimplifiedVIIT(vnd.getField("Id"), 
					vnd.getField("InventoryItemTypeName"), vnd.getField("VendorPrice"), vnd.getField("DateLastUpdated"));*/
			view.getItems().add(siit);
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


