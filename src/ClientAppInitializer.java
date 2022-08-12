import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientAppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("lk/ijse/chatapp/view/ClientLoginForm.fxml"))));
        primaryStage.setTitle("Play Tech Chat");
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }
}
