package userinterface;


import java.util.Properties;

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import model.InsertPatron;
import model.MaleablePatron;
import model.Patron;
import model.PatronCollection;
import model.SearchPatron;
import model.Teller;

public class SearchPatronView extends View
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


	protected Button cancelButton;
	protected Button searchButton;
	
	protected TableView<String> table;
	
	InsertPatron cancel = new InsertPatron();
	

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchPatronView(IModel patron)
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

		Text titleText = new Text(" Search For A Patron ");
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
        

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));     
        
        table = new TableView<String>();
        TableColumn firstNameCol = new TableColumn("PatronId");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("PatronId"));
        TableColumn lastNameCol = new TableColumn("Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("Name"));
        TableColumn addressCol = new TableColumn("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("Address"));
        TableColumn cityCol = new TableColumn("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("City"));
        TableColumn stateCol = new TableColumn("StateCode");
        stateCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("StateCode"));
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("Email"));
        TableColumn birthCol = new TableColumn("DateOfBirth");
        birthCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("DateOfBirth"));
        TableColumn statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<MaleablePatron, String>("Status"));
        
        table.getColumns().setAll(firstNameCol, lastNameCol, addressCol, cityCol, stateCol, emailCol, birthCol, statusCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        //final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
  
        
		
		Text zipLabel = new Text(" Patron Zip: ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
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


		HBox cancelB = new HBox(10);
		cancelB.setAlignment(Pos.CENTER);
		cancelButton = new Button("Back");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	cancel.cancelView();
            	  }
        	});
		cancelB.getChildren().add(cancelButton);
		
		HBox searchB = new HBox(10);
		searchB.setAlignment(Pos.CENTER_RIGHT);
		searchButton = new Button("Search");
		searchButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	SearchPatron sp = (SearchPatron)myModel;
    				sp.updateState("Zip", zip.getText());
    				myModel.stateChangeRequest(SearchPatron.SEARCH_FOR_PATRON, null); 
    				ObservableList ol = FXCollections.observableArrayList(sp.getCollectionOfPatronsForTable());
    				table.setItems(ol);
            	  }
        	});
		cancelB.getChildren().add(searchButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(cancelB);

		return vbox;
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


