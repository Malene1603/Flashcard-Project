package com.example.flashcardproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FlashcardDaoimpl {
    private  Connection con;

    public FlashcardDaoimpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Malene_Flashcards;userName=CSe2023t_t_4;password=CSe2023tT4#23;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }

    public void addFlashcards(String CardID, String Category, String Question) throws SQLException {
        String sql = "INSERT INTO cards (CardID, Category, Question) VALUES (?, ?, ?)";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, CardID);
            statement.setString(2, Category);
            statement.setString(3, Question);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception according to your application's error handling strategy
        }
    }
}
