package com.example.flashcardproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class FlashcardController {

    @FXML
    private Label cardsInSetLabel;

    @FXML
    private Label cardsLeftLabel;

    @FXML
    private Label almostCorrectLabel;

    @FXML
    private Label correctLabel;

    @FXML
    private Label incorrectLabel;

    @FXML
    private Label partiallyCorrectLabel;

    @FXML
    private Button finnishButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button restartButton;

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

    @FXML
    private Button irrelevantButton;

    private FlashcardDaoimpl fdi = new FlashcardDaoimpl();



    public FlashcardController() {
    }

    public void initialize() throws SQLException, MalformedURLException {
        showRandomFlashcard();

        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        cardsInSetLabel.setText("Card in set: " + fdi.countCards());
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());

        Background correctBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Correct.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        correctButton.setOpacity(0.0);
        correctButton.setBackground(correctBackground);

        Background AlmostCorrectBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("AlmostCorrect.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        almostCorrectButton.setOpacity(0.0);
        almostCorrectButton.setBackground(AlmostCorrectBackground);

        Background partiallyCorrectBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("PartiallyCorrect.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        partiallyCorrectButton.setOpacity(0.0);
        partiallyCorrectButton.setBackground(partiallyCorrectBackground);

        Background incorrectBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Incorrect.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        incorrectButton.setOpacity(0.0);
        incorrectButton.setBackground(incorrectBackground);

        Background irrelevantBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Irrelevant.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        irrelevantButton.setBackground(irrelevantBackground);

        Background pauseBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Pause.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        pauseButton.setBackground(pauseBackground);

        Background finnishBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Finnish.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        finnishButton.setBackground(finnishBackground);

        Background restartBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Restart.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        restartButton.setBackground(restartBackground);

        Background showAnswerBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("ShowAnswer.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        showAnswerButton.setBackground(showAnswerBackground);
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
        irrelevantButton.setOpacity(0.0);

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

    public void irrelevantButtonClicked(ActionEvent event) {

    }

    public void pauseButtonClicked(ActionEvent actionEvent) {
    }

    public void restartButtonClicked(ActionEvent actionEvent) {
    }

    public void FinnishButtonClicked(ActionEvent actionEvent) {
    }
}
