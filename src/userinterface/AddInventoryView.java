// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Properties;
import java.util.regex.Pattern;

import javafx.util.StringConverter;
import model.InventoryItem;
import model.InventoryItemType;
// project imports
import impresario.IModel;

public class AddInventoryView extends View{
	// GUI components
	//Restaurant owner provides the Inventory Manager
	//with the Item Type Name, Units, Unit Measure, Validity Days,
	//Reorder Point, and Notes of the Inventory Item Type
	// GUI components
	protected TextField TypeName;
	protected TextField Units;
	protected TextField UnitMeasure;
	protected TextField ValidityDay;
	protected TextField RoerderPoint;
	protected TextField Notes;
	protected TextField ItemType;
	protected ComboBox Status;

	protected TextField serviceCharge;


	protected Button cancelButton, submitButton;

	// For showing error message
	protected MessageView statusLog;
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddInventoryView(IModel account)
	{
		super(account, "AddInventoryView");

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
		VBox vbox = new VBox(20);

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

        Text itemTypeNameLabel = new Text(" Inventory Item Type Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		itemTypeNameLabel.setFont(myFont);
		itemTypeNameLabel.setWrappingWidth(150);
		itemTypeNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(itemTypeNameLabel, 0, 1);

		TypeName = new TextField();
		grid.add(TypeName, 1, 1);

        Text inventoryUnits = new Text(" Units : ");
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

		ValidityDay = new TextField();
		grid.add(ValidityDay, 1, 4);

		Text inventoryRoerderPoint = new Text(" Roerder point: ");
		inventoryRoerderPoint.setFont(myFont);
		inventoryRoerderPoint.setWrappingWidth(150);
		inventoryRoerderPoint.setTextAlignment(TextAlignment.RIGHT);
		grid.add(inventoryRoerderPoint, 0, 5);

		RoerderPoint = new TextField();
		grid.add(RoerderPoint, 1, 5);

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
  		    	addInventoryItem();
  		     }
       	  });

		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(submitButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}

	private void addInventoryItem() {
		if ((TypeName == null ) || (TypeName.getLength() == 0))
		{
			displayErrorMessage("Please Enter name!");
			TypeName.requestFocus();
		}
		else if ((Units == null) || (Units.getLength() == 0))
		{
			displayErrorMessage("Please a valid unit!");
			Units.requestFocus();
			return;
		}
		else if (Integer.parseInt(Units.getText()) < 0)
		{
			displayErrorMessage("You can't put a negativ unit!");
			Units.requestFocus();
		}
		else if ((UnitMeasure == null) || (UnitMeasure.getLength() == 0))
		{
				displayErrorMessage("Please Enter Valid unit measure");
				UnitMeasure.requestFocus();
				return;
		}
		else if (Integer.parseInt(UnitMeasure.getText()) < 0)
		{
			displayErrorMessage("You can't put a negativ Unit Measure!");
			UnitMeasure.requestFocus();
		}
		else if ((ValidityDay == null) || (ValidityDay.getLength() == 0))
		{
			displayErrorMessage("Please enter a valid validity day");
			ValidityDay.requestFocus();
			return;
		}
		else if (Integer.parseInt(ValidityDay.getText()) < 0)
		{
			displayErrorMessage("You can't put a negativ Validity Day!");
			ValidityDay.requestFocus();
		}
		else if ((RoerderPoint == null) || (RoerderPoint.getLength() == 0))
		{
			displayErrorMessage("Please Enter a roerder point!");
			RoerderPoint.requestFocus();
		}
		else if ((Notes == null) || (Notes.getLength() == 0))
		{
			displayErrorMessage("Please Enter zip note!");
			Notes.requestFocus();
		}
		else if (Status == null)
		{
			displayErrorMessage("Please Enter a valid status!");
			Status.requestFocus();
		}
		else
		{
		// create the properties of the inventoryIte,
		Properties p1 = new Properties();
			//p1.setProperty("InventoryItemId", "1"); // this is auto incremented dont touch
			p1.setProperty("ItemTypeName", TypeName.getText());
			p1.setProperty("Units", Units.getText());
			p1.setProperty("UnitMeasure", UnitMeasure.getText());
			p1.setProperty("ValidityDays", ValidityDay.getText());
			p1.setProperty("ReorderPoint", RoerderPoint.getText());
			p1.setProperty("Notes", Notes.getText());
			p1.setProperty("Status", Status.getValue().toString());
			// Create the Inventory Item object with the properties we made
			InventoryItemType it = new InventoryItemType(p1);
			// add it to the db
			it.update();
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