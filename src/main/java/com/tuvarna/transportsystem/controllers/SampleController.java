package com.tuvarna.transportsystem.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public void Change(javafx.event.ActionEvent event) throws IOException {
		Parent adminPanel = FXMLLoader.load(getClass().getClassLoader().getResource("/views/UserPanel.fxml"));
		Scene adminScene = new Scene(adminPanel);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
}
