package bca;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class app2 extends Application {
    private Text dateText;

    @Override
    public void start(Stage primaryStage) {
        // Creating the Page Title
        Text pageTitle = new Text("River Edge Swim Club");

        // Creating the Date Text Element
        dateText = new Text();
        updateDate(); // Initial update
        // Schedule regular updates of the date (every second in this case)
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

        // Creating Column Headers (Assuming 5 headers)
        Text column1 = new Text("Chemical Name");
        Text column2 = new Text("Chemical Quantity");
        Text column3 = new Text("Description");
        Text column4 = new Text("Latest Transaction Date");
        Text column5 = new Text("User");

        // Creating the top left section for page title and date
        VBox topLeftSection = new VBox(5, pageTitle, dateText);
        topLeftSection.setAlignment(Pos.TOP_LEFT);
        topLeftSection.setPadding(new Insets(10));

        // Creating the section for centered column headers
        HBox columnHeaders = new HBox(20, column1, createVerticalLine(), column2, createVerticalLine(), column3, createVerticalLine(), column4, createVerticalLine(), column5);
        columnHeaders.setAlignment(Pos.CENTER);
        columnHeaders.setPadding(new Insets(10));

        // Creating the horizontal line under the headers
        Pane horizontalLine = createHorizontalLine();
        VBox.setMargin(horizontalLine, new Insets(10, 0, 10, 0));

        // Creating the buttons
        Button addButton = new Button("Add");
        Button editDeleteButton = new Button("Edit/Delete");
        HBox buttons = new HBox(20, addButton, editDeleteButton);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));

        // Creating the layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(topLeftSection, columnHeaders, horizontalLine, buttons);

        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("River Edge Swim Club Application");
        primaryStage.show();
    }

    private void updateDate() {
        // Update the date 
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        Date date = new Date();
        String formattedDate = dateFormat.format(date);
        dateText.setText(formattedDate);
    }

    private Line createVerticalLine() {
        Line line = new Line();
        line.setStartY(0);
        line.setEndY(20);
        line.setStyle("-fx-stroke: black;");
        return line;
    }

    private Pane createHorizontalLine() {
        Line line = new Line(0, 0, 600, 0);
        line.setStyle("-fx-stroke: black;");
        Pane pane = new Pane(line);
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
