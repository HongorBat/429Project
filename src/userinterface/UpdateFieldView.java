package userinterface;

import java.util.Properties;

import impresario.IModel;
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
import model.InventoryItemType;
import model.InventoryItemTypeCollection;
import model.Vendor;

public class UpdateFieldView extends View{
	
	// GUI components
	//Restaurant owner provides the Inventory Manager 
	//with the Item Type Name, Units, Unit Measure, Validity Days, 
	//Reorder Point, and Notes of the Inventory Item Type
	// GUI components
	protected TextField TypeName;
	protected TextField Units;
	protected TextField UnitMeasure;
	protected TextField ValidityDays;
	protected TextField ReorderPoint;
	protected TextField Notes;
	protected TextField ItemType;
	protected ComboBox Status;
	
	protected TextField serviceCharge;
	private InventoryItemTypeCollection iitc = new InventoryItemTypeCollection("InventoryItemType");


	protected Button cancelButton, submitButton;

	// For showing error message
	protected MessageView statusLog;
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public UpdateFieldView(IModel account)
	{
		super(account, "UpdateFieldView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

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
        
        Text prompt = new Text("ITEM INVENTORY INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text inventoryUnits = new Text(" Units : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		inventoryUnits.setFont(myFont);
		inventoryUnits.setWrappingWidth(150);
		inventoryUnits.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryUnits, 0, 2);

		Units = new TextField();
		grid.add(Units, 1, 2);

		Text inventoryUnitMeasure = new Text(" Unit measure : ");
		inventoryUnitMeasure.setFont(myFont);
		inventoryUnitMeasure.setWrappingWidth(150);
		inventoryUnitMeasure.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryUnitMeasure, 0, 3);

		UnitMeasure = new TextField();
		grid.add(UnitMeasure, 1, 3);
		
		Text inventoryValidityDay = new Text(" Validity day: ");
		inventoryValidityDay.setFont(myFont);
		inventoryValidityDay.setWrappingWidth(150);
		inventoryValidityDay.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryValidityDay, 0, 4);

		ValidityDays = new TextField();
		grid.add(ValidityDays, 1, 4);
		
		Text inventoryRoerderPoint = new Text(" Roerder point: ");
		inventoryRoerderPoint.setFont(myFont);
		inventoryRoerderPoint.setWrappingWidth(150);
		inventoryRoerderPoint.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryRoerderPoint, 0, 5);

		ReorderPoint = new TextField();
		grid.add(ReorderPoint, 1, 5);
		
		Text inventoryNotes = new Text(" Notes: ");
		inventoryNotes.setFont(myFont);
		inventoryNotes.setWrappingWidth(150);
		inventoryNotes.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryNotes, 0, 6);

		Notes = new TextField();
		grid.add(Notes, 1, 6);
		
		Status = new ComboBox();
		Status.getItems().addAll(
				"Active",
				"Inactive");
		grid.add(Status, 1, 7);
		Status.getSelectionModel().selectFirst();


		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		cancelButton = new Button("Cancel");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("TellerView", null);   
            	  }
        	});
		
		submitButton = new Button("Update");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				updateFields();
   		    	clearErrorMessage();
   		    	myModel.stateChangeRequest("TellerView", null);   
				
			}
		});
		
		
		
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(submitButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	private void updateInventory() {
		// add the properties
		Properties p2 = new Properties();
		//p2.setProperty("Id", ""); // this field is auto incremented, dont touch
		/*p2.setProperty("Name", nameField.getText());
		p2.setProperty("PhoneNumber", phoneNumberField.getText());
		p2.setProperty("Status", statusBox.getValue().toString());
		
		// create item using properties, then add to db
		InventoryItemType iIT = new InventoryItemType(p2);
		ve.update();*/
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
	
	public void populateFields()
	{
		iitc.getInventoryItemTypeName(UpdateInventoryView.SELECTED_ITEM);
		UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeName(UpdateInventoryView.SELECTED_ITEM);
		InventoryItemType iit = UpdateInventoryView.INVENTORY_ITEM_TYPE_COLLECTION.getInventoryItemTypeList().get(0);
		Units.setText(iit.getField("Units"));
		UnitMeasure.setText(iit.getField("UnitMeasure"));
		ValidityDays.setText(iit.getField("ValidityDays"));
		ReorderPoint.setText(iit.getField("ReorderPoint"));
		Notes.setText(iit.getField("Notes"));
		Status.getSelectionModel().select(iit.getField("Status"));
	}
	
	public void updateFields() {
		String str = UpdateInventoryView.SELECTED_ITEM;
		iitc.updateInventoryItemTypeWithName(str, "Units", Units.getText());
		iitc.updateInventoryItemTypeWithName(str, "UnitMeasure", UnitMeasure.getText());
		iitc.updateInventoryItemTypeWithName(str, "ValidityDays", ValidityDays.getText());
		iitc.updateInventoryItemTypeWithName(str, "ReorderPoint", ReorderPoint.getText());
		iitc.updateInventoryItemTypeWithName(str, "Notes", Notes.getText());
		iitc.updateInventoryItemTypeWithName(str, "Status", (String)Status.getSelectionModel().getSelectedItem());
	}
}
