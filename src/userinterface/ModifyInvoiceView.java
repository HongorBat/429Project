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
public class ModifyInvoiceView extends View{

		
		// GUI components
		//Restaurant owner provides the Inventory Manager 
		//with the Item Type Name, Units, Unit Measure, Validity Days, 
		//Reorder Point, and Notes of the Inventory Item Type
		// GUI components
		protected Text TypeName;
		protected TextField Barcode;
		protected TextField InventoryItemTypeName;
		protected TextField VendorId;
		protected TextField DateReceived;
		protected TextField DateOfLastUse;
		protected TextField Notes;
		protected ComboBox Status;
		
		protected TextField serviceCharge;
		//private InventoryItemCollection iic = new InventoryItemCollection("InventoryItem");


		protected Button cancelButton, submitButton;

		// For showing error message
		protected MessageView statusLog;
		// constructor for this class -- takes a model object
		//----------------------------------------------------------
		public ModifyInvoiceView(IModel account)
		{
			super(account, "ModifyInvoiceView");

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

			Text bCode = new Text("Barcode : ");
			Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
			bCode.setFont(myFont);
			bCode.setWrappingWidth(150);
			bCode.setTextAlignment(TextAlignment.RIGHT);
			grid.add(bCode, 0, 2);

			Barcode = new TextField();
			grid.add(Barcode, 1, 2);

			Text iITName = new Text(" InventoryItemTypeName : ");
			iITName.setFont(myFont);
			iITName.setWrappingWidth(150);
			iITName.setTextAlignment(TextAlignment.RIGHT);
			grid.add(iITName, 0, 3);

			InventoryItemTypeName = new TextField();
			InventoryItemTypeName.setEditable(false);
			grid.add(InventoryItemTypeName, 1, 3);
			
			Text vndrId = new Text(" VendorId: ");
			vndrId.setFont(myFont);
			vndrId.setWrappingWidth(150);
			vndrId.setTextAlignment(TextAlignment.RIGHT);
			grid.add(vndrId, 0, 4);

			VendorId = new TextField();
			VendorId.setEditable(false);
			grid.add(VendorId, 1, 4);
			
			Text dteRec = new Text(" Date Received: ");
			dteRec.setFont(myFont);
			dteRec.setWrappingWidth(150);
			dteRec.setTextAlignment(TextAlignment.RIGHT);
			grid.add(dteRec, 0, 5);

			DateReceived = new TextField();
			grid.add(DateReceived, 1, 5);
			
			Text dteLastUsed = new Text(" Date of Last Use: ");
			dteLastUsed.setFont(myFont);
			dteLastUsed.setWrappingWidth(150);
			dteLastUsed.setTextAlignment(TextAlignment.RIGHT);
			grid.add(dteLastUsed, 0, 6);

			DateOfLastUse = new TextField();
			grid.add(DateOfLastUse, 1, 6);
			
			Text notes = new Text(" Date of Last Use: ");
			notes.setFont(myFont);
			notes.setWrappingWidth(150);
			notes.setTextAlignment(TextAlignment.RIGHT);
			grid.add(notes, 0, 7);

			Notes = new TextField();
			grid.add(Notes, 1, 7);
			
			Status = new ComboBox();
			Status.getItems().addAll(
					"Available");
			grid.add(Status, 1, 8);
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
			
			submitButton = new Button("Submit");
			submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			submitButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					//updateFields();
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



