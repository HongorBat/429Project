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
import model.VendorInventoryItemType;

import java.util.Properties;

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

	protected Button cancelButton, submitButton;

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

        Text iITNLabel = new Text(" Inventory Item Type Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		iITNLabel.setFont(myFont);
		iITNLabel.setWrappingWidth(150);
		iITNLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(iITNLabel, 0, 1);

		InventoryItemTypeName = new TextField();
		grid.add(InventoryItemTypeName, 1, 1);

		Text priceLabel = new Text(" Vendor Price : ");
		priceLabel.setFont(myFont);
		priceLabel.setWrappingWidth(150);
		priceLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(priceLabel, 0, 2);

		VendorPrice = new TextField();
		grid.add(VendorPrice, 1, 2);

		Text dateUpdatedLabel = new Text(" Date Last Updated : ");
		dateUpdatedLabel.setFont(myFont);
		dateUpdatedLabel.setWrappingWidth(150);
		dateUpdatedLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dateUpdatedLabel, 0, 3);
		
		DateLastUpdated = new TextField();
		grid.add(DateLastUpdated, 1, 3);

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
  		    	addVIIT();  
       	  }
   	});
		doneCont.getChildren().add(cancelButton);
		doneCont.getChildren().add(submitButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}
	
	private void addVIIT() {
		// add the properties
		Properties p3 = new Properties();
		//p2.setProperty("Id", ""); // this field is auto incremented, dont touch
		p3.setProperty("InventoryItemTypeName", InventoryItemTypeName.getText());
		p3.setProperty("VendorPrice", VendorPrice.getText());
		p3.setProperty("DateLastUpdated", DateLastUpdated.getText());       
        
		
		// create item using properties, then add to db
		VendorInventoryItemType ve = new VendorInventoryItemType(p3);
		ve.update();
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

