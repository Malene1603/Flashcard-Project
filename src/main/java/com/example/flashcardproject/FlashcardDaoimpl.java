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
    public void addFlashcard(Flashcard flashcard) {
        String sql = "INSERT INTO cards (CardID, Category, Question, Artwork, Artist, Title, Subtitle, Date, Period, Medium, Nationality, Note, Tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, flashcard.getCardID());
            statement.setString(2, flashcard.getCategory());
            statement.setString(3, flashcard.getQuestion());
            statement.setString(4, flashcard.getArtwork());
            statement.setString(5, flashcard.getArtist());
            statement.setString(6, flashcard.getTitle());
            statement.setString(7, flashcard.getSubtitle());
            statement.setString(8, flashcard.getDate());
            statement.setString(9, flashcard.getPeriod());
            statement.setString(10, flashcard.getMedium());
            statement.setString(11, flashcard.getNationality());
            statement.setString(12, flashcard.getNote());
            statement.setString(13, flashcard.getTags());
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
                flashcard.setPeriod(resultSet.getString("Period"));
                flashcard.setMedium(resultSet.getString("Medium"));
                flashcard.setNationality(resultSet.getString("Nationality"));
                flashcard.setNote(resultSet.getString("Note"));
                flashcard.setTags(resultSet.getString("Tags"));

                flashcards.add(flashcard);
            }
        }

        return flashcards;
    }

   @Override
    public List<Flashcard> nextCard() throws SQLException{
        List<Flashcard> flashcards = new ArrayList<>();


        String sql = "SELECT * FROM Cards WHERE CardID = (SELECT TOP(1) CardID FROM CardStatus WHERE Answer IS NULL OR Answer = 'Incorrect' OR Answer = 'Almost correct' OR Answer = 'Partially correct' ORDER BY Position)";

        try (PreparedStatement statement = con.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

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
                flashcard.setPeriod(resultSet.getString("Period"));
                flashcard.setMedium(resultSet.getString("Medium"));
                flashcard.setNationality(resultSet.getString("Nationality"));
                flashcard.setNote(resultSet.getString("Note"));
                flashcard.setTags(resultSet.getString("Tags"));

                flashcards.add(flashcard);
            }


        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
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

    public boolean somethingInCardStatusTable(){
        try {
            String sql = "SELECT COUNT(*) FROM CardStatus";

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

    public int countCards(){
        try {
            String sql = "SELECT COUNT(*) FROM cards";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public int countAnswers(){
        try {
            String sql = "SELECT COUNT(*) FROM CardStatus";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public void schuffleCards(Integer randomIndex, Integer position){
        try {
            String sql = "UPDATE CardStatus SET Position = "+ position +" WHERE CardID = (SELECT CardID FROM CardStatus WHERE Position IS NULL ORDER BY (SELECT NULL) OFFSET "+ randomIndex +" ROWS FETCH NEXT 1 ROW ONLY)";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
    }

    public void updateAnswer(String cardID, String answer){
        try {
            String sql = "UPDATE CardStatus SET Answer = '" + answer + "' WHERE CardID = '" + cardID + "'";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
    }

    public void cardNeverShownAgain(String cardID){
        try {
            String sql = "UPDATE CardStatus SET Position = 700  WHERE CardID = '" + cardID + "'";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
    }

    public int countCorrect(){
        try {
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Correct'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public int countIncorrect(){
        try {
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Incorrect'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public int countAlmostCorrect(){
        try {
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Almost correct'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public int countPartiallyCorrect(){
        try {
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Partially correct'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public int countCardsLeft() {
        try {
            String sql = "SELECT COUNT(*) FROM CardStatus WHERE Answer = 'Incorrect' OR Answer = 'Almost correct' OR Answer = 'Partially correct' OR Answer IS NULL";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // Assuming there is an integer column named "count" in the result set
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    public void cardStatusInit(){
        try{
            String sql ="TRUNCATE TABLE CardStatus INSERT INTO CardStatus (CardID) SELECT CardID FROM Cards";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                statement.executeUpdate();

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void cardStatusRestart(){
        try{
            String sql ="TRUNCATE TABLE CardStatus";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                statement.executeUpdate();

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
