package com.example.flashcardproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class FlashcardController {

    @FXML
    private Label artistLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label periodLabel;

    @FXML
    private ImageView questionImage;

    @FXML
    private Label questionLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button showAnswerButton;

    @FXML
    private Button correctButton;

    @FXML
    private Button almostCorrectButton;

    @FXML
    private Button partiallyCorrectButton;

    @FXML
    private Button incorrectButton;

    private FlashcardDao flashcardDao;

    public FlashcardController() {
    }

    public void initialize() throws SQLException, MalformedURLException {
        showRandomFlashcard();
    }


    @FXML
    void showRandomFlashcard() throws SQLException, MalformedURLException {
        FlashcardManager flashcardManager = new FlashcardManager();

        Flashcard randomFlashcard = flashcardManager.getRandomFlashcard();

        if (randomFlashcard != null) {
            questionLabel.setText("Artist?");
            String URL1 = randomFlashcard.getArtwork();
            Integer pos1 = URL1.indexOf("\"\"", 0);
            Integer pos2 = URL1.indexOf("\"\"", pos1 + 1);
            String filename = URL1.substring(pos1 + 2, pos2);
            String path = "Images/" + filename;
            System.out.println(URL1 + "     " +pos1 + "     " + pos2);
            System.out.println(filename);
            System.out.println(path);

            Image image = new Image(String.valueOf(getClass().getResource(path)));
            System.out.println(image);
            questionImage.setImage(image);

            artistLabel.setText(randomFlashcard.getArtist());
            artistLabel.setOpacity(0.0);

            titleLabel.setText(randomFlashcard.getTitle());
            titleLabel.setOpacity(0.0);

            dateLabel.setText(randomFlashcard.getDate());
            dateLabel.setOpacity(0.0);

            periodLabel.setText(randomFlashcard.getPeriod());
            periodLabel.setOpacity(0.0);

            correctButton.setOpacity(0.0);
            almostCorrectButton.setOpacity(0.0);
            partiallyCorrectButton.setOpacity(0.0);
            incorrectButton.setOpacity(0.0);
        } else {
            // Handle the case where there are no flashcards
            questionLabel.setText("No flashcards available");
            artistLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            periodLabel.setText("");
        }
    }

    public void showAnswerButtonClicked(ActionEvent actionEvent) {
        showAnswerButton.setOpacity(0.0);

        artistLabel.setOpacity(1.0);
        titleLabel.setOpacity(1.0);
        dateLabel.setOpacity(1.0);
        periodLabel.setOpacity(1.0);

        correctButton.setOpacity(1.0);
        almostCorrectButton.setOpacity(1.0);
        partiallyCorrectButton.setOpacity(1.0);
        incorrectButton.setOpacity(1.0);
    }

    public void incorrectButtonClicked(ActionEvent actionEvent) {
    }

    public void partiallyCorrectButtonClicked(ActionEvent actionEvent) {
    }

    public void almostCorrectButtonClicked(ActionEvent actionEvent) {
    }

    public void correctButtonClicked(ActionEvent actionEvent) {
    }
}
