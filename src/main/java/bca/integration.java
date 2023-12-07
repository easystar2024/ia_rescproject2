package bca; 

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class integration extends Application {
    private Text dateText;
    private Stage secondaryStage;
    private Stage editStage = new Stage();

    private TextField quantityField, descriptionField;
    private ComboBox<Chemical> chemicalNameField;
    private ComboBox<Name> nameField;
    private VBox dataRows;

    ObservableList<Chemical> chemicalList = FXCollections.observableArrayList();
        
        public void fillChemicalList(){
            chemicalList.clear();
            chemicalList.addAll(DB.loadChemical());
        }
    ObservableList<Name> nameList = FXCollections.observableArrayList();
        
        public void fillnameList(){
            nameList.clear();
            nameList.addAll(DB.loadName());
        }
    @Override
    public void start(Stage primaryStage) {
        // Creating the Page Title
        Text pageTitle = new Text("River Edge Swim Club");

        // Creating the Date Text Element
        dateText = new Text();
        updateDate();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateDate();
            }
        }).start();

        

        // Creating the top left section for page title and date
        VBox topLeftSection = new VBox(5, pageTitle, dateText);
        topLeftSection.setAlignment(Pos.TOP_LEFT);
        topLeftSection.setPadding(new Insets(10));

        // Creating the headers and lines in the main screen
        HBox headers = new HBox();
        headers.getChildren().addAll(
            createHeader("Chemical Name"),
            createVerticalLine(),
            createHeader("Quantity"),
            createVerticalLine(),
            createHeader("Description"),
            createVerticalLine(),
            createHeader("User Name")
            // createVerticalLine(),
            // createHeader("Last Name")
        );
        headers.setAlignment(Pos.CENTER);
        headers.setSpacing(20);

        dataRows = new VBox();
        Line line = createHorizontalLine();

        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(topLeftSection, headers, createHorizontalLine(), dataRows);

        // run a query that gets all rows from table
        // for each row call addDataRow (addDataToList)



        // Add button in the main screen
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            
            openSecondaryStage();
            
        });
        Button editDeleteButton = new Button("Edit/Delete");
        editDeleteButton.setOnAction(event -> {

            openEditStage();

        });

        HBox addLayout = new HBox(20, addButton, editDeleteButton);
        addLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(addLayout);

        Scene mainScene = new Scene(mainLayout, 600, 400);

        // Set up the secondary stage for data entry
        secondaryStage = new Stage();
        secondaryStage.setTitle("Data Entry");
        
        //Set up the secondary stage for edit/delete functions
        VBox secondaryLayout = new VBox(10);
        Text pageTitleSecondary = new Text("River Edge Swim Club");
        Text addChemicalText = new Text("Add Chemical Input");
        dateText = new Text();
        updateDate();
        secondaryLayout.getChildren().addAll(pageTitleSecondary, dateText, addChemicalText);
        fillChemicalList();
        chemicalNameField = new ComboBox<>(chemicalList);
        chemicalNameField.setPromptText("Chemical Name");
        quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        fillnameList();
        nameField = new ComboBox<>(nameList);
        nameField.setPromptText("User Name");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            addDataToList();
           //String chemical = chemicalNameField.getText();
            String quantity = quantityField.getText();
            String description = descriptionField.getText();
            //String firstName = firstNameField.getText();
            //String lastName = lastNameField.getText();
            DB.insertChemical(quantity, description);
            secondaryStage.close();
        });
    
        secondaryLayout.getChildren().addAll(chemicalNameField, quantityField, descriptionField, nameField, submitButton);
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.setPadding(new Insets(20));

        Scene secondaryScene = new Scene(secondaryLayout, 400, 300);
        secondaryStage.setScene(secondaryScene);

            editStage.setTitle("Edit/Delete Data");
    
            VBox editLayout = new VBox(10);
            Text editTitle = new Text("Edit/Delete Chemical Data");
            // Add more UI components here as needed for editing/deleting
            // Example: TextField for editing, Buttons for 'Save' and 'Delete' operations
            // ...
    
            editLayout.getChildren().addAll(editTitle /*, other UI components */);
            editLayout.setAlignment(Pos.CENTER);
            editLayout.setPadding(new Insets(20));
    
            Scene editScene = new Scene(editLayout, 400, 300);
            editStage.setScene(editScene);
    
        

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("River Edge Swim Club Application");
        primaryStage.show();
    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        dateText.setText(formattedDate);
    }

    private void openSecondaryStage() {
        secondaryStage.show();
    }
    private void openEditStage(){
            editStage.show();
    }
    private void addDataToList() {
        Chemical chemical = chemicalNameField.getValue();
        String quantity = quantityField.getText();
        String description = descriptionField.getText();
        Name userName = nameField.getValue();

        HBox row = createDataRow(chemical.getChem_name(), quantity, description, userName.getName());
        dataRows.getChildren().addAll(row, createHorizontalLine());
    }

    private Text createHeader(String text) {
        Text header = new Text(text);
        header.setStyle("-fx-font-weight: bold;");
        return header;
    }

    private Line createVerticalLine() {
        Line line = new Line();
        line.setStartY(0);
        line.setEndY(20);
        line.setStyle("-fx-stroke: black;");
        return line;
    }

    private Line createHorizontalLine() {
        Line line = new Line(0, 0, 600, 0);
        line.setStyle("-fx-stroke: black;");
        return line;
    }

    private HBox createDataRow(String col1, String col2, String col3, String col4) {
        return new HBox(20, new Text(col1), createVerticalLine(), new Text(col2), createVerticalLine(), new Text(col3), createVerticalLine(), new Text(col4), createVerticalLine());
    }

    public static void main(String[] args) {
        launch(args);
    }
}   
