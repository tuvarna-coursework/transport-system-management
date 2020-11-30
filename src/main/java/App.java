import com.tuvarna.transportsystem.entities.Location;
import com.tuvarna.transportsystem.entities.User;
import com.tuvarna.transportsystem.entities.UserProfile;
import com.tuvarna.transportsystem.entities.UserType;
import com.tuvarna.transportsystem.services.LocationService;
import com.tuvarna.transportsystem.services.TripService;
import com.tuvarna.transportsystem.services.UserProfileService;
import com.tuvarna.transportsystem.services.UserService;
import com.tuvarna.transportsystem.utils.DatabaseUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/CompanyAddTripPanel.fxml"));
		stage.setTitle("Transport Company");

		Scene scene = new Scene(root);
		stage.setScene(scene);

		// primaryStage.setScene(new Scene(root, 380, 500));
		stage.show();
	}

	public static void main(String[] args) {
		DatabaseUtils.init();
		launch(args);
		
		// TEST 
		
		UserService userService = new UserService();
		
		
		UserProfile profile = new UserProfile();
		new UserProfileService().save(profile);
		
		Location location = new LocationService().getByName("Varna").get();
		User user = new User("Casier1", "asdf", "123", profile, DatabaseUtils.USERTYPE_CASHIER, location);
		userService.save(user);
	
		
		userService.addCashierToTransportCompany(DatabaseUtils.currentUser, user);
	
		TripService tripService = new TripService();
	
		tripService.addCashierForTrip(tripService.getById(1).get(), user);
	}
}
