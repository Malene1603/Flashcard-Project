package com.example.flashcardproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class FlashcardController {

    @FXML
    private HBox hbox;

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
    private Label endLabel;

    @FXML
    private Button finnishButton2;

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

    private Instant startTime;
    private Instant endTime;

    private FlashcardDaoimpl fdi = new FlashcardDaoimpl();



    public FlashcardController() {
    }

    public void initialize() throws SQLException, MalformedURLException {
        // Der sættes en starTime for det første kort
        startTime = Instant.now();

        // Kalder metode til at vise det næste kort
        showRandomFlashcard();

        // Den "Finnish" knap der dukker op på lærredet når man gennemfører gøres usynlig, og så man ikke kan trykke på den
        finnishButton2.setDisable(true);
        finnishButton2.setOpacity(0);

        // Opdaterer de forskellige labels ved at kalde de enkelte metoder inde fra flashcardDao
        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        cardsInSetLabel.setText("Card in set: " + fdi.countCards());
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());
        endLabel.setText("");

        // Alt nedenstående opretter og sætter baggrunde på knapperne, og svar knapperne gøres usynlige
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
        //Opretter en FlashcardManager, så metoder derfra bliver tilgængelige
        FlashcardManager flashcardManager = new FlashcardManager();

        // Gemmer det første flashcard der bliver vist i randomFlashcard
        Flashcard randomFlashcard = flashcardManager.showTheFisrstCard();

        // Hvis randomFlashcard ikke er null
        if (randomFlashcard != null) {
            // Teksten på spørgsmåls label bliver sat
            questionLabel.setText("Artist?");

            //Da alle billederne i filen er omring af html tags og "", skal disse "fjernes" for at billederne kan bruges
            String URL1 = randomFlashcard.getArtwork();

            // Gemmer positionen for de 2 steder der er ""
            Integer pos1 = URL1.indexOf("\"\"", 0);
            Integer pos2 = URL1.indexOf("\"\"", pos1 + 1);

            // Gemmer filename i en string, som er alt det der står i mellem de to positioner hvor der er ""
            String filename = URL1.substring(pos1 + 2, pos2);

            //Gemmer den endelige path i en string
            String path = "Images/" + filename;

            // Opretter et image, som kommer fra den path vi lige har gemt, og sætter det på det imageview der skal vise kunstværket
            Image image = new Image(String.valueOf(getClass().getResource(path)));
            questionImage.setImage(image);

            // Sætter teksten på alle svar labels, og gør dem usynlige
            artistLabel.setText(randomFlashcard.getArtist());
            artistLabel.setOpacity(0.0);
            titleLabel.setText(randomFlashcard.getTitle());
            titleLabel.setOpacity(0.0);
            dateLabel.setText(randomFlashcard.getDate());
            dateLabel.setOpacity(0.0);
            periodLabel.setText(randomFlashcard.getPeriod());
            periodLabel.setOpacity(0.0);


            // Ellers gøres alle labels tekster tomme, og der bliver skrevet at der ikke er nogen kort
        } else {
            // Handle the case where there are no flashcards
            questionLabel.setText("No flashcards available");
            questionImage.setImage(null);
            artistLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            periodLabel.setText("");
        }
    }

    // En eventlistener der lytter til når der bliver klikket på knappen "Show answer"
    public void showAnswerButtonClicked(ActionEvent actionEvent) {
        // Gør "Show answer" og "Irrelevant" knapperne usynlige
        showAnswerButton.setOpacity(0.0);
        irrelevantButton.setOpacity(0.0);
        hbox.setPrefHeight(0);
        hbox.setPrefWidth(0);

        // Gør alle svar labels synlige
        artistLabel.setOpacity(1.0);
        titleLabel.setOpacity(1.0);
        dateLabel.setOpacity(1.0);
        periodLabel.setOpacity(1.0);

        // Gør alle svar knapperne synlige igen
        correctButton.setOpacity(1.0);
        almostCorrectButton.setOpacity(1.0);
        partiallyCorrectButton.setOpacity(1.0);
        incorrectButton.setOpacity(1.0);
    }

    // Eventhandler på "Incorrect" knappen
    public void incorrectButtonClicked(ActionEvent actionEvent) throws SQLException, MalformedURLException {
        // Sætter en end time
        endTime = Instant.now();

        // Regner forskellen ud på startTime og endTime og gemmer det som den tid der er brugt på kortet
        Duration timeElapsed = Duration.between(startTime, endTime);

        //Opretter en FlashcardManager for at kunne få adgang til metoderne derfra, og kalder en metode for at kunne gemme oplysninger fra lige det flashcard
        FlashcardManager flashcardManager = new FlashcardManager();
        Flashcard thisFlashcard = flashcardManager.showTheFisrstCard();

        // Gemmer cardID og svaret i to strings
        String cardID = thisFlashcard.getCardID();
        String answer = "Incorrect";

        // Kalder sql metoden inde fra FlashcardDao, som opdaterer med svar og tid, og også laver beregningerne for hvornår det skal vises igen.
        fdi.updateAnswer(cardID, answer, timeElapsed.toSeconds());

        // Laver en ny startTime til det næste kort
        startTime = Instant.now();

        // Kalder metode til at vise det næste kort
        showRandomFlashcard();

        // Opdaterer de forskellige labels ved at kalde de enkelte metoder inde fra flashcardDao
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());
        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());

        // Gør alle svar knapperne usynlige igen
        correctButton.setOpacity(0);
        incorrectButton.setOpacity(0);
        almostCorrectButton.setOpacity(0);
        partiallyCorrectButton.setOpacity(0);

        // Gør "Show answer" og "Irrelevant" knapperne synlige igen
        hbox.setPrefWidth(800);
        hbox.setPrefHeight(100);
        showAnswerButton.setOpacity(1);
        irrelevantButton.setOpacity(1);

    }

    // Eventhandler på "Partially correct" knappen
    public void partiallyCorrectButtonClicked(ActionEvent actionEvent) throws MalformedURLException, SQLException {
        // Sætter en end time
        endTime = Instant.now();

        // Regner forskellen ud på startTime og endTime og gemmer det som den tid der er brugt på kortet
        Duration timeElapsed = Duration.between(startTime, endTime);

        //Opretter en FlashcardManager for at kunne få adgang til metoderne derfra, og kalder en metode for at kunne gemme oplysninger fra lige det flashcard
        FlashcardManager flashcardManager = new FlashcardManager();
        Flashcard thisFlashcard = flashcardManager.showTheFisrstCard();

        // Gemmer cardID og svaret i to strings
        String cardID = thisFlashcard.getCardID();
        String answer = "Partially correct";

        // Kalder sql metoden inde fra FlashcardDao, som opdaterer med svar og tid, og også laver beregningerne for hvornår det skal vises igen.
        fdi.updateAnswer(cardID, answer, timeElapsed.toSeconds());

        // Laver en ny startTime til det næste kort
        startTime = Instant.now();

        // Kalder metode til at vise det næste kort
        showRandomFlashcard();

        // Opdaterer de forskellige labels ved at kalde de enkelte metoder inde fra flashcardDao
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());
        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());

        // Gør alle svar knapperne usynlige igen
        correctButton.setOpacity(0);
        incorrectButton.setOpacity(0);
        almostCorrectButton.setOpacity(0);
        partiallyCorrectButton.setOpacity(0);

        // Gør "Show answer" og "Irrelevant" knapperne synlige igen
        hbox.setPrefWidth(800);
        hbox.setPrefHeight(100);
        showAnswerButton.setOpacity(1);
        irrelevantButton.setOpacity(1);
    }

    // Eventhandler på "Almost correct" knappen
    public void almostCorrectButtonClicked(ActionEvent actionEvent) throws MalformedURLException, SQLException {
        // Sætter en end time
        endTime = Instant.now();

        // Regner forskellen ud på startTime og endTime og gemmer det som den tid der er brugt på kortet
        Duration timeElapsed = Duration.between(startTime, endTime);

        //Opretter en FlashcardManager for at kunne få adgang til metoderne derfra, og kalder en metode for at kunne gemme oplysninger fra lige det flashcard
        FlashcardManager flashcardManager = new FlashcardManager();
        Flashcard thisFlashcard = flashcardManager.showTheFisrstCard();

        // Gemmer cardID og svaret i to strings
        String cardID = thisFlashcard.getCardID();
        String answer = "Almost correct";

        // Kalder sql metoden inde fra FlashcardDao, som opdaterer med svar og tid, og også laver beregningerne for hvornår det skal vises igen.
        fdi.updateAnswer(cardID, answer, timeElapsed.toSeconds());

        // Laver en ny startTime til det næste kort
        startTime = Instant.now();

        // Kalder metode til at vise det næste kort
        showRandomFlashcard();

        // Opdaterer de forskellige labels ved at kalde de enkelte metoder inde fra flashcardDao
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());
        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());

        // Gør alle svar knapperne usynlige igen
        correctButton.setOpacity(0);
        incorrectButton.setOpacity(0);
        almostCorrectButton.setOpacity(0);
        partiallyCorrectButton.setOpacity(0);

        // Gør "Show answer" og "Irrelevant" knapperne synlige igen
        hbox.setPrefWidth(800);
        hbox.setPrefHeight(100);
        showAnswerButton.setOpacity(1);
        irrelevantButton.setOpacity(1);
    }

    // Eventhandler på "Correct" knappen
    public void correctButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        // Sætter en end time
        endTime = Instant.now();

        // Regner forskellen ud på startTime og endTime og gemmer det som den tid der er brugt på kortet
        Duration timeElapsed = Duration.between(startTime, endTime);

        //Opretter en FlashcardManager for at kunne få adgang til metoderne derfra, og kalder en metode for at kunne gemme oplysninger fra lige det flashcard
        FlashcardManager flashcardManager = new FlashcardManager();
        Flashcard thisFlashcard = flashcardManager.showTheFisrstCard();

        // Gemmer cardID og svaret i to strings
        String cardID = thisFlashcard.getCardID();
        String answer = "Correct";

        // Kalder sql metoden inde fra FlashcardDao, som opdaterer med svar og tid
        fdi.updateAnswer(cardID, answer, timeElapsed.toSeconds());

        // Laver en ny startTime til det næste kort
        startTime = Instant.now();

        // Kalder metode til at vise det næste kort
        showRandomFlashcard();

        // Opdaterer de forskellige labels ved at kalde de enkelte metoder inde fra flashcardDao
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());
        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());

        // Tjekker om antallet af kort der er tilbage = 0, for at tjekke om træningen er gennemført
        if (fdi.countCardsLeft() == 0){

            // Ændrer teksten på den øverste label
            questionLabel.setText("Congratulations!");

            // Alt nedenstående er med til gøre alt der der ikke skal være der mere usynligt
            questionImage.setImage(null);
            cardsLeftLabel.setText("");
            cardsInSetLabel.setText("");
            artistLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            periodLabel.setText("");
            correctLabel.setOpacity(0);
            incorrectLabel.setOpacity(0);
            showAnswerButton.setDisable(true);
            irrelevantButton.setDisable(true);
            finnishButton.setDisable(true);
            restartButton.setDisable(true);
            pauseButton.setDisable(true);
            finnishButton.setOpacity(0);
            restartButton.setOpacity(0);
            pauseButton.setOpacity(0);
            partiallyCorrectLabel.setOpacity(0);
            almostCorrectLabel.setOpacity(0);
            showAnswerButton.setOpacity(0);
            irrelevantButton.setOpacity(0);
            hbox.setPrefHeight(0);
            hbox.setPrefWidth(0);
            hbox.setOpacity(0);

            // Sætter teksten på det ekstra label der er når man har gennemført
            endLabel.setText("you have completed the traning");

            // "Finnish" knappen på lærredet gøres synlig, og gøres så den kan trykkes på
            finnishButton2.setDisable(false);
            finnishButton2.setOpacity(1);

            //Oprætter baggrundsbillede på den "Finnish" knap der kommer frem på lærredet
            Background finnish2Background = new Background(new BackgroundImage(
                    new Image(getClass().getResource("Finnish2.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            ));

            //Sætter baggrundsbillede på den "Finnish" knap der kommer frem på lærredet
            finnishButton2.setBackground(finnish2Background);

            // Kalder en sql metoden inde fra flashcardDao, der truncater CardStatus tabellen
            fdi.cardStatusRestart();

        }

        // Gør alle svar knapperne usynlige igen
        correctButton.setOpacity(0);
        incorrectButton.setOpacity(0);
        almostCorrectButton.setOpacity(0);
        partiallyCorrectButton.setOpacity(0);

        // Gør "Show answer" og "Irrelevant" knapperne synlige igen
        hbox.setPrefWidth(800);
        hbox.setPrefHeight(100);
        showAnswerButton.setOpacity(1);
        irrelevantButton.setOpacity(1);
    }

    // Eventhandler på "Irrelevant" knappen
    public void irrelevantButtonClicked(ActionEvent event) throws MalformedURLException, SQLException {
        // Sætter en end time
        endTime = Instant.now();

        // Regner forskellen ud på startTime og endTime og gemmer det som den tid der er brugt på kortet
        Duration timeElapsed = Duration.between(startTime, endTime);

        //Opretter en FlashcardManager for at kunne få adgang til metoderne derfra, og kalder en metode for at kunne gemme oplysninger fra lige det flashcard
        FlashcardManager flashcardManager = new FlashcardManager();
        Flashcard thisFlashcard = flashcardManager.showTheFisrstCard();

        // Gemmer cardID og svaret i to strings
        String cardID = thisFlashcard.getCardID();
        String answer = "Irrelevant";

        // Kalder sql metoden inde fra FlashcardDao, som opdaterer med svar og tid
        fdi.updateAnswer(cardID, answer, timeElapsed.toSeconds());

        // Laver en ny startTime til det næste kort
        startTime = Instant.now();

        // Kalder metode til at vise det næste kort
        showRandomFlashcard();

        // Opdaterer de forskellige labels ved at kalde de enkelte metoder inde fra flashcardDao
        cardsLeftLabel.setText("Cards left: " + fdi.countCardsLeft());
        correctLabel.setText("Correct: " + fdi.countCorrect());
        incorrectLabel.setText("Incorrect: " + fdi.countIncorrect());
        partiallyCorrectLabel.setText("Partially correct: " + fdi.countPartiallyCorrect());
        almostCorrectLabel.setText("Almost correct: " + fdi.countAlmostCorrect());

        // Tjekker om antallet af kort der er tilbage = 0, for at tjekke om træningen er gennemført
        if (fdi.countCardsLeft() == 0){

            // Ændrer teksten på den øverste label
            questionLabel.setText("Congratulations!");

            // Alt nedenstående er med til gøre alt der der ikke skal være der mere usynligt
            questionImage.setImage(null);
            cardsLeftLabel.setText("");
            cardsInSetLabel.setText("");
            artistLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            periodLabel.setText("");
            correctLabel.setOpacity(0);
            incorrectLabel.setOpacity(0);
            showAnswerButton.setDisable(true);
            irrelevantButton.setDisable(true);
            finnishButton.setDisable(true);
            restartButton.setDisable(true);
            pauseButton.setDisable(true);
            finnishButton.setOpacity(0);
            restartButton.setOpacity(0);
            pauseButton.setOpacity(0);
            partiallyCorrectLabel.setOpacity(0);
            almostCorrectLabel.setOpacity(0);
            showAnswerButton.setOpacity(0);
            irrelevantButton.setOpacity(0);
            hbox.setPrefHeight(0);
            hbox.setPrefWidth(0);
            hbox.setOpacity(0);

            // Sætter teksten på det ekstra label der er når man har gennemført
            endLabel.setText("you have completed the traning");

            // "Finnish" knappen på lærredet gøres synlig, og gøres så den kan trykkes på
            finnishButton2.setDisable(false);
            finnishButton2.setOpacity(1);

            //Oprætter baggrundsbillede på den "Finnish" knap der kommer frem på lærredet
            Background finnish2Background = new Background(new BackgroundImage(
                    new Image(getClass().getResource("Finnish2.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            ));

            //Sætter baggrundsbillede på den "Finnish" knap der kommer frem på lærredet
            finnishButton2.setBackground(finnish2Background);

            // Kalder en sql metoden inde fra flashcardDao, der truncater CardStatus tabellen
            fdi.cardStatusRestart();

        }
    }

    //Eventhandler på "Pause knappen oppe i hjørnet
    public void pauseButtonClicked(ActionEvent actionEvent) {
        // Lukker programmet ned
        Stage stage = (Stage) pauseButton.getScene().getWindow();
        stage.close();
    }

    //Eventhandler på "Restart" knappen oppe i hjørnet
    public void restartButtonClicked(ActionEvent actionEvent) throws SQLException, IOException {
        // Kalder en sql metoden inde fra flashcardDao, der truncater CardStatus tabellen
        fdi.cardStatusRestart();

        // Skifter scenen tilbage startskærmen ved at kalde changeScene metoden
        Scene currentScene = restartButton.getScene();
        changeScene(currentScene);
    }

    // Eventhandler på "Finnish" knappen oppe i hjørnet
    public void FinnishButtonClicked(ActionEvent actionEvent) throws IOException {
        // Kalder en sql metoden inde fra flashcardDao, der truncater CardStatus tabellen
        fdi.cardStatusRestart();

        // Skifter scenen tilbage startskærmen ved at kalde changeScene metoden
        Scene currentScene = finnishButton.getScene();
        changeScene(currentScene);
    }

    // Eventhandler på "Finnish" knappen der kommer frem på lærredet når man har gennemført
    public void FinnishButton2Clicked(ActionEvent actionEvent) throws IOException {
        // Kalder en sql metoden inde fra flashcardDao, der truncater CardStatus tabellen
        fdi.cardStatusRestart();

        // Skifter scenen tilbage startskærmen ved at kalde changeScene metoden
        Scene currentScene = finnishButton.getScene();
        changeScene(currentScene);
    }

    // En metodee til at skifte tilbage til startskærmen
    void  changeScene(Scene scene) throws IOException {
        // Loader det andet fxml dokument
        FXMLLoader fxmlLoader = new FXMLLoader(FlashcardApplication.class.getResource("FrontScreen.fxml"));
        scene.setRoot(fxmlLoader.load());
    }
}
