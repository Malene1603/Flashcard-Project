package com.example.flashcardproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class FrontscreenController {
    @FXML
    private Pane pane;

    @FXML
    private ListView<FlashcardSet> cardsListview;

    @FXML
    private Button deleteButton;

    @FXML
    private Button playButton;

    @FXML
    private Button uploadButton;
    private FlashcardDaoimpl fdi = new FlashcardDaoimpl();

    final FileChooser fileChooser = new FileChooser();

    private final ObservableList<FlashcardSet> cardSets = FXCollections.observableArrayList();

    public void initialize(){
        cardsListview.setItems(cardSets);
    }

    @FXML
    void deleteButtonClicked(ActionEvent event) {

    }

    @FXML
    void playButtonClicked(ActionEvent event) {

    }

    @FXML
    void uploadButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // display files in folder from which the app was launched
        fileChooser.setInitialDirectory(new File("."));

        // display the FileChooser
        File file = fileChooser.showOpenDialog(
                pane.getScene().getWindow());

        if (file != null) {
            // Create the dialog outside the loop
            Dialog<ButtonType> dialog = new Dialog();
            dialog.setTitle("Add new card set");
            dialog.setHeaderText("Add new card set");

            ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

            TextField categoryTextField = new TextField();
            categoryTextField.setPromptText("Category");
            VBox box = new VBox(categoryTextField);
            box.setPrefHeight(50);
            box.setPrefWidth(300);
            dialog.getDialogPane().setContent(box);

            Optional<ButtonType> button = dialog.showAndWait();

            if (button.isPresent() && button.get() == addButton) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Split the line based on the separator (assuming tab-separated values)
                        String[] values = line.split("\t");

                        // Extract values and insert into the database
                        if (values.length >= 3) {  // Ensure there are at least three columns
                            String cardID = values[0];
                            String category = values[1];
                            String question = values[2];

                            FlashcardSet fs = new FlashcardSet(0, category);

                            categoryTextField.setText(fs.getCategory());
                            fdi.addFlashcards(cardID, categoryTextField.getText(), question);
                        }

                    }
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
                // User clicked Add, insert into the database

            }
        }
    }
}
