package lk.ijse.chatapp.controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.chatapp.model.Client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ClientAppFormController {
    public TextField txtMessageArea;
    public Pane mainContext;
    public Text txtUsername;

    final int PORT = 3000;
    public ScrollPane scrollPane;
    public VBox vboxMessage;
    private Socket socket;

    final FileChooser chooser = new FileChooser();
    Client client;

    public void initialize(String username) throws IOException {

        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("../view/assets/style/style.css")).toExternalForm());

        txtUsername.setText(username);

        socket = new Socket("localhost", PORT);
        System.out.println("socket : " + socket);
        client = new Client(socket);
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
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(1, 5, 1, 10));
        Text text;
        TextFlow textFlow;
        if (message.contains("->MSG")) {
            hBox.setAlignment(Pos.CENTER_LEFT);

            String[] parts = message.split("->MSG");
            String name = parts[0];
            String msg = parts[1];

            Text text1 = new Text(name);
            text1.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;");
            Text text2 = new Text("\n" + msg);

            text1.setFill(Color.web("40b240"));
            text2.setFill(Color.color(0.934, 0.945, 0.996));

            textFlow = new TextFlow(text1, text2);
            textFlow.setStyle("-fx-background-radius: 12px;" +
                    "-fx-background-color: #33434c;" +
                    "-fx-font-size: 15px;");
            textFlow.setLineSpacing(5);

        } else if (message.contains("->IMG")) {
            String[] parts = message.split("->IMG");
            String name = parts[0];
            String file = parts[1];

            hBox.setAlignment(Pos.CENTER_LEFT);

            Text text1 = new Text(name + "\n\n");
            text1.setStyle("-fx-font-size: 13px;-fx-font-weight: bold;");

            text1.setFill(Color.web("#40b240"));
            ImageView imageView = new ImageView(file);
            imageView.setFitHeight(250);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(false);
            textFlow = new TextFlow(text1, imageView);
            textFlow.setStyle("-fx-background-radius: 12px;" +
                    "-fx-background-color: #33434c;");

        } else {
            hBox.setAlignment(Pos.CENTER);
            text = new Text(message);
            text.setStyle("-fx-font-weight: bold;-fx-font-size: 10px");
            text.setFill(Color.color(0.934, 0.945, 0.996));
            textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-radius: 12px;" +
                    "-fx-background-color: #2e3b44;" +
                    "-fx-font-size: 15px;");

        }
        textFlow.setPadding(new Insets(8, 10, 8, 8));
        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> vboxMessage.getChildren().add(hBox));

    }

    public void logoutBtnOnAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) mainContext.getScene().getWindow();
            stage.close();
            System.exit(0);
        }
    }

    public void sendMessage() {
        String message = txtMessageArea.getText().trim();
        if (!message.isEmpty()) {

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(1, 5, 1, 10));

            Text text = new Text(message);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-radius: 12px;" +
                    "-fx-background-color: #195C4B;" +
                    "-fx-font-size: 15px");

            textFlow.setPadding(new Insets(8, 10, 8, 8));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            vboxMessage.getChildren().add(hBox);

            client.sendMessage(txtUsername.getText() + "->MSG" + message);

            txtMessageArea.clear();
        }
    }

    public void choosePhotoOnAction() {
        chooser.setTitle("Image Selector");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(1, 5, 1, 10));

        File file = chooser.showOpenDialog(null);
        if (file == null) {
            return;
        }
        ImageView imageView = new ImageView(file.toURI().toString());
        imageView.setFitHeight(250);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(false);
        TextFlow textFlow = new TextFlow(imageView);
        textFlow.setStyle("-fx-background-radius: 12px;" +
                "-fx-background-color: #195C4B;");

        textFlow.setPadding(new Insets(8, 10, 8, 8));

        hBox.getChildren().add(textFlow);
        vboxMessage.getChildren().add(hBox);

        client.sendMessage(txtUsername.getText() + "->IMG" + file.toURI());

    }

    public void sendMessageOnMouse() {
        sendMessage();
    }

    public void sendMessageOnKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

}
