package com.example.flashcardproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlashcardDaoimpl implements FlashcardDao {
    private  Connection con;

    public FlashcardDaoimpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Malene_Flashcards;userName=CSe2023t_t_4;password=CSe2023tT4#23;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }

    @Override
    public void addFlashcards(String cardID, String category, String question, String artwork, String artist, String title, String subtitle, String date, String period, String medium, String nationality, String note, String tags) {
        String sql = "INSERT INTO cards (CardID, Category, Question, Artwork, Artist, Title, Subtitle, Date, Period, Medium, Nationality, Note, Tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, cardID);
            statement.setString(2, category);
            statement.setString(3, question);
            statement.setString(4, artwork);
            statement.setString(5, artist);
            statement.setString(6, title);
            statement.setString(7, subtitle);
            statement.setString(8, date);
            statement.setString(9, period);
            statement.setString(10, medium);
            statement.setString(11, nationality);
            statement.setString(12, note);
            statement.setString(13, tags);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's error handling strategy
        }
    }

    @Override
    public List<Flashcard> getAllFlashcards() throws SQLException {
        List<Flashcard> flashcards = new ArrayList<>();

        String sql = "SELECT * FROM cards";

        try (PreparedStatement statement = con.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Flashcard flashcard = new Flashcard("", "", "", "", "", "", "", "", "", "", "", "", "");
                flashcard.setCardID(resultSet.getString("CardID"));
                flashcard.setCategory(resultSet.getString("Category"));
                flashcard.setQuestion(resultSet.getString("Question"));
                flashcard.setArtwork(resultSet.getString("Artwork"));
                flashcard.setArtist(resultSet.getString("Artist"));
                flashcard.setTitle(resultSet.getString("Title"));
                flashcard.setSubtitle(resultSet.getString("Subtitle"));
                flashcard.setDate(resultSet.getString("Date"));
                flashcard.setPeriod(resultSet.getString("period"));
                flashcard.setMedium(resultSet.getString("medium"));
                flashcard.setNote(resultSet.getString("Note"));
                flashcard.setTags(resultSet.getString("Tags"));

                flashcards.add(flashcard);
            }
        }

        return flashcards;
    }

    public boolean somethingInDatabase(){
        try {
            String sql = "SELECT COUNT(*) FROM cards";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return false;
    }
}
