package com.example.gardenproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class GardenApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GardenApplication.class.getResource("garden.fxml"));

        // Load the FXML content and get the root and controller
        Parent root = fxmlLoader.load();
        GardenController controller = fxmlLoader.getController();

        // Create the WorldUI instance and set the scene
        WorldUI worldUI = new WorldUI(root, 800, 600);
        controller.setWorldUI(worldUI);

        stage.setTitle("Garden Project");
        stage.setScene(worldUI.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
