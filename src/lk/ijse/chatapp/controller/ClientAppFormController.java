package lk.ijse.chatapp.controller;

import com.vdurmont.emoji.EmojiParser;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.chatapp.model.Client;

import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Objects;

public class ClientAppFormController {
    public TextField txtMessageArea;
    public Pane mainContext;

    final int PORT = 3000;
    public ScrollPane scrollPane;
    public VBox vboxMessage;
    public Pane emojiAreaContext;
    public ImageView emjGrind;
    public ImageView emjSmile;
    public ImageView emjWink;
    public ImageView emjSmirk;
    public ImageView emjYum;
    public ImageView emjStuck_out_tongue_closed_eyes;
    public ImageView emjJoy;
    public ImageView emjBlush;
    public ImageView emjKissing_heart;
    public ImageView emjHeart_eyes;
    public ImageView emjSunglasses;
    public ImageView emjFlushed;
    public ImageView emjOpen_mouth;
    public ImageView emjAnguished;
    public ImageView emjScream;
    public ImageView emjSob;
    public ImageView emjExpressionless;
    public ImageView emjSleeping;
    public ImageView emjConfounded;
    public ImageView emjAngry;
    public Pane activeUserContext;
    public VBox vboxActiveUser;
    public ScrollPane activeUserScrollPane;
    public Text txtActiveUser;
    private Socket socket;

    final FileChooser chooser = new FileChooser();
    Client client;

    String username;
    int activeUserCount = 0;

    public void initialize(String username) throws IOException {
        this.username = username;

        activeUserScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("../view/assets/style/style.css")).toExternalForm());


        socket = new Socket("localhost", PORT);
        System.out.println("socket : " + socket);
        client = new Client(socket);
        client.sendMessage(username);

        setActiveUsers(username + "(you)");

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


        emjGrind.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":grin:"));
        });
        emjSmile.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":smile:"));
        });
        emjWink.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":wink:"));
        });
        emjSmirk.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":smirk:"));
        });
        emjYum.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":yum:"));
        });
        emjStuck_out_tongue_closed_eyes.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":stuck_out_tongue_closed_eyes:"));
        });
        emjJoy.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":joy:"));
        });
        emjBlush.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":blush:"));
        });
        emjKissing_heart.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":kissing_heart:"));
        });
        emjHeart_eyes.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":heart_eyes:"));
        });
        emjSunglasses.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":sunglasses:"));
        });
        emjFlushed.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":flushed:"));
        });
        emjOpen_mouth.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":open_mouth:"));
        });
        emjAnguished.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":anguished:"));
        });
        emjScream.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":scream:"));
        });
        emjSob.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":sob:"));
        });
        emjExpressionless.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":expressionless:"));
        });
        emjSleeping.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":sleeping:"));
        });
        emjConfounded.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":confounded:"));
        });
        emjAngry.setOnMouseClicked(event -> {
            String text = txtMessageArea.getText();
            txtMessageArea.setText(text + EmojiParser.parseToUnicode(":angry:"));
        });
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

            if (message.contains("-")) {
                String[] split = message.split("-");
                String name = split[0];
                setActiveUsers(name.trim());
                return;
            } else if (message.contains("joined")) {
                String[] split = message.split("has");
                String name = split[0];
                setActiveUsers(name.trim());
            } else if (message.contains("left")) {
                String[] split = message.split("has");
                String name = split[0];
                setInActiveUsers(name.trim());
            }

            hBox.setAlignment(Pos.CENTER);
            text = new Text(message.toUpperCase(Locale.ROOT));
            text.setStyle("-fx-font-weight: bold;-fx-font-size: 10px");
            text.setFill(Color.web("#aaa9a9"));
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Do You Want Logout");
                DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("../view/assets/style/myDialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

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
            String parse = EmojiParser.parseToUnicode(message);

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(1, 5, 1, 10));

            Text text = new Text(parse);
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-radius: 12px;" +
                    "-fx-background-color: #195C4B;" +
                    "-fx-font-size: 15px" +
                    "-fx-font-family OpenSansEmoji;");

            textFlow.setPadding(new Insets(8, 10, 8, 8));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            vboxMessage.getChildren().add(hBox);
            client.sendMessage(username + "->MSG" + parse);

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

        client.sendMessage(username + "->IMG" + file.toURI());

    }

    public void sendMessageOnMouse() {
        sendMessage();
    }

    public void sendMessageOnKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    public void emojiBtnOnAction() {
        if (emojiAreaContext.isVisible()) {
            emojiAreaContext.setVisible(false);
            emojiAreaContext.setManaged(false);
        } else {
            emojiAreaContext.setVisible(true);
            emojiAreaContext.setManaged(true);
        }
    }

    public void showActivesOnAction() {
        if (activeUserContext.isVisible()) {
            activeUserContext.setVisible(false);
            activeUserContext.setManaged(false);

        } else {
            activeUserContext.setVisible(true);
            activeUserContext.setManaged(true);
        }
    }

    private void setActiveUsers(String name) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(0, 0, 5, 0));
        hBox.setAlignment(Pos.CENTER_LEFT);

        Circle circle = new Circle();
        circle.setRadius(5);
        circle.setFill(Color.web("#1eff46"));
        Text text = new Text(" " + name);
        text.setStyle("-fx-background-radius: 12px;" +
                "-fx-font-size: 15px");
        TextFlow textFlow = new TextFlow(circle, text);

        text.setFill(Color.web("white"));

        hBox.getChildren().add(textFlow);
        Platform.runLater(() -> {
            vboxActiveUser.getChildren().add(hBox);
            txtActiveUser.setText(" (" + (activeUserCount += 1) + ")");
        });
    }

    private void setInActiveUsers(String name) {
        for (Node hBox : vboxActiveUser.getChildren()) {
            if (hBox instanceof HBox) {
                for (Node textFlow : ((HBox) hBox).getChildren()) {
                    if (textFlow instanceof TextFlow) {
                        for (Node text : ((TextFlow) textFlow).getChildren()) {
                            if (text instanceof Text) {
                                if (((Text) text).getText().trim().equals(name)) {
                                    Platform.runLater(() -> {
                                                vboxActiveUser.getChildren().remove(hBox);
                                                txtActiveUser.setText("(" + (activeUserCount -= 1) + ")");
                                            }
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
