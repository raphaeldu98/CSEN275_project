package com.example.gardenproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GardenController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}