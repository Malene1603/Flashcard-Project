package com.example.flashcardproject;

import java.sql.SQLException;
import java.util.List;

// Et interface der indeholder alle de metoder der skal være inde i FlashcardDaoimpl classen
public interface FlashcardDao {
    void addFlashcards(String question, String answer, String category, String artwork, String artist, String title, String subtitle, String date, String period, String medium, String nationality, String note, String tags);

    void addFlashcard(Flashcard flashcard);

    List<Flashcard> nextCard() throws SQLException;

    boolean somethingInDatabase();

    boolean somethingInCardStatusTable();

    int countCards();

    int countAnswers();

    void schuffleCards(Integer randomIndex, Integer position);

    void updateAnswer(String cardID, String answer, Long timeElapsed);

    void newCardPosition(String text);

    int countCorrect();

    int countIncorrect();

    int countAlmostCorrect();

    int countPartiallyCorrect();

    int countCardsLeft();

    void cardStatusInit();

    void cardStatusRestart();
}
