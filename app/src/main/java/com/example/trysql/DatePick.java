package com.example.trysql;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePick {
    @FXML
    private Label nameLabel;
    private Stage stage; // Declare stage

    // Setter for stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private DatePicker myStartDate;
    @FXML
    private DatePicker myFinishDate;

    public void getDate(ActionEvent event){
        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel", "root", "password");
            String sql = "SELECT * FROM excel.linesproductivity_updated_csv WHERE LoggedValue = " +
                    "(SELECT MAX(LoggedValue) FROM excel.linesproductivity_updated_csv where LogTime between ? and ? )";
            PreparedStatement statement = connection.prepareStatement(sql);
            LocalDate myDate = myStartDate.getValue();
            LocalDate myDate2 = myFinishDate.getValue();
            String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String myFormattedDate2 = myDate2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            statement.setString(1, myFormattedDate);
            statement.setString(2, myFormattedDate2);
            ResultSet resultSet = statement.executeQuery();
            String outPut = "";
            while (resultSet.next()) {
                outPut += "For the MAX LoggedValue: "+resultSet.getFloat("LoggedValue")+ ", the LineID is: "+resultSet.getString("LineID")+"\n";
            }
            nameLabel.setText(outPut);
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}