package com.example.flashcardproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FlashcardDaoimpl {
    private Connection con;

    public FlashcardDaoimpl() {
        try {
            con = DriverManager.getConnection("jdbc:sqlserver://10.176.111.34:1433;database=Malene_Flashcards;userName=CSe2023t_t_4;password=CSe2023tT4#23;encrypt=true;trustServerCertificate=true");
        } catch (SQLException e) {
            System.out.println("Cant connect to Database" + e);
        }
    }
}
