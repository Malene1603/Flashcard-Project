package com.example.flashcardproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlashcardDaoimpl implements FlashcardDao {
    private  Connection con;

    // Skaber forbindelsen til databasen
    public FlashcardDaoimpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Malene_Flashcards;userName=CSe2023t_t_4;password=CSe2023tT4#23;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }

    // Tilføjer et nyt flashcard til "cards"-tabellen i databasen med de angivne oplysninger.
    @Override
    public void addFlashcards(String cardID, String category, String question, String artwork, String artist, String title, String subtitle, String date, String period, String medium, String nationality, String note, String tags) {
        // SQL-query for at indsætte et nyt kort i "cards"-tabellen
        String sql = "INSERT INTO cards (CardID, Category, Question, Artwork, Artist, Title, Subtitle, Date, Period, Medium, Nationality, Note, Tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            // Indsætter værdierne
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
            // Udfører SQL-queryet
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tilføjer et nyt flashcard til "cards"-tabellen i databasen med informationerne fra det givne Flashcard-objekt.
    @Override
    public void addFlashcard(Flashcard flashcard) {
        // SQL-query for at indsætte et nyt kort i "cards"-tabellen
        String sql = "INSERT INTO cards (CardID, Category, Question, Artwork, Artist, Title, Subtitle, Date, Period, Medium, Nationality, Note, Tags) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            // Indsætter værdierne
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
            // Udfører SQL-queryet
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    // Henter den næste tilgængelige flashcard fra databasen baseret på kortets status og position.
   @Override
    public List<Flashcard> nextCard() throws SQLException{
       // Opretter en liste til at gemme de hentede flashcards
        List<Flashcard> flashcards = new ArrayList<>();

       // SQL-query for at hente det næste kort baseret på dets status og position
        String sql = "SELECT * FROM Cards WHERE CardID = (SELECT TOP(1) CardID FROM CardStatus WHERE Answer IS NULL OR Answer = 'Incorrect' OR Answer = 'Almost correct' OR Answer = 'Partially correct' ORDER BY Position)";

        try (PreparedStatement statement = con.prepareStatement(sql)){
            // Udfører SQL-queryet
            ResultSet resultSet = statement.executeQuery();

            // Itererer gennem ResultSet for at oprette Flashcard-objekter og tilføje dem til listen
            while (resultSet.next()) {
                // Sætter attributterne for hvert Flashcard-objekt baseret på resultaterne fra databasen
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

                // Tilføjer det oprettede Flashcard-objekt til listen
                flashcards.add(flashcard);
            }


        } catch (SQLException e) {
        }
       // Returnerer listen af hentede flashcards
        return flashcards;
    }

    // Undersøger, om der er mindst ét element i databasetabellen Cards.
    public boolean somethingInDatabase(){
        try {
            // SQL-forespørgsel for at tælle antallet af rækker i databasetabellen
            String sql = "SELECT COUNT(*) FROM cards";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter værdien fra den første kolonne (indeks 1) i resultatet
                    int count = resultSet.getInt(1);
                    // Returnerer sandt, hvis antallet er større end 0, ellers falsk
                    return count > 0;
                }
            }
        } catch (SQLException e) {
        }
        return false;
    }

    // Undersøger, om der er mindst ét element i databasetabellen CardStatus.
    public boolean somethingInCardStatusTable(){
        try {
            // SQL-forespørgsel for at tælle antallet af rækker i databasetabellen
            String sql = "SELECT COUNT(*) FROM CardStatus";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter værdien fra den første kolonne (indeks 1) i resultatet
                    int count = resultSet.getInt(1);
                    // Returnerer sandt, hvis antallet er større end 0, ellers falsk
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return false;
    }

    // Tæller antal kort der er i database tabellen Cards.
    public int countCards(){
        try {
            // SQL-query til at tælle antallet af kort
            String sql = "SELECT COUNT(*) FROM cards";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter værdien fra den første kolonne (indeks 1) i resultatet
                    int count = resultSet.getInt(1);
                    // Returnerer antallet af rækker
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    // Tæller antal kort der er i database tabellen CardStatus.
    public int countAnswers(){
        try {
            // SQL-query til at tælle antallet af kort
            String sql = "SELECT COUNT(*) FROM CardStatus";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter værdien fra den første kolonne (indeks 1) i resultatet
                    int count = resultSet.getInt(1);
                    // Returnerer antallet af rækker
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your application's error handling strategy
        }
        return 0;
    }

    // Blander kortene ved at opdatere positionen for et kort baseret på et tilfældigt indeks.
    public void schuffleCards(Integer randomIndex, Integer position){
        try {
            // SQL-forespørgsel for at opdatere positionen for et kort baseret på et tilfældigt indeks
            String sql = "UPDATE CardStatus SET Position = "+ position +" WHERE CardID = (SELECT CardID FROM CardStatus WHERE Position IS NULL ORDER BY (SELECT NULL) OFFSET "+ randomIndex +" ROWS FETCH NEXT 1 ROW ONLY)";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                // Udfører SQL-queryet
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opdaterer svaret og tidsforbruget for et kort i CardStatus-tabellen baseret på kortets ID.
    // Det er hele algoritmen til at bestemme hvilket kort der skal være det næste.
    public void updateAnswer(String cardID, String answer, Long timeElapsed){
        try {
            // SQL-forespørgsel for at opdatere svaret og tidsforbruget for et kort i CardStatus-tabellen
            String sql = "UPDATE CardStatus SET Answer = '" + answer + "', TimeSpend = '" + timeElapsed + "' WHERE CardID = '" + cardID + "'";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                // Udfører SQL-queryet
                statement.executeUpdate();

            }
            // Hvis svaret ikke er 'Correct' eller 'Irrelevant'
            if (answer != "Correct" && answer != "Irrelevant") {
                // SQL-forespørgsel for at tælle antallet af kort og summen af tidsforbruget for kort, hvor tidsforbruget ikke er null
                String sql2 = "SELECT COUNT(*) AS Count, SUM(TimeSpend) AS SumTimeSpend FROM CardStatus WHERE TimeSpend IS NOT NULL";
                try (PreparedStatement statement = con.prepareStatement(sql2);
                     // Udfører SQL-queryet
                     ResultSet resultSet = statement.executeQuery()) {

                    // Opretter maxMin integer
                    Integer maxMin = 0;

                    // Sætter maxMin baseret på svaret
                    if (answer == "Almost correct") {
                        maxMin = 10;
                    } else if (answer == "Partially correct") {
                        maxMin = 5;
                    } else if (answer == "Incorrect") {
                        maxMin = 1;
                    }

                    // Hvis der er resultater i resultatsettet
                    if (resultSet.next()) {
                        // Definerer count og sumSpendTime som to integers
                        Integer count = resultSet.getInt(1);
                        Integer sumSpendTime = resultSet.getInt(2);

                        // Beregner en maksimal tilfældig værdi baseret på tidsforbruget og antallet af kort
                        int maxRandom = (int) (maxMin.floatValue() * 60 / (sumSpendTime.floatValue() / count.floatValue()));

                        // Opretter et random objekt
                        Random random = new Random();

                        // Genererer en tilfældig værdi mellem 1 og maxRandom
                        int randomIndex = random.nextInt(maxRandom) + 1;

                        // SQL-forespørgsel for at opdatere kortets position i CardStatus-tabellen baseret på det genererede tilfældige indeks
                        String sql3 = "UPDATE CardStatus SET Position = POSITION + '" + randomIndex + "' WHERE CardID = '" + cardID + "'";

                        try (PreparedStatement statement2 = con.prepareStatement(sql3)){
                            // Udfører SQL-queryet
                            statement2.executeUpdate();

                        }

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opdaterer positionen for et nyt kort i CardStatus-tabellen baseret på kortets CardID.
    // Positionen sættes til at være lig med antallet af kort i Cards-tabellen.
    public void newCardPosition(String cardID){
        try {
            // SQL-query til at indsætte CardID i CardStatus-tabellen og opdatere positionen så den får den sidste position
            String sql = "INSERT INTO CardStatus (CardID) SELECT CardID FROM Cards WHERE CardID = '" + cardID + "'UPDATE CardStatus SET position = (SELECT COUNT(*) FROM cards) WHERE cardID = '"+ cardID +"';";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                // Udfører SQL-queryet
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tæller antallet af kort, der er markeret som "Correct".
    public int countCorrect(){
        try {
            // SQL-query til at tælle antallet af kort baseret på bestemte svarstatusser
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Correct'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter tællingen fra resultatet og returnerer den
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tæller antallet af kort, der er markeret som "Incorrect".
    public int countIncorrect(){
        try {
            // SQL-query til at tælle antallet af kort baseret på bestemte svarstatusser
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Incorrect'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter tællingen fra resultatet og returnerer den
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tæller antallet af kort, der er markeret som "Almost correct".
    public int countAlmostCorrect(){
        try {
            // SQL-query til at tælle antallet af kort baseret på bestemte svarstatusser
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Almost correct'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter tællingen fra resultatet og returnerer den
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tæller antallet af kort, der er markeret som "Partially correct".
    public int countPartiallyCorrect(){
        try {
            // SQL-query til at tælle antallet af kort baseret på bestemte svarstatusser
            String sql = "SELECT COUNT(Answer) FROM CardStatus WHERE Answer = 'Partially correct'";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter tællingen fra resultatet og returnerer den
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Tæller antallet af kort tilbage, der enten er markeret som "Incorrect", "Almost correct", Partially correct" eller hvor svaret endnu ikke er indtastet.
    public int countCardsLeft() {
        try {
            // SQL-query til at tælle antallet af kort tilbage baseret på bestemte svarstatusser
            String sql = "SELECT COUNT(*) FROM CardStatus WHERE Answer = 'Incorrect' OR Answer = 'Almost correct' OR Answer = 'Partially correct' OR Answer IS NULL";

            try (PreparedStatement statement = con.prepareStatement(sql);
                 // Udfører SQL-queryet
                 ResultSet resultSet = statement.executeQuery()) {

                // Tjekker om resultatet indeholder rækker (data)
                if (resultSet.next()) {
                    // Henter tællingen fra resultatet og returnerer den
                    int count = resultSet.getInt(1);
                    return count;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Initialiserer CardStatus-databasetabellen ved først at nulstille indholdet og derefter indsætte CardID fra Cards-tabellen.
    public void cardStatusInit(){
        try{
            // SQL-query til at nulstille indholdet af CardStatus-tabellen og indsætte CardID fra Cards-tabellen i CardStatus-tabellen
            String sql ="TRUNCATE TABLE CardStatus INSERT INTO CardStatus (CardID) SELECT CardID FROM Cards";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                // Udfører SQL-queryet
                statement.executeUpdate();

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Nulstiller indholdet af CardStatus-databasetabellen.
    public void cardStatusRestart(){
        try{
            // SQL-query til at slette alle rækker fra CardStatus-tabellen
            String sql ="TRUNCATE TABLE CardStatus";

            try (PreparedStatement statement = con.prepareStatement(sql)){
                // Udfører SQL-queryet
                statement.executeUpdate();

            }

        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
