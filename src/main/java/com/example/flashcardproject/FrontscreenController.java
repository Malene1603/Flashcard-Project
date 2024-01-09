package com.example.flashcardproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import java.util.Arrays;
import java.util.Optional;

public class FrontscreenController {
    @FXML
    private Pane pane;

    @FXML
    private Button uploadButton;
    private FlashcardDaoimpl fdi = new FlashcardDaoimpl();

    final FileChooser fileChooser = new FileChooser();

    private Boolean playmode;



    public void initialize(){
        if(fdi.somethingInDatabase()){
            uploadButton.setText("Play");
            playmode = true;
        } else{
            uploadButton.setText("Import cards");
            playmode = false;
        }
    }

    void  changeScene(Scene scene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlashcardApplication.class.getResource("FlashcardScreen.fxml"));
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void uploadButtonClicked(ActionEvent event) throws IOException {
        if (playmode = true){
            Scene currentScene = uploadButton.getScene();
            changeScene(currentScene);

        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");

            // display files in folder from which the app was launched
            fileChooser.setInitialDirectory(new File("."));

            // display the FileChooser
            File file = fileChooser.showOpenDialog(
                    pane.getScene().getWindow());

            if (file != null) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath()))) {
                    String line;

                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().startsWith("#")) {
                            String[] values = line.split("\t");

                            if (values.length >= 3) {
                                String cardID = values[0];
                                String category = values[1];
                                String question = values[2];
                                String artwork = values[3];
                                String artist = values[4];
                                String title = values[5];

                                String subtitle = values.length >= 7 ? values[6] : "";
                                String date = values.length >= 8 ? values[7] : "";
                                String period = values.length >= 9 ? values[8] : "";
                                String medium = values.length >= 10 ? values[9] : "";
                                String nationality = values.length >= 11 ? values[10] : "";
                                String note = values.length >= 12 ? values[11] : "";
                                String tags = values.length >= 13 ? values[12] : "";

                                fdi.addFlashcards(cardID, category, question, artwork, artist, title, subtitle, date, period, medium, nationality, note, tags);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                uploadButton.setText("Play");
                playmode = true;
            }


        }
    }
}
