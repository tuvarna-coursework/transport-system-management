package com.tuvarna.transportsystem.entities;

import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.services.UserTypeService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

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
		//DatabaseUtils.testMappings();
		/*UserProfile profile = new UserProfile(4.9, 4.6);
		new UserProfileService().save(profile);
		UserType type = new UserTypeService().getById(3);
		Location location = (Location) new LocationService().getByName("Varna").get(0);
		UserService userService = new UserService();





		User user= new User("Georgi Petrov","gogata","1234",profile,type,location);
		userService.save(user);*/





		launch(args);
	}
}
