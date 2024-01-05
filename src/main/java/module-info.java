module com.example.flashcardproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.flashcardproject to javafx.fxml;
    exports com.example.flashcardproject;
}