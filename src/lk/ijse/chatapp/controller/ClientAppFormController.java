package lk.ijse.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientAppFormController{
    public TextField txtMessageArea;
    public Pane mainContext;
    public Pane messageAreaContext;
    public Text txtUsername;
    public TextArea txtArea;

    final int PORT = 3000;
    private String username;

    Client client;


    public void initialize(String username) throws IOException {
        this.username=username;
        txtUsername.setText(username);

        Socket socket = new Socket("localhost", PORT);
        System.out.println("socket : "+socket);
        client = new Client(socket, txtUsername.getText());
        client.sendMessage(username);

        BufferedReader bufferedReader = client.getBufferedReader();

        new Thread(() -> {
            String msgFromGroupChat;
            while (socket.isConnected()) {
                try {
                    msgFromGroupChat = bufferedReader.readLine();
                    String message = msgFromGroupChat;
                    setMsgToTextArea(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setMsgToTextArea(String message) {
/*
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-color:red; " +
                "-fx-background-color:blue;" +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5,10,5,10));
        text.setFill(Color.color(0.9,0.9,0.9));
        hBox.getChildren().add(textFlow);*/
        System.out.println(message);
        txtArea.appendText("\n"+message);

    }

    public void logoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) mainContext.getScene().getWindow();
            stage.close();
        }
    }

    public void sendMessage() throws IOException {
        if (!txtMessageArea.getText().isEmpty()){
            client.sendMessage((txtUsername.getText()+" : \n"+txtMessageArea.getText().trim()));
            txtMessageArea.clear();
        }

    }

    public void sendPhotoOnAction(ActionEvent actionEvent) {

    }

    public void sendMessageOnMouse(MouseEvent mouseEvent) throws IOException {
        sendMessage();
    }

    public void sendMessageOnKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode()== KeyCode.ENTER){
            sendMessage();
        }
    }

}
