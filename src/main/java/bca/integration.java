package bca; 

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class integration extends Application {
    private Text dateText;
    private Stage secondaryStage;
    private TextField chemicalNameField, quantityField, descriptionField, firstNameField, lastNameField;
    private VBox dataRows;

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
            createHeader("First Name"),
            createVerticalLine(),
            createHeader("Last Name")
        );
        headers.setAlignment(Pos.CENTER);

        dataRows = new VBox();
        Line line = createHorizontalLine();

        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(topLeftSection, headers, createHorizontalLine(), dataRows);

        // Add button in the main screen
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            
            openSecondaryStage();
            
        });

        HBox addLayout = new HBox(20, addButton);
        addLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(addLayout);

        Scene mainScene = new Scene(mainLayout, 600, 400);

        // Set up the secondary stage for data entry
        secondaryStage = new Stage();
        secondaryStage.setTitle("Data Entry");

        VBox secondaryLayout = new VBox(10);
        Text pageTitleSecondary = new Text("River Edge Swim Club");
        Text addChemicalText = new Text("Add Chemical Input");
        dateText = new Text();
        updateDate();
        secondaryLayout.getChildren().addAll(pageTitleSecondary, dateText, addChemicalText);

        chemicalNameField = new TextField();
        chemicalNameField.setPromptText("Chemical Name");
        quantityField = new TextField();
        quantityField.setPromptText("Quantity");
        descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            addDataToList();
            String chemical = chemicalNameField.getText();
            String quantity = quantityField.getText();
            String description = descriptionField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            DB.insertChemical(chemical, quantity, description, firstName, lastName);
            secondaryStage.close();
        });

        secondaryLayout.getChildren().addAll(chemicalNameField, quantityField, descriptionField, firstNameField, lastNameField, submitButton);
        secondaryLayout.setAlignment(Pos.CENTER);
        secondaryLayout.setPadding(new Insets(20));

        Scene secondaryScene = new Scene(secondaryLayout, 400, 300);
        secondaryStage.setScene(secondaryScene);

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

    private void addDataToList() {
        // Implement logic to add the input data to the list displayed in the main screen
        // Get the data from the fields and display it in the main screen
        // Example: create labels/text and add them to the mainLayout in the appropriate section
        // Use the data from the fields: chemicalNameField.getText(), quantityField.getText(), etc.
        String chemical = chemicalNameField.getText();
        String quantity = quantityField.getText();
        String description = descriptionField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        HBox row = createDataRow(chemical, quantity, description, firstName, lastName);
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

    private HBox createDataRow(String col1, String col2, String col3, String col4, String col5) {
        return new HBox(20, new Text(col1), createVerticalLine(), new Text(col2), createVerticalLine(), new Text(col3), createVerticalLine(), new Text(col4), createVerticalLine(), new Text(col5));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
