package lk.ijse.chatapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientLoginFormController {
    public TextField txtUsername;
    public AnchorPane loginContext;
    public Text txtErrorText;

    public void initialize(){
        txtUsername.setOnKeyPressed(event -> {
            if (event.getCode()== KeyCode.ENTER){
                try {
                    loginBtnOnAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loginBtnOnAction() throws IOException {
        if(txtUsername.getText().trim().isEmpty()){
            txtErrorText.setVisible(true);
            txtUsername.setStyle("-fx-border-color: #6b0000;-fx-border-radius: 10px;-fx-background-radius: 10px; -fx-background-color: #2A3942; -fx-text-fill: white;");
            return;
        }

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/ClientAppForm.fxml"));
        Parent parent=loader.load();
        ClientAppFormController controller = loader.getController();
        controller.initialize(txtUsername.getText());
        Stage window = (Stage) loginContext.getScene().getWindow();
        window.setScene(new Scene(parent));
        txtUsername.clear();
    }
    
}
