package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserPanel implements Initializable {





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToSchedule(javafx.event.ActionEvent event) throws IOException {
        Parent schedulePanel= FXMLLoader.load(getClass().getResource("SchedulePanel.fxml"));
        Scene scheduleScene= new Scene(schedulePanel);

        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scheduleScene);
        window.show();

    }

    public void goToTicket(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel= FXMLLoader.load(getClass().getResource("UserPanel.fxml"));
        Scene ticketScene= new Scene(ticketPanel);

        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();

    }

}
