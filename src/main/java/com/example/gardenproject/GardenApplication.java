package com.example.gardenproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

public class GardenApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GardenApplication.class.getResource("garden.fxml"));
        WorldUI worldUI = new WorldUI(fxmlLoader, 800, 600);

        stage.setTitle("Garden Project");
        stage.setScene(worldUI.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
