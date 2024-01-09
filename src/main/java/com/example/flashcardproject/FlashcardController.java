package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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

    private FlashcardDao flashcardDao;

    public FlashcardController() {
    }

    public FlashcardController(FlashcardDao flashcardDao) {
        this.flashcardDao = flashcardDao;
    }
    public void initialize() throws SQLException {
        showRandomFlashcard();
    }


    @FXML
    void showRandomFlashcard() throws SQLException {
        FlashcardManager flashcardManager = new FlashcardManager();

        Flashcard randomFlashcard = flashcardManager.getRandomFlashcard();
        System.out.println(randomFlashcard.getArtist() + randomFlashcard.getTitle() + randomFlashcard.getSubtitle() + randomFlashcard.getDate() + randomFlashcard.getPeriod() + randomFlashcard.getMedium() + randomFlashcard.getNationality() +randomFlashcard.getNote() + randomFlashcard.getTags());

        if (randomFlashcard != null) {
            questionLabel.setText("Artist?");
            artistLabel.setText(randomFlashcard.getArtist());
            titleLabel.setText(randomFlashcard.getTitle());
            dateLabel.setText(randomFlashcard.getDate());
            periodLabel.setText(randomFlashcard.getPeriod());
        } else {
            // Handle the case where there are no flashcards
            questionLabel.setText("No flashcards available");
            artistLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            periodLabel.setText("");
        }
    }

}
