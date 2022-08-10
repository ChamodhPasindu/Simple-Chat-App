package lk.ijse.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientAppFormController {
    public TextField txtMessageArea;
    public Pane mainContext;
    public Pane messageAreaContext;
    public Text txtUsername;

    public void initialize(){
    }


    public void logoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage window= (Stage) mainContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/clientLoginForm.fxml"))));
    }

    public void sendMessageOnAction(ActionEvent actionEvent) {

    }

    public void sendPhotoOnAction(ActionEvent actionEvent) {

    }
}
