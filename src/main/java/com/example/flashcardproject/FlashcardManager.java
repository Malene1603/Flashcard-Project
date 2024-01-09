package com.example.flashcardproject;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class FlashcardManager {
    private FlashcardDao fdi = new FlashcardDaoimpl();

    public FlashcardManager() {
    }

    public List<Flashcard> getAllFlashcards() throws SQLException {
        return fdi.getAllFlashcards();
    }

    public Flashcard getRandomFlashcard() throws SQLException {
        List<Flashcard> allFlashcards = getAllFlashcards();

        if (allFlashcards.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allFlashcards.size());

        return allFlashcards.get(randomIndex);
    }
}
