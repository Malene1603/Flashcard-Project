package com.example.flashcardproject;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class FlashcardManager {
    private FlashcardDao fdi = new FlashcardDaoimpl();

    public FlashcardManager() {
    }

    public List<Flashcard> getAllFlashcards() throws SQLException {
        return fdi.nextCard();
    }

    public Flashcard getRandomFlashcard() throws SQLException {
        List<Flashcard> allFlashcards = getAllFlashcards();

        if (allFlashcards.isEmpty()) {
            return null;
        }

        return allFlashcards.get(0);
    }
}
