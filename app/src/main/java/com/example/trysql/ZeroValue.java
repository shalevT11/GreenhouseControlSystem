package com.example.trysql;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class ZeroValue {

    @FXML
    private PieChart pieChart;
    @FXML
    TextArea nameText;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayValue(String outPut){
        nameText.setText(outPut);
    }
    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("firstFrame.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void showPieChart(ActionEvent event) {
        // Call start method to display the PieChart
        start(new Stage());
    }
    public void start(Stage primaryStage) {
        // Create the PieChart
        PieChart pieChart = new PieChart();

        // Fetch data from the database and populate the PieChart
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/excel", "root", "password");
            Statement stmt = connection.createStatement();
            String sql = "SELECT LogTime FROM excel.linesproductivity_updated_csv WHERE LoggedValue = 0";
            ResultSet rs = stmt.executeQuery(sql);

            // Map to store count of occurrences for each day
            java.util.Map<String, Integer> dayCountMap = new java.util.HashMap<>();

            // Iterate through the result set
            while (rs.next()) {
                // Get the date part only without the time
                String date = rs.getString("LogTime").split(" ")[0];
                // Update the count for this date
                dayCountMap.put(date, dayCountMap.getOrDefault(date, 0) + 1);
            }

            // Create ObservableList to hold PieChart.Data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Populate PieChart.Data from the dayCountMap
            for (String date : dayCountMap.keySet()) {
                pieChartData.add(new PieChart.Data(date, dayCountMap.get(date)));
            }

            // Set the data to the PieChart
            pieChart.setData(pieChartData);

            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a scene and add the PieChart to it
        Scene scene = new Scene(pieChart, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

