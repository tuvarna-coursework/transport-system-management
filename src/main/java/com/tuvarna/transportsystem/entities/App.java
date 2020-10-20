package com.tuvarna.transportsystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.tuvarna.transportsystem.dao.RoleDAO;
import com.tuvarna.transportsystem.services.RoleService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/sample.fxml"));
		stage.setTitle("Transport Company");

		Scene scene = new Scene(root);
		stage.setScene(scene);

		// primaryStage.setScene(new Scene(root, 380, 500));
		stage.show();
	}

	public static void main(String[] args) {
		RoleService roleService = new RoleService();
		
		/*
		 * roleService.save(new Role("User")); Role role = roleService.getById(69);
		 * System.out.println(role.getRoleName());
		 */

		/*
		List<Role> roles = roleService.getAll();
		System.out.println(roles.size());
		*/
		
		/*roleService.deleteById(69);
		roleService.deleteByName("Admin");
		*/
		

		launch(args);

	}

}
