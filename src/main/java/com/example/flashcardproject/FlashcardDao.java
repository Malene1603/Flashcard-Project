package com.example.flashcardproject;

public interface FlashcardDao {
    void addFlashcards(String question, String answer, String category, String artwork, String artist, String title, String subtitle, String date, String period, String medium, String nationality, String note, String tags);
}
