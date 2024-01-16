package com.example.flashcardproject;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

public class NewCardDialog {

    //Atributter
    private Pane pane;
    private FlashcardDao fdi = new FlashcardDaoimpl();

    // Konstruktør, der modtager en reference til en Pane
    public NewCardDialog(Pane pane) {
        this.pane = pane;

        // Opretter et nyt flashcard med et unikt ID og standardværdier
        Flashcard f = new Flashcard(generateUniqueID(), "Art", "Great Works of Art::Artists","", "", "", "", "", "", "", "", "", "");

        // Standard kategori
        String categoryText = "Art";

        // Opret en dialogboks
        Dialog<ButtonType> dialogvindue = new Dialog();
        dialogvindue.setTitle("Add new card");
        dialogvindue.setHeaderText("Add new card");

        // Tilføjer style class og adgang til css dokumentet
        dialogvindue.getDialogPane().getStyleClass().add("Dialog");
        dialogvindue.getDialogPane().getStylesheets().add(getClass().getResource("/FlashcardCSS.css").toExternalForm());

        // Tilføj knapper til dialogboksen
        ButtonType addButton = new ButtonType("", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = ButtonType.CANCEL;
        dialogvindue.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
        Button addBtn = (Button) dialogvindue.getDialogPane().lookupButton(addButton);
        Button cancelBtn = (Button) dialogvindue.getDialogPane().lookupButton(cancelButton);
        addBtn.getStyleClass().add("dialogButtons");
        cancelBtn.getStyleClass().add("dialogButtons");
        cancelBtn.setText("");

        //Opretter alle tekstfelter, sætter prompttekster, og de første fire sættes til ikke at skulle kunne redigeres.
        TextField cardID = new TextField();
        cardID.setEditable(false);
        cardID.getStyleClass().add("text-field");
        TextField category = new TextField(categoryText);
        category.setEditable(false);
        category.getStyleClass().add("text-field");
        TextField question = new TextField();
        question.setEditable(false);
        TextField artwork = new TextField();
        artwork.setPromptText("Chooose a file");
        artwork.setEditable(false);
        Button chooseFileButton = new Button();
        chooseFileButton.getStyleClass().add("dialogButtons");
        chooseFileButton.setText("");
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

        // Laver en ny hbox artwork tekstfeltet og choose file button ind i den så de vil blive vist ved siden af hinanden
        HBox hbox = new HBox(artwork, chooseFileButton);
        hbox.getStyleClass().add("hbox");
        // Laver en vbox og tilføjer alle tekstfelter og hboxen i den rækkefølge de skal vises
        VBox box = new VBox(cardID, category, question, hbox, artist, title, subtitle, date, period, medium, nationality, note, tags);

        // Sætter spacing mellem elementerne
        box.setSpacing(10);
        hbox.setSpacing(70);

        // Sætter højde og bredde på dialogboksen
        box.setPrefHeight(800);
        box.setPrefWidth(500);

        //Sætter baggrundsbilleder på knapperne
        Background addBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Add.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        addBtn.setBackground(addBackground);

        Background cancelBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("Cancel.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        cancelBtn.setBackground(cancelBackground);

        Background chooseFileBackground = new Background(new BackgroundImage(
                new Image(getClass().getResource("ChooseFile.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        ));
        chooseFileButton.setBackground(chooseFileBackground);

        // Indsæt layout i dialogboksen
        dialogvindue.getDialogPane().setContent(box);

        // Eventhandler på knappen til at vælge en fil med, som både åbner en filechooser, og flytter den valgte fil til Images mappen under ressources
        chooseFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select File");

            // Viser filechooseren
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

            // Tjekker om der er valgt en fil
            if (file != null) {
                String fileNameDB = "" + (char)34 + (char)34 + file.getName() + (char)34 + (char)34;
                String fileName = file.getName() ;

                // Sætter tekstfeltet for billedet (artwork) til filnavnet
                artwork.setText(fileNameDB);

                // Angiv destinationsmappen (Images-mappen under resources)
                java.nio.file.Path sourcePath = java.nio.file.Paths.get(file.getPath());
                java.nio.file.Path destinationDirectory = java.nio.file.Paths.get("src/main/resources/com/example/flashcardproject/Images");
                java.nio.file.Path destinationPath = destinationDirectory.resolve(fileName); // Use the original file name

                try {
                    // Kopier filen til destinationen
                    java.nio.file.Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Udfyld tekstfelter med standardværdier fra flashcard
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

        // Vis dialogboksen og vent på brugeren
        Optional<ButtonType> button = dialogvindue.showAndWait();
        if (button.get() == addButton) {

            // Opdaterer flashcard med de værdier der er indtastet i tekstfelterne
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

            // Tilføj flashcard til databasen
            fdi.addFlashcard(f);

            // Tilføjer til CardStatus tabellen og opdaterer kortets position
            fdi.newCardPosition(cardID.getText());

        }
    }

    // Metode til at generere et unikt ID
    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
