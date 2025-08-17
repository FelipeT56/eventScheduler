import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Event;
import controller.EventController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private EventController controller = new EventController();
    private ListView<Event> listView = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        controller.loadEventsFromFile(); // load saved events
        listView.getItems().addAll(controller.getEvents());

        primaryStage.setTitle("Events For You");

        // Input fields
        TextField titleField = new TextField();
        titleField.setPromptText("Event Title");

        TextField locationField = new TextField();
        locationField.setPromptText("Location");

        TextField startField = new TextField();
        startField.setPromptText("Start Time (yyyy-MM-ddTHH:mm)");

        TextField endField = new TextField();
        endField.setPromptText("End Time (yyyy-MM-ddTHH:mm)");

        // Buttons
        Button addButton = new Button("Add Event");
        Button removeButton = new Button("Remove Selected");

        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String location = locationField.getText();
                LocalDateTime start = LocalDateTime.parse(startField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime end = LocalDateTime.parse(endField.getText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                Event event = new Event(title, start, end, location);
                controller.addEvent(event);
                listView.getItems().add(event);

                // Clear inputs
                titleField.clear();
                locationField.clear();
                startField.clear();
                endField.clear();

            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input. Please check date format.");
                alert.showAndWait();
            }
        });

        removeButton.setOnAction(e -> {
            Event selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.removeEvent(selected);
                listView.getItems().remove(selected);
            }
        });

        // Layout
        VBox inputBox = new VBox(5, titleField, locationField, startField, endField, addButton, removeButton);
        inputBox.setPadding(new Insets(10));

        HBox root = new HBox(10, inputBox, listView);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        controller.saveEventsToFile(); // save events when app closes
    }

    public static void main(String[] args) {
        launch(args);
    }
}
