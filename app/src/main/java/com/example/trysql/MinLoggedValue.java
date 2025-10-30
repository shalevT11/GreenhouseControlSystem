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

public class MinLoggedValue {
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

}
