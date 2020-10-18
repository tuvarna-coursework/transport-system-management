package Panels;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DistributorPanel implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToScheduleDistributor(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel= FXMLLoader.load(getClass().getResource("DistributorPanelSchedule.fxml"));
        Scene ticketScene= new Scene(ticketPanel);

        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();

    }

    public void goToRequest(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel= FXMLLoader.load(getClass().getResource("DistributorRequestPanel.fxml"));
        Scene ticketScene= new Scene(ticketPanel);

        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();

    }
    public void goToAdd(javafx.event.ActionEvent event) throws IOException {
        Parent ticketPanel= FXMLLoader.load(getClass().getResource("DistributorAddPanel.fxml"));
        Scene ticketScene= new Scene(ticketPanel);

        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(ticketScene);
        window.show();

    }


}
