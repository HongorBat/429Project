package userinterface;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InsertBook;

public class InsertBookView extends View
{

	// GUI components
	protected TextField bookIdField;
	protected TextField authorField;
	protected TextField titleField;
	protected TextField pubYearField;
	protected TextField statusField;
	
	protected ComboBox<String> statusComboBox;

	protected Button backButton;
	protected Button insertButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public InsertBookView(IModel book)
	{
		super(book, "InsertBookView");

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

		Text titleText = new Text(" Brockport Bank ATM ");
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
        
        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text bookIdLabel = new Text("Id : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		/*bookIdLabel.setFont(myFont);
		bookIdLabel.setWrappingWidth(150);
		bookIdLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(bookIdLabel, 0, 1);

		bookIdField = new TextField();
		grid.add(bookIdField, 1, 1);*/

		Text bookAuthorLabel = new Text("Author: ");
		bookAuthorLabel.setFont(myFont);
		bookAuthorLabel.setWrappingWidth(150);
		bookAuthorLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(bookAuthorLabel, 0, 2);

		authorField = new TextField();
		grid.add(authorField, 1, 2);

		Text titleLabel = new Text("Title: ");
		titleLabel.setFont(myFont);
		titleLabel.setWrappingWidth(150);
		titleLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(titleLabel, 0, 3);

		titleField = new TextField();
		grid.add(titleField, 1, 3);

		Text pubYearLabel = new Text("Publication Year : ");
		pubYearLabel.setFont(myFont);
		pubYearLabel.setWrappingWidth(150);
		pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(pubYearLabel, 0, 4);

		pubYearField = new TextField();
		grid.add(pubYearField, 1, 4);
		
		Text statusLabel = new Text("Status :");
		statusLabel.setFont(myFont);
		statusLabel.setWrappingWidth(150);
		statusLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(statusLabel, 0, 5);
		
		statusComboBox = new ComboBox<String>();
		statusComboBox.setItems(FXCollections.observableArrayList("Active", "Inactive"));
		statusComboBox.setValue("Active");
		//statusField = new TextField();
		grid.add(statusComboBox, 1, 5);
		
		

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		backButton = new Button("Done");
		backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest(InsertBook.INSERT_BOOK_BACK, null);   	
			}	
		});
		doneCont.getChildren().add(backButton);
		
		insertButton = new Button("Submit");
		insertButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		insertButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				if (authorField.getText().length() == 0 || titleField.getText().length() == 0) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Please enter valid Author and/or Title");
					a.show();
					return; 
				} else if (ensureIntegerValue(pubYearField.getText()) == false) {
					Alert a = new Alert(AlertType.ERROR);
					a.setContentText("Please enter a year between 1800 and 2019");
					a.show();
					return;
				}
				InsertBook ib = (InsertBook)myModel;
				//ib.updateState("BookId", bookIdField.getText());
				ib.updateState("Title", titleField.getText());
				ib.updateState("Author", authorField.getText());
				ib.updateState("PubYear", pubYearField.getText());
				ib.updateState("Status", statusComboBox.getValue());
				myModel.stateChangeRequest(InsertBook.INSERT_BOOK_INSERT, null); 
				Alert a = new Alert(AlertType.CONFIRMATION);
				a.setContentText("Book has been inserted.");
				a.show();
			}	
		});
		doneCont.getChildren().add(insertButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);
		return vbox;
	}
	
	private boolean ensureIntegerValue(String pubYear) {
		try {
			int val = Integer.parseInt(pubYear);
			return val >= 1800 && val <= 2019;
		} catch (Exception ex) {
			return false;
		}
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

		if (key.equals("ServiceCharge") == true)
		{
			String val = (String)value;
			pubYearField.setText(val);
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