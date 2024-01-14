package com.example.flashcardproject;

import java.sql.SQLException;
import java.util.List;

public class FlashcardManager {

    // FlashcardDaoimpl bruges til at interagere med databasen.
    private FlashcardDao fdi = new FlashcardDaoimpl();

    // Henter alle flashcards fra databasen og returnerer dem som en liste.
    public List<Flashcard> getNextCard() throws SQLException {
        return fdi.nextCard();
    }

    // Henter og returnerer det første flashcard i tabellen.
    // Returnerer null, hvis der ikke er nogen flashcards i tabellen.
    public Flashcard showTheFisrstCard() throws SQLException {
        List<Flashcard> firstFlashcard = getNextCard();

        if (firstFlashcard.isEmpty()) {
            return null;
        }

        return firstFlashcard.get(0);
    }
}
