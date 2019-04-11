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
import model.Vendor;

import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the Account View  for the ATM application */
//==============================================================
public class AddVendorView extends View
{

	// GUI components
	protected TextField nameField;
	protected TextField phoneNumberField;
	protected ComboBox statusBox;
	protected TextField serviceCharge;

	protected Button cancelButton, submitButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddVendorView(IModel account)
	{
		super(account, "AddVendorView");

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
        
        Text prompt = new Text("VENDOR INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text vendorNameLabel = new Text(" Vendor Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		vendorNameLabel.setFont(myFont);
		vendorNameLabel.setWrappingWidth(150);
		vendorNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vendorNameLabel, 0, 1);

		nameField = new TextField();
		grid.add(nameField, 1, 1);

		Text phoneLabel = new Text(" Vendor Phone Number : ");
		phoneLabel.setFont(myFont);
		phoneLabel.setWrappingWidth(150);
		phoneLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(phoneLabel, 0, 2);

		phoneNumberField = new TextField();
		grid.add(phoneNumberField, 1, 2);

		Text statusLabel = new Text(" Status : ");
		statusLabel.setFont(myFont);
		statusLabel.setWrappingWidth(150);
		statusLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(statusLabel, 0, 3);

		
		statusBox = new ComboBox();
		statusBox.getItems().addAll(
				"Active",
				"Inactive");
		grid.add(statusBox, 1, 3);
		statusBox.getSelectionModel().selectFirst();

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
			 	if (isSomethingEmpty()) {
			 		displayErrorMessage("Please fill out all the fields");
				} else {
			 		if (!phoneNumberField.getText().matches("\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d"))
					{
						displayErrorMessage("Please enter a valid phone number.");
						return;
					}
					clearErrorMessage();
					addVendor();
				}
   	  }
	});
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(submitButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	private void addVendor() {
		// add the properties
		Properties p2 = new Properties();
		//p2.setProperty("Id", ""); // this field is auto incremented, dont touch
		p2.setProperty("Name", nameField.getText());
		p2.setProperty("PhoneNumber", phoneNumberField.getText());
		p2.setProperty("Status", statusBox.getValue().toString());
		
		// create item using properties, then add to db
		Vendor ve = new Vendor(p2);
		ve.update();
	}

	private Boolean isSomethingEmpty() {
		if (nameField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
			return true;
		}
		return false;
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

//---------------------------------------------------------------
//	Revision History:
//


