package com.example.trysql;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Image icon = new Image("Kinneret.png"); //להשים תמונה שתהיה ולא לשכוח שהתמונה תהיה בתוך תקיית resources
            stage.getIcons().add(icon); //לא לשכוח הוספה כמובן
            stage.setTitle("מערכת בקרת ייצור במפעל לייצור יריעות פלסטיק לחממות");
            Parent root = FXMLLoader.load(getClass().getResource("firstFrame.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
