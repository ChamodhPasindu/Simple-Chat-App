package lk.ijse.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientLoginFormController {
    public TextField txtUsername;
    public AnchorPane loginContext;


    public void initialize(){
    }

    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ClientAppForm.fxml"));
        Parent parent=loader.load();
        ClientAppFormController controller = loader.<ClientAppFormController>getController();
        controller.initialize(txtUsername.getText());
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(parent));
        txtUsername.clear();
    }
}
