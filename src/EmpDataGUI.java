/**
 * 
 */


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Scott
 *
 */
public class EmpDataGUI extends Application {
	private BorderPane borderPane = new BorderPane();
    private GridPane main = new GridPane();	
    private ListController controller = new ListController();
    private BorderPane view = new BorderPane();
    private ScrollPane scroll = new ScrollPane();
    
    Alert alert;
    Alert noNameWarning;
    
    
    
    Alert underAgeWarning;
    Alert ssnError;
    Alert salaryError;
    Alert ageError;
    Alert numberFormatExceptionError;
    
    
    TextField firstNameTF;
    TextField lastNameTF;
    TextField ssnTF;
    TextField ageTF;
    TextField pronounsTF;
    TextField salaryTF;
    TextField yearsTF;
    
    ToggleGroup tg;
    RadioButton temp;
    
	RadioButton engineering;
	RadioButton marketingSales;
	RadioButton manufacturing;
	RadioButton finance;
	RadioButton humanResources;
	RadioButton customerSupport;
	RadioButton management;  
	
    // TODO #1:
    // create private TextField variables for Name, SSN, Salary and Years
    // so that they can be accessed directly by methods inside this class.
	
    @Override
    public void start(Stage primaryStage) {
    
    	Scene scene = new Scene(borderPane,400, 500);
    	Scene scene2 = new Scene(view, 400, 500);
    	
    	
    	//Part 4 updates
    	borderPane.setCenter(main);
    	Label employeeDataEntry = new Label("EMPLOYEE DATA ENTRY");
    	borderPane.setTop(employeeDataEntry);
    	

	// TODO #2:
    	// create Labels for Name, SSN, Salary and Years
    	Label lastName = new Label("Last Name:");
    	Label firstName = new Label("First Name:");
    	Label ssn = new Label("SSN:");
    	Label age = new Label("Age:");
    	Label pronouns = new Label("Pronouns:");
    	Label salary = new Label("Salary:");
    	Label years = new Label("Years:");
    	Label employeeData = new Label("Employee Data View");
    	view.setTop(employeeData);
    	
    	
	// TODO #4
    	// instantiate (new) TextFields (already declared above) for Name, SSN, Salary and Years
    	lastNameTF = new TextField("");
    	firstNameTF = new TextField("");
    	ssnTF = new TextField("");
    	ageTF = new TextField("");
    	pronounsTF = new TextField("");
    	salaryTF = new TextField("");
    	yearsTF = new TextField("");
    	
   
	// TODO #5
        // Create Add Employee Button, and write the setOnAction handler to call the controller
    	// to add the new Employee data
    	Button addEmployee = new Button("Add Employee");
    	Button viewData = new Button("Employee Data");
    	Button saveData = new Button("Save Data");
    	
    	HBox bottomPaneHBox = new HBox(20);
    	bottomPaneHBox.getChildren().addAll(addEmployee, viewData, saveData);	
    	borderPane.setBottom(bottomPaneHBox);
    	
    	
    	Button backBtn = new Button("Back");
    	Button sortByName = new Button("Sort By Name");
    	Button sortByID = new Button("Sort By ID");
    	Button sortBySalary = new Button("Sort By Salary");
    	
    	HBox employeeDataHBox = new HBox(20);
    	employeeDataHBox.getChildren().addAll(backBtn, sortByName, sortByID, sortBySalary);
    	view.setBottom(employeeDataHBox);
    	 	
    	//RADIO BUTTONS
    	engineering = new RadioButton("Engineering");
    	marketingSales = new RadioButton("Markeing/Sales");
    	manufacturing = new RadioButton("Manufacturing");
    	finance = new RadioButton("Finance");
    	humanResources = new RadioButton("Human Resources");
    	customerSupport = new RadioButton("Customer Support");
    	management = new RadioButton("Management");    	
    	
    	//addEmployee.setOnAction(e -> controller.addEmployee(nameTF.getText(), ssnTF.getText(), salaryTF.getText(), yearsTF.getText()));
    	addEmployee.setOnAction(e -> basicErrorChecking());
    	backBtn.setOnAction(e -> primaryStage.setScene(scene));
    	viewData.setOnAction(e -> {viewEmployeeDB(); primaryStage.setScene(scene2);});
    	saveData.setOnAction(e -> controller.saveEmployeeData());
    	sortBySalary.setOnAction(e -> {controller.sortBySalary(); viewEmployeeDB(); System.out.println("sortBySalary pressed");});
    	sortByID.setOnAction(e -> {controller.sortByID(); viewEmployeeDB(); System.out.println("sortByID pressed");});
    	sortByName.setOnAction(e -> {controller.sortByName(); viewEmployeeDB(); System.out.println("sortByName pressed");});
    	
    	
    	
    	view.setCenter(scroll);
    	
		alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Add Employee Failed");
		alert.setContentText("Make sure that all fields are populated");
		
		noNameWarning = new Alert(AlertType.WARNING);
		noNameWarning.setHeaderText("Invalid Names");
		noNameWarning.setContentText("Make sure that the name fields are populated");
		
		underAgeWarning = new Alert(AlertType.ERROR);
		underAgeWarning.setHeaderText("Under Age Employee");
		underAgeWarning.setContentText("We do not accept workers who are under the age of 16");
		
		ssnError = new Alert(AlertType.ERROR);
		ssnError.setHeaderText("Invalid SSN");
		ssnError.setContentText("Invalid SSN format, or duplicate SSN \nFormat: xxx-xx-xxxx");
		
		salaryError = new Alert(AlertType.ERROR);
		salaryError.setHeaderText("Invalid Salary");
		salaryError.setContentText("The minimum salary is $30,000");
		
		ageError = new Alert(AlertType.ERROR);
		ageError.setHeaderText("Invalid Age");
		ageError.setContentText("Age must be greater than # years worked + the min age (16)");
		
		numberFormatExceptionError = new Alert(AlertType.ERROR);
		numberFormatExceptionError.setHeaderText("Invalid Characters");
		numberFormatExceptionError.setContentText("Please put in valid characters for each field");
		
		
    	
	// TODO #6
    	// add all the labels, textfields and button to gridpane main. refer to the slide
    	// for ordering...
		main.add(lastName, 0, 0);
    	main.add(firstName, 0, 1);
    	main.add(ssn, 0, 2);
    	main.add(age, 0, 3);
    	main.add(pronouns, 0, 4);
    	main.add(salary, 0, 5);
    	main.add(years, 0, 6);
    	
    	main.add(lastNameTF, 1, 0);
    	main.add(firstNameTF, 1, 1);
    	main.add(ssnTF, 1, 2);
    	main.add(ageTF, 1, 3);
    	main.add(pronounsTF, 1, 4);
    	main.add(salaryTF, 1, 5);
    	main.add(yearsTF, 1, 6);
    	
    	main.add(engineering, 1, 7);
    	main.add(marketingSales, 1, 8);
    	main.add(manufacturing, 1, 9);
    	main.add(finance, 1, 10);
    	main.add(humanResources, 1, 11);
    	main.add(customerSupport, 1, 12);
    	main.add(management, 1, 13);
    	main.add(new Label("Dept:"), 0, 10);
    	
    	engineering.setSelected(true);
    	
    	tg = new ToggleGroup();
    	engineering.setToggleGroup(tg);
    	marketingSales.setToggleGroup(tg);
    	manufacturing.setToggleGroup(tg);
    	finance.setToggleGroup(tg);
    	humanResources.setToggleGroup(tg);
    	customerSupport.setToggleGroup(tg);
    	management.setToggleGroup(tg);
    	
    	primaryStage.setTitle("Employees");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void viewEmployeeDB() {
    	String[] empDataStr = controller.getEmployeeDataStr();
    	ListView<String> lv = new ListView<>(FXCollections.observableArrayList(empDataStr));
    	lv.setPrefWidth(400);
    	scroll.setContent(lv);
    }
    
    private void basicErrorChecking() {
    	
    	temp = (RadioButton) tg.getSelectedToggle();
    	String addEmployeeReturnCode = controller.addEmployee(firstNameTF.getText(), lastNameTF.getText(), ssnTF.getText(), ageTF.getText(), pronounsTF.getText(), salaryTF.getText(), yearsTF.getText(), temp.getText());		
    	if(addEmployeeReturnCode != "") {
    		if(addEmployeeReturnCode.equals("numberFormatException")) {
    			numberFormatExceptionError.showAndWait();
    		} else if(addEmployeeReturnCode.equals("salaryError")) {
    			salaryError.showAndWait();
    		} else if(addEmployeeReturnCode.equals("ssnError")) {
    			ssnError.showAndWait();
    		} else if(addEmployeeReturnCode.equals("underAgeError")) {
    			underAgeWarning.showAndWait();
    		} else if(addEmployeeReturnCode.equals("noNameError")) {
    			noNameWarning.showAndWait();
    		} else if(addEmployeeReturnCode.equals("ageError")) {
    			ageError.showAndWait();
    		} else {
    			alert.showAndWait();
    		}
    	} else {
    		//System.out.println("EMPLOYEE ADDED"); // for debugging
    		clearTextFields();
    	}
    }
    
    private void clearTextFields() {
    	lastNameTF.clear(); firstNameTF.clear(); ssnTF.clear(); ageTF.clear(); pronounsTF.clear(); salaryTF.clear(); yearsTF.clear(); engineering.setSelected(true);
    }
    
  /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}	
	
} ;
