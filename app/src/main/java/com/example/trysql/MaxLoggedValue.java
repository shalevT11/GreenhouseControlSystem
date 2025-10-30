package com.example.trysql;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class MaxLoggedValue {
    @FXML
    TextArea nameText;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayValue(String outPut){
        nameText.setText(outPut);
    }

    public void switchToScene1(ActionEvent event) throws IOException { // Method to switch to another scene

        root = FXMLLoader.load(getClass().getResource("firstFrame.fxml")); // Load the FXML file for the desired scene
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Set the stage for the loaded scene
        scene = new Scene(root);
        stage.setScene(scene);  // Set the scene for the stage
        stage.show(); // Show the stage
    }
}
