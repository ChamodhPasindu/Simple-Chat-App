package lk.ijse.chatapp.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.regex.Pattern;

public class ClientAppFormController {
    public TextField txtMessageArea;
    public Pane mainContext;
    public Pane messageAreaContext;
    public Text txtUsername;
    public TextArea txtArea;

    final int PORT = 3000;
    public ScrollPane scrollPane;
    public VBox vboxMessage;
    private String username;
    private Socket socket;

    final FileChooser chooser = new FileChooser();

    Client client;


    public void initialize(String username) throws IOException {

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStylesheets().add(this.getClass().getResource("../view/assets/style/style.css").toExternalForm());

        this.username = username;
        txtUsername.setText(username);

        socket = new Socket("localhost", PORT);
        System.out.println("socket : " + socket);
        client = new Client(socket, txtUsername.getText());
        client.sendMessage(username);

        vboxMessage.heightProperty().addListener((observable, oldValue, newValue) -> scrollPane.setVvalue((Double) newValue));

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
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(1, 5, 1, 10));
        Text text ;
        TextFlow textFlow;
        if (!message.contains(":")){
            hBox.setAlignment(Pos.CENTER);
             text = new Text(message);
             text.setStyle( "-fx-font-weight: bold;-fx-font-size: 10px");
            text.setFill(Color.color(0.934, 0.945, 0.996));
            textFlow = new TextFlow(text);



        }else {
            hBox.setAlignment(Pos.CENTER_LEFT);
            String[]parts=message.split(":");
            String name=parts[0];
            String msg=parts[1];

            Text text1 = new Text(name);
            text1.setStyle("-fx-font-size: 12px;-fx-font-weight: bold");
            Text text2 = new Text("\n"+msg);

            text1.setFill(Color.color(0.57, 0.66, 0.239));
            text2.setFill(Color.color(0.934, 0.945, 0.996));

            textFlow = new TextFlow(text1,text2);

        }

        textFlow.setStyle("-fx-background-radius: 12px;" +
                "-fx-background-color: #424242;" +
                "-fx-font-size: 15px;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

        textFlow.setPadding(new Insets(8, 10, 8, 8));
        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> vboxMessage.getChildren().add(hBox));

    }

    public void logoutBtnOnAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) mainContext.getScene().getWindow();
            stage.close();
            System.exit(0);
        }
    }

    public void sendMessage() throws IOException {
        String message = txtMessageArea.getText().trim();
        if (!message.isEmpty()) {

            client.sendMessage(txtUsername.getText()+":"+message);

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(1, 5, 1, 10));

            Text text = new Text(message);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-radius: 12px;" +
                    "-fx-background-color: #000e5e;" +
                    "-fx-font-size: 15px");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            vboxMessage.getChildren().add(hBox);

            txtMessageArea.clear();

        }

    }

    public void choosePhotoOnAction(ActionEvent actionEvent) {
        chooser.setTitle("Image Selector");
        File file = chooser.showOpenDialog(null);
        System.out.println(file.getAbsolutePath());
    }

    public void sendMessageOnMouse(MouseEvent mouseEvent) throws IOException {
        sendMessage();
    }

    public void sendMessageOnKey(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

}
