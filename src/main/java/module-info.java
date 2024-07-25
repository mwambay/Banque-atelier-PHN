module org.example.bankapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.bankapp to javafx.fxml;
    exports org.example.bankapp;
    exports org.example.bankapp.banques;
    opens org.example.bankapp.banques to javafx.fxml;
}