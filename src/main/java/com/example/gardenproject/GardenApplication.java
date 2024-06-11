package com.example.gardenproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class GardenApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GardenApplication.class.getResource("garden.fxml"));

        // Load the FXML content and get the root and controller
        Parent root = fxmlLoader.load();
        GardenController controller = fxmlLoader.getController();

        // Start the controller
        controller.startController();

        // Get the StackPane from the FXML to add the WorldUI
        StackPane gardenStackPane = (StackPane) fxmlLoader.getNamespace().get("gardenStackPane");

        // Create the scene and the WorldUI
        Scene scene = new Scene(root, 800, 600);
        WorldUI worldUI = new WorldUI(scene, gardenStackPane);
        controller.setWorldUI(worldUI);

        stage.setTitle("Garden Project");
        stage.setScene(scene);
        stage.show();

        // Periodically update the UI to reflect changes
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                worldUI.updateUI();
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
