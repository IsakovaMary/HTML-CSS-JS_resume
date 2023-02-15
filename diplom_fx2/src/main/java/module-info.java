module com.example.diplom_fx2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.diplom_fx2 to javafx.fxml;
    exports com.example.diplom_fx2;
}