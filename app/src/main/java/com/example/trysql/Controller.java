package com.example.trysql;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Controller {
    private Scene scene;
    private Parent root;
    private Stage stage;

    // Setter for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private PieChart pieChart;


    public void loggedValueZero(ActionEvent event) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM excel.linesproductivity_updated_csv Where LoggedValue = 0");

            String outPut = "";
            while (resultSet.next()) {
                outPut += "LogID: " + resultSet.getInt("LogID") + "\n"
                        + "LineID: " + resultSet.getString("LineID") + "\n"
                        + "LogTime: " + resultSet.getString("LogTime") + "\n"
                        + "LoggedValue: " + resultSet.getFloat("LoggedValue") + "\n"
                        + "CmdType: " + resultSet.getInt("CmdType") + "\n"
                        + "Description: " + resultSet.getString("Description") + "\n"
                        + "UnitType: " + resultSet.getString("UnitType") + "\n\n";

            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("zeroValue.fxml"));
            root = loader.load();
            ZeroValue sec_Class = loader.getController();
            sec_Class.displayValue(outPut);

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void maxLoggedValue(ActionEvent event) throws IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel", "root", "password");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM excel.linesproductivity_updated_csv WHERE LoggedValue = (select max(LoggedValue) from excel.linesproductivity_updated_csv)");

            String outPut = "";
            // Process the result set
            while (resultSet.next()) {
                outPut += "For the MAX LoggedValue: " + resultSet.getFloat("LoggedValue") + " :\nThe LogID is: " + resultSet.getInt("LogID") + ".\nThe LineID is: " + resultSet.getString("LineID") + ".\nThe LogTime is: " + resultSet.getString("LogTime");
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("maxLoggedValue.fxml"));
            root = loader.load();
            MaxLoggedValue maxLoggedValue = loader.getController();
            maxLoggedValue.displayValue(outPut);

            // Close resources
            resultSet.close();
            statement.close();
            connection.close();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void whichLogID(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("whichLogID.fxml"));
        root = loader.load();
        WhichLogID which_LogID = new WhichLogID();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void minLoggedValue(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel", "root", "password");

                // Create a statement and execute the query
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM excel.linesproductivity_updated_csv WHERE LoggedValue = (select min(LoggedValue) from excel.linesproductivity_updated_csv where LoggedValue !=0)");

                String outPut = "";
                // Process the result set
                while (resultSet.next()) {
                    outPut += resultSet.getString("LineID") + "\n";
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("minLoggedValue.fxml"));
                root = loader.load();
                MinLoggedValue min_Value = loader.getController();
                min_Value.displayValue(outPut);

                // Close resources
                resultSet.close();
                statement.close();
                connection.close();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void datePick(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("datePick.fxml"));
                Parent root = loader.load();
                DatePick datePick = loader.getController();
                datePick.setStage((Stage) ((Node) event.getSource()).getScene().getWindow()); // Set the stage for DatePick controller

                Stage stage = new Stage(); // Create a new stage
                stage.setScene(new Scene(root)); // Set the scene
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void multiple(ActionEvent event) {
        setStage(stage); // Set the stage

        Thread thread1 = new Thread(() -> {
            try {
                datePick(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                minLoggedValue(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
    }
}


