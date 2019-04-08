package userinterface;


import java.util.Properties;

import impresario.IModel;
import javafx.collections.FXCollections;
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
import model.InsertPatron;
import model.Patron;
import model.PatronCollection;
import model.Teller;

public class InsertPatronView extends View
{

	// GUI components
	protected TextField patronId;
	protected TextField name;
	protected TextField address;
	protected TextField city;
	protected TextField stateCode;
	protected TextField zip;
	protected TextField email;
	protected TextField dateOfBirth;
	protected TextField status;

	protected ComboBox<String> statusComboBox;

	protected Button cancelButton;
	protected Button insertButton;
	
	InsertPatron cancel = new InsertPatron();
	

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public InsertPatronView(IModel patron)
	{
		super(patron, "PatronView");

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

		Text titleText = new Text(" Insert a new Patron ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.BLACK);
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
        
        Text patronInfoLabel = new Text("PATRON INFORMATION");
        patronInfoLabel.setWrappingWidth(400);
        patronInfoLabel.setTextAlignment(TextAlignment.CENTER);
        patronInfoLabel.setFill(Color.BLACK);
        grid.add(patronInfoLabel, 0, 0, 2, 1);

		Text patronNameLabel = new Text(" Patron Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		patronNameLabel.setFont(myFont);
		patronNameLabel.setWrappingWidth(150);
		patronNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(patronNameLabel, 0, 1);

		name = new TextField();
		name.setEditable(true);
		name.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("name", name.getText());
     	     }
      });
		grid.add(name, 1, 1);

		Text patronAddressLabel = new Text(" Patron Address : ");
		patronAddressLabel.setFont(myFont);
		patronAddressLabel.setWrappingWidth(150);
		patronAddressLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(patronAddressLabel, 0, 2);

		address = new TextField();
		address.setEditable(true);
		address.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("address", address.getText());
     	     }
      });
		grid.add(address, 1, 2);

		Text patronCityLabel = new Text(" Patron City : ");
		patronCityLabel.setFont(myFont);
		patronCityLabel.setWrappingWidth(150);
		patronCityLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(patronCityLabel, 0, 3);

		city = new TextField();
		city.setEditable(true);
		city.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("city", city.getText());
     	     }
      });
		grid.add(city, 1, 3);

		Text scLabel = new Text(" Patron State Code : ");
		scLabel.setFont(myFont);
		scLabel.setWrappingWidth(150);
		scLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(scLabel, 0, 4);

		stateCode = new TextField();
		stateCode.setEditable(true);
		stateCode.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("stateCode", stateCode.getText());
     	     }
      });
		grid.add(stateCode, 1, 4);
		
		Text zipLabel = new Text(" Patron Zip: ");
		zipLabel.setFont(myFont);
		zipLabel.setWrappingWidth(150);
		zipLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(zipLabel, 0, 5);

		zip = new TextField();
		zip.setEditable(true);
		zip.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("zip", zip.getText());
     	     }
      });
		grid.add(zip, 1, 5);
		
		Text emailLabel = new Text(" Patron Email: ");
		emailLabel.setFont(myFont);
		emailLabel.setWrappingWidth(150);
		emailLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(emailLabel, 0, 6);

		email = new TextField();
		email.setEditable(true);
		email.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("email", email.getText());
     	     }
      });
		grid.add(email, 1, 6);
		
		Text dobLabel = new Text(" Patron Date of Birth: ");
		dobLabel.setFont(myFont);
		dobLabel.setWrappingWidth(150);
		dobLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dobLabel, 0, 7);

		dateOfBirth = new TextField();
		dateOfBirth.setEditable(true);
		dateOfBirth.setOnAction(new EventHandler<ActionEvent>() {

		     @Override
		     public void handle(ActionEvent e) {
		    	clearErrorMessage();
		    	myModel.stateChangeRequest("dateOfBirth", dateOfBirth.getText());
     	     }
      });
		grid.add(dateOfBirth, 1, 7);
		
		Text statusLabel = new Text(" Patron Status: ");
		statusLabel.setFont(myFont);
		statusLabel.setWrappingWidth(150);
		statusLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(statusLabel, 0, 8);

		statusComboBox = new ComboBox<String>();
		statusComboBox.setItems(FXCollections.observableArrayList("A", "I"));
		statusComboBox.setValue("A");
		grid.add(statusComboBox, 1, 8);

		HBox cancelB = new HBox(10);
		cancelB.setAlignment(Pos.CENTER);
		cancelButton = new Button("Done");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	cancel.cancelView();
            	  }
        	});
		cancelB.getChildren().add(cancelButton);
		
		HBox insertB = new HBox(10);
		insertB.setAlignment(Pos.CENTER_RIGHT);
		insertButton = new Button("Submit");
		insertButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		insertButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	
       		    	if (containsNoEmptyFields() == false) {
       		    		Alert a = new Alert(AlertType.ERROR);
    					a.setContentText("Please complete all fields");
    					a.show();
    					return;
       		    	}
       		    	String dob = dateOfBirth.getText().substring(0, 4);
       		    	if (ensureIntegerValue(dob) == false) {
       		    		Alert a = new Alert(AlertType.ERROR);
    					a.setContentText("Please enter a valid year");
    					a.show();
    					return;
       		    	}
       		    	
       		    	InsertPatron ip = (InsertPatron)myModel;
    				ip.updateState("Name", name.getText());
    				ip.updateState("Address", address.getText());
    				ip.updateState("City", city.getText());
    				ip.updateState("StateCode", stateCode.getText());
    				ip.updateState("Zip", zip.getText());
    				ip.updateState("Email",email.getText());
    				ip.updateState("DateOfBirth", dateOfBirth.getText());
    				ip.updateState("Status", statusComboBox.getValue());
    				myModel.stateChangeRequest(InsertPatron.INSERT_PATRON_INSERT, null); 
            	  }
        	});
		cancelB.getChildren().add(insertButton);
		vbox.getChildren().add(grid);
		vbox.getChildren().add(cancelB);
		return vbox;
	}
	
	private boolean ensureIntegerValue(String pubYear) {
		try {
			int val = Integer.parseInt(pubYear);
			return val >= 1917 && val <= 2000;
		} catch (Exception ex) {
			return false;
		}
	}
	public boolean containsNoEmptyFields() {
		return name.getText().length() > 0 &&
				address.getText().length() > 0 &&
				city.getText().length() > 0 &&
				stateCode.getText().length() > 0 &&
				zip.getText().length() > 0 && 
				email.getText().length() > 0 &&
				dateOfBirth.getText().length() > 0;
	}

	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		//accountNumber.setText((String)myModel.getState("AccountNumber"));
		//acctType.setText((String)myModel.getState("Type"));
		//balance.setText((String)myModel.getState("Balance"));
	 	//serviceCharge.setText((String)myModel.getState("ServiceCharge"));
	}

	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("name") == true)
		{
			String val = (String)value;
			name.setText(val);
			displayMessage("Patron name: " + val);
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
