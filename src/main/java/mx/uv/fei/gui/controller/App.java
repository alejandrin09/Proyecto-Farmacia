package mx.uv.fei.gui.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.application.Platform;
import javafx.stage.Modality;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FXMLLogin.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void openNewWindow(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlFile));
        Parent root = loader.load();

        Scene newScene = new Scene(root);
        primaryStage.setScene(newScene);

        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();

        newScene.getWindow().setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void newView(Parent root) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
