package com.example.trysql;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WhichLogID {
    @FXML
    private Label myLabel;

    @FXML
    private TextField myTextField;
    @FXML
    private Button myButton;
    private Parent root;
    private Scene scene;
    private Stage stage;
    int log_ID;
    public void submit(ActionEvent event){
        try{
            log_ID = Integer.parseInt(myTextField.getText());
            if (log_ID <= 6 && log_ID >= 0){
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    // Establish the database connection
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel", "root", "password");
                    float sum = 0; //יסכום את הערך אותו אנחנו מחפשים
                    // Create a statement and execute the query

                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM excel.linesproductivity_updated_csv");


                    // Process the result set
                    while (resultSet.next()){
                        if (resultSet.getInt("LogID") == log_ID){
                            sum +=resultSet.getFloat("LoggedValue");
                        }
                    }
                    myLabel.setText( "סך כל הLoggedValue כאשר ה LogID = "+log_ID + " הוא: "+sum);

                    // Close resources
                    resultSet.close();
                    statement.close();
                    connection.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                myLabel.setText("אנא רשום מספר שהוא רק בין 1 ל6");
            }
        }catch (NumberFormatException e){
            myLabel.setText("הכנס אך ורק מספרים בבקשה");
        }
    }
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("firstFrame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
