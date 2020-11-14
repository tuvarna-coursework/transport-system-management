

import com.tuvarna.transportsystem.services.RoleService;
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
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/CompanyRequestPanel.fxml"));
		stage.setTitle("Transport Company");

		Scene scene = new Scene(root);
		stage.setScene(scene);

		// primaryStage.setScene(new Scene(root, 380, 500));
		stage.show();
	}

	public static void main(String[] args) {
		//DatabaseUtils.populateAuxiliaryTables();
		//DatabaseUtils.testMappings();
	
		launch(args);
	}
}
