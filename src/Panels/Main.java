package Panels;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Transport Company");

        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);

        //primaryStage.setScene(new Scene(root, 380, 500));
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


}
