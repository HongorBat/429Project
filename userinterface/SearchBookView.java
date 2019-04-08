package userinterface;

import impresario.IModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.InsertBook;
import model.MutableBook;
import model.SearchBook;

public class SearchBookView extends View {

	// GUI components
	protected TextField bookIdField;
	protected TextField authorField;
	protected TextField titleField;
	protected TextField pubYearField;
	protected TextField statusField;
	protected TextField titleLikeField;
	
	protected TableView<String> tv;

	protected Button backButton;
	protected Button insertButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object

	public SearchBookView(IModel model) {
		super(model, "SearchBookView");
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
        
        Text prompt = new Text("BOOK INFO");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        tv = new TableView<String>();
        TableColumn c1 = new TableColumn("Book Id");
        c1.setCellValueFactory(new PropertyValueFactory<MutableBook, String>("id"));
        TableColumn c2 = new TableColumn("Author");
        c2.setCellValueFactory(new PropertyValueFactory<MutableBook, String>("author"));
        TableColumn c3 = new TableColumn("Title");
        c3.setCellValueFactory(new PropertyValueFactory<MutableBook, String>("title"));
        TableColumn c4 = new TableColumn("PubYear");
        c4.setCellValueFactory(new PropertyValueFactory<MutableBook, String>("pubYear"));
        TableColumn c5 = new TableColumn("Status");
        c5.setCellValueFactory(new PropertyValueFactory<MutableBook, String>("status"));
        tv.getColumns().setAll(c1, c2, c3, c4, c5);
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //table.setPrefWidth(450);
        //table.setPrefHeight(300);
        //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
		grid.add(tv, 0, 0);
		
		

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		backButton = new Button("Back");
		backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				myModel.stateChangeRequest(SearchBook.SEARCH_BOOK_BACK, null);   	
			}	
		});
		doneCont.getChildren().add(backButton);
		
		Text titleLikeText = new Text("Title: ");
		doneCont.getChildren().add(titleLikeText);
		
		titleLikeField = new TextField();
		doneCont.getChildren().add(titleLikeField);
		
		
		insertButton = new Button("Search");
		insertButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		insertButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				SearchBook sb = (SearchBook)myModel;
				sb.updateState("TitleLike", titleLikeField.getText());
				myModel.stateChangeRequest(SearchBook.SEARCH_BOOK_SEARCH, null); 
				ObservableList ol = FXCollections.observableArrayList(sb.getCollectionOfBooksForTable());
				tv.setItems(ol);
		
			}	
		});
		doneCont.getChildren().add(insertButton);
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);
		return vbox;
	}


	public void populateTable() {
		
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
