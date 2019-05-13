
// specify the package
package userinterface;

// system imports
import java.text.NumberFormat;
import java.util.Properties;

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

// project imports
import impresario.IModel;

/** The class containing the Teller View for the ATM application */
//==============================================================
public class TellerView extends View {

	// GUI stuff
	private TextField userid;
	private PasswordField password;
	private Button addInventoryItem, updateInventoryItem, addVendor, modifyVendor, addVIIT, deleteVIIT, takeOut,
			processInv, Reorder, FullInv, ModifyII;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public TellerView(IModel teller) {

		super(teller, "TellerView");

		// create a container for showing the contents
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

		// populateFields();

		// STEP 0: Be sure you tell your model what keys you are interested in
		myModel.subscribe("LoginError", this);
	}

	// Create the label (Text) for the title of the screen
	// -------------------------------------------------------------
	private Node createTitle() {

		Text titleText = new Text("       Brockport Restauraunt Main Menu          ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);

		return titleText;
	}

	// Create the main form contents
	// -------------------------------------------------------------
	private GridPane createFormContents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		// data entry fields

		addInventoryItem = new Button("Add Inventory Item");
		addInventoryItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("AddInventoryView", props);
			}
		});

		HBox addIIBtn = new HBox(15);
		addIIBtn.setAlignment(Pos.CENTER);
		addIIBtn.getChildren().add(addInventoryItem);
		grid.add(addIIBtn, 1, 0);

		updateInventoryItem = new Button("Update/Delete Inventory Item");
		updateInventoryItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("UpdateInventoryView", props);
			}
		});

		HBox updateIIBtn = new HBox(15);
		updateIIBtn.setAlignment(Pos.CENTER);
		updateIIBtn.getChildren().add(updateInventoryItem);
		grid.add(updateIIBtn, 1, 1);

		addVendor = new Button("Add Vendor");
		addVendor.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("AddVendorView", props);
			}
		});

		HBox addVendorBtn = new HBox(15);
		addVendorBtn.setAlignment(Pos.CENTER);
		addVendorBtn.getChildren().add(addVendor);
		grid.add(addVendorBtn, 1, 2);

		modifyVendor = new Button("Modify Vendor");
		modifyVendor.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("ModifyVendorView", props);
			}
		});

		HBox modifyVendorBtn = new HBox(15);
		modifyVendorBtn.setAlignment(Pos.CENTER);
		modifyVendorBtn.getChildren().add(modifyVendor);
		grid.add(modifyVendorBtn, 1, 3);

		addVIIT = new Button("Add Vendor Inventory Item Type");
		addVIIT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("AddVIITView", props);
			}
		});

		HBox addVIITBtn = new HBox(15);
		addVIITBtn.setAlignment(Pos.CENTER);
		addVIITBtn.getChildren().add(addVIIT);
		grid.add(addVIITBtn, 1, 4);

		deleteVIIT = new Button("Delete Vendor Inventory Item Type");
		deleteVIIT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("DeleteVIITView", props);
			}
		});

		HBox deleteVIITBtn = new HBox(15);
		deleteVIITBtn.setAlignment(Pos.CENTER);
		deleteVIITBtn.getChildren().add(deleteVIIT);
		grid.add(deleteVIITBtn, 1, 5);

		takeOut = new Button("Take out Inventory Item");
		takeOut.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("TakeOutInventoryView", props);
			}
		});

		HBox takeOutBtn = new HBox(15);
		takeOutBtn.setAlignment(Pos.CENTER);
		takeOutBtn.getChildren().add(takeOut);
		grid.add(takeOutBtn, 1, 6);

		processInv = new Button("Process Invoice");
		processInv.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("ProcessInvoiceView", props);
			}
		});
		
		HBox processInvBtn = new HBox(15);
		processInvBtn.setAlignment(Pos.CENTER);
		processInvBtn.getChildren().add(processInv);
		grid.add(processInvBtn, 1, 7);

		ModifyII = new Button("Modify Inventory Item Status");
		ModifyII.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("ModifyIIView", props);
			}
		});

		HBox ModifyIIBtn = new HBox(15);
		ModifyIIBtn.setAlignment(Pos.CENTER);
		ModifyIIBtn.getChildren().add(ModifyII);
		grid.add(ModifyIIBtn, 1, 8);

		Reorder = new Button("Obtain Re-Order List");
		Reorder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("ReorderView", props);
			}
		});

		HBox ReorderBtn = new HBox(15);
		ReorderBtn.setAlignment(Pos.CENTER);
		ReorderBtn.getChildren().add(Reorder);
		grid.add(ReorderBtn, 1, 9);
		
		FullInv = new Button("Obtain Full Current Inventory");
		FullInv.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				processAction(e);
				Properties props = new Properties();
				myModel.stateChangeRequest("FullInventoryView", props);
			}
		});

		HBox FullInvBtn = new HBox(15);
		FullInvBtn.setAlignment(Pos.CENTER);
		FullInvBtn.getChildren().add(FullInv);
		grid.add(FullInvBtn, 1, 10);

		return grid;
	}

	// Create the status log field
	// -------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage) {

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	// -------------------------------------------------------------
	// public void populateFields()
	// {
	// userid.setText("");
	// password.setText("");
	// }

	// This method processes events generated from our GUI components.
	// Make the ActionListeners delegate to this method
	// -------------------------------------------------------------
	public void processAction(Event evt) {
		// DEBUG: System.out.println("TellerView.actionPerformed()");

		clearErrorMessage();

	}

	/**
	 * Process userid and pwd supplied when Submit button is hit. Action is to pass
	 * this info on to the teller object
	 */
	// ----------------------------------------------------------
	private void processUserIDAndPassword(String useridString, String passwordString) {
		Properties props = new Properties();
		props.setProperty("ID", useridString);
		props.setProperty("Password", passwordString);

		// clear fields for next time around
		userid.setText("");
		password.setText("");

		myModel.stateChangeRequest("Login", props);
	}

	// ---------------------------------------------------------
	public void updateState(String key, Object value) {
		// STEP 6: Be sure to finish the end of the 'perturbation'
		// by indicating how the view state gets updated.
		if (key.equals("LoginError") == true) {
			// display the passed text
			displayErrorMessage((String) value);
		}

	}

	/**
	 * Display error message
	 */
	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

}
