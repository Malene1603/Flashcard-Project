package com.example.flashcardproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class FrontscreenController {

    //Atributter
    @FXML
    private Pane pane;

    @FXML
    private Button uploadButton;

    @FXML
    private Button newCardButton;
    private FlashcardDaoimpl fdi = new FlashcardDaoimpl();

    // Boolean til at
    private Boolean playmode;

    // Metode kaldt under initialisering af controlleren
    public void initialize(){

        // Hvis der er noget i databasen betyder det at filen er importeret og derfor kan man begynde træningen
        if(fdi.somethingInDatabase()){
            Background startTrainingBackground = new Background(new BackgroundImage(
                    new Image(String.valueOf(getClass().getResource("StartTraning.png"))),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            ));
            uploadButton.setBackground(startTrainingBackground);
            playmode = true;

            // Ellers er filen ikke importeret og dette skal gøres inden man kan begynde at træne
        } else{
            Background importCardsBackground = new Background(new BackgroundImage(
                    new Image(String.valueOf(getClass().getResource("ImportCards.png"))),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            ));
            uploadButton.setBackground(importCardsBackground);
            playmode = false;
        }

        Background addNewCardBackground = new Background(new BackgroundImage(
                new Image(String.valueOf(getClass().getResource("AddNewCard.png"))),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        newCardButton.setBackground(addNewCardBackground);
    }

    // Metode til at skifte scenen
    void  changeScene(Scene scene) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FlashcardApplication.class.getResource("FlashcardScreen.fxml"));
        scene.setRoot(fxmlLoader.load());
    }

    @FXML
    void uploadButtonClicked(ActionEvent event) throws IOException {

        // Hvis playmode er true
        if (playmode){

            // Og hvis der ikke er noget i CardStatus tabellen i databasen
            if (!fdi.somethingInCardStatusTable()){

                // En metode der flyttet alle CardID fra Cards tabellen over i CardID i Cardstatus tabellen i databasen
                fdi.cardStatusInit();

                // Opretter en int som er antallet af kort i CardStatus tabellen i databasen
                int total = fdi.countAnswers();

                // For-loopet itererer fra 1 til 'total' (inklusiv)
                for (int i = 1; i <= total; i++ ){

                    // Opret en ny Random-objekt for at generere tilfældige tal
                    Random random = new Random();

                    // Generer et tilfældigt indeks mellem 0 (inklusiv) og 'total - i + 1' (eksklusiv)
                    int randomIndex = random.nextInt(total - i + 1);

                    // En metode der blander alle kortene inde i databasen ud fra randomindex og i
                    fdi.schuffleCards(randomIndex, i);
                }
            }

            // Skifter scenen
            Scene currentScene = uploadButton.getScene();
            changeScene(currentScene);

        // hvis playmode ikke er true kører den else
        } else {

            //Opretter en string til art filens path
            String filePath = "src/main/resources/com/example/flashcardproject/Great Works of Art__Artists.txt";

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                    // Læs filen linje for linje
                    while ((line = reader.readLine()) != null) {

                        // Undlad linjer, der starter med "#"
                        if (!line.trim().startsWith("#")) {

                            // Del linjen ved tabulatorer
                            String[] values = line.split("\t");

                            // Tjek om der er mindst 3 værdier i linjen, dat det har de allesammen
                            if (values.length >= 3) {
                                String cardID = values[0];
                                String category = values[1];
                                String question = values[2];
                                String artwork = values[3];
                                String artist = values[4];
                                String title = values[5];

                                // Tjekker om der er flere værdier og tildel dem hvis det er tilfældet
                                // Hvis længden af arrayet 'values' er større end eller lig med 7, tildel værdien på indeks 6 til 'subtitle', ellers tildel en tom streng ("").
                                // Da ikke alle kort havde noget i alle 13 kolonner var dette nødvendigt
                                String subtitle = values.length >= 7 ? values[6] : "";
                                String date = values.length >= 8 ? values[7] : "";
                                String period = values.length >= 9 ? values[8] : "";
                                String medium = values.length >= 10 ? values[9] : "";
                                String nationality = values.length >= 11 ? values[10] : "";
                                String note = values.length >= 12 ? values[11] : "";
                                String tags = values.length >= 13 ? values[12] : "";

                                // Tilføjer kortet til databasen
                                fdi.addFlashcards(cardID, category, question, artwork, artist, title, subtitle, date, period, medium, nationality, note, tags);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                // Opdater knappens baggrund og sætter playmode til true
                Background startTrainingBackground = new Background(new BackgroundImage(
                        new Image(String.valueOf(getClass().getResource("StartTraning.png"))),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT
                ));
                uploadButton.setBackground(startTrainingBackground);
                playmode = true;
            }

        }

    // Eventhandler på new card button der åbner et dialogvindue når der trykkes
    @FXML
    void newCardButtonClicked(ActionEvent event) throws IOException {
        NewCardDialog newCardDialog = new NewCardDialog(pane);
    }
}
