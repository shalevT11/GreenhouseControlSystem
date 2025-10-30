module com.example.trysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.trysql to javafx.fxml;
    exports com.example.trysql;
}