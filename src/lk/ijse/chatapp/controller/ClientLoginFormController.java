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

    public void loginBtnOnAction(ActionEvent actionEvent) throws IOException {
        String username=txtUsername.getText();

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/clientAppForm.fxml"));
        Parent parent=loader.load();
        ClientAppFormController controller = loader.<ClientAppFormController>getController();
        controller.txtUsername.setText(username);
        Stage window= (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(parent));
/*
        switch (username) {
            case "chamodh":
                parent parent=FXMLLoader.load(getClass().getResource("")


                break;
            case "pasindu":
                System.out.println("pasindu");
                break;
            case "sahan":
                System.out.println("sahan");
                break;
            default:
                System.out.println("wrong");
                break;
        }*/
    }
}
