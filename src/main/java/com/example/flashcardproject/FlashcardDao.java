package com.example.flashcardproject;

import java.sql.SQLException;
import java.util.List;

public interface FlashcardDao {
    void addFlashcards(String question, String answer, String category, String artwork, String artist, String title, String subtitle, String date, String period, String medium, String nationality, String note, String tags);

    void addFlashcard(Flashcard flashcard);

    List<Flashcard> getAllFlashcards() throws SQLException;

    List<Flashcard> nextCard() throws SQLException;
}
