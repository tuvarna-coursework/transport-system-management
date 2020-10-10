package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Transport Company");

        Scene scene=new Scene(root);
        stage.setScene(scene);

        //primaryStage.setScene(new Scene(root, 380, 500));
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


}
