package com.example.flashcardproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.*;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class FrontscreenController {
    @FXML
    private Pane pane;

    @FXML
    private Button uploadButton;

    @FXML
    private Button newCardButton;
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
        System.out.println(playmode);
        if (playmode){
            if (!fdi.somethingInCardStatusTable()){
                fdi.cardStatusInit();

                int total = fdi.countAnswers();

                for (int i = 1; i <= total; i++ ){
                    Random random = new Random();
                    int randomIndex = random.nextInt(total - i + 1);
                    System.out.println(randomIndex);
                    fdi.schuffleCards(randomIndex, i);
                }
            }

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
                    System.out.println(file.getPath());

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

    @FXML
    void newCardButtonClicked(ActionEvent event) throws IOException {
        Flashcard f = new Flashcard(generateUniqueID(), "Art", "Great Works of Art::Artists","", "", "", "", "", "", "", "", "", "");

        String categoryText = "Art";

        Dialog<ButtonType> dialogvindue = new Dialog();
        dialogvindue.setTitle("Add new card");
        dialogvindue.setHeaderText("Add new card");
        //dialogvindue.getDialogPane().getStyleClass().add("Dialog");
        //dialogvindue.getDialogPane().getStylesheets().add(getClass().getResource("/MyTunesCSS.css").toExternalForm());
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialogvindue.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        TextField cardID = new TextField();
        cardID.setEditable(false);
        TextField category = new TextField(categoryText);
        category.setEditable(false);
        TextField question = new TextField();
        question.setEditable(false);
        TextField artwork = new TextField();
        artwork.setPromptText("Chooose a file");
        Button chooseFileButton = new Button();
        chooseFileButton.setText("Choose file");
        TextField artist = new TextField();
        artist.setPromptText("Artist");
        TextField title = new TextField();
        title.setPromptText("Title");
        TextField subtitle = new TextField();
        subtitle.setPromptText("Subtitle");
        TextField date = new TextField();
        date.setPromptText("Date");
        TextField period = new TextField();
        period.setPromptText("Period");
        TextField medium = new TextField();
        medium.setPromptText("Medium");
        TextField nationality = new TextField();
        nationality.setPromptText("Nationality");
        TextField note = new TextField();
        note.setPromptText("Note");
        TextField tags = new TextField();
        tags.setPromptText("Tags");

        HBox hbox = new HBox(artwork, chooseFileButton);
        VBox box = new VBox(cardID, category, question, hbox, artist, title, subtitle, date, period, medium, nationality, note, tags);
        box.setPrefHeight(500);
        box.setPrefWidth(300);
        dialogvindue.getDialogPane().setContent(box);

        chooseFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");

            // display files in the folder from which the app was launched
            fileChooser.setInitialDirectory(new File("."));

            // display the FileChooser
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
            if (file != null) { // Check if a file was selected
                String fileNameDB = "" + (char)34 + (char)34 + file.getName() + (char)34 + (char)34;
                String fileName = file.getName() ;

                // Set the text of the artwork TextField with the file name
                artwork.setText(fileNameDB);

                // Specify the destination directory (Images folder under resources)
                java.nio.file.Path sourcePath = java.nio.file.Paths.get(file.getPath());
                java.nio.file.Path destinationDirectory = java.nio.file.Paths.get("src/main/resources/com/example/flashcardproject/Images");
                java.nio.file.Path destinationPath = destinationDirectory.resolve(fileName); // Use the original file name

                try {
                    java.nio.file.Files.createDirectories(destinationDirectory);  // Lag målkatalogen hvis den ikke eksisterer
                    java.nio.file.Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Behandle unntaket etter behov
                }
            }
        });

        cardID.setText(f.getCardID());
        category.setText(f.getCategory());
        question.setText(f.getQuestion());
        artwork.setText(f.getArtwork());
        artist.setText(f.getArtist());
        title.setText(f.getTitle());
        subtitle.setText(f.getSubtitle());
        date.setText(f.getDate());
        period.setText(f.getPeriod());
        medium.setText(f.getMedium());
        nationality.setText(f.getNationality());
        note.setText(f.getNote());
        tags.setText(f.getTags());

        Optional<ButtonType> button = dialogvindue.showAndWait();
        if (button.get() == addButton) {
            f.setArtwork(artwork.getText());
            f.setArtist(artist.getText());
            f.setTitle(title.getText());
            f.setSubtitle(subtitle.getText());
            f.setDate(date.getText());
            f.setPeriod(period.getText());
            f.setMedium(medium.getText());
            f.setNationality(nationality.getText());
            f.setNote(note.getText());
            f.setTags(tags.getText());

            fdi.addFlashcard(f);

        }
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
