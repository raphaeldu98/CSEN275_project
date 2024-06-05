package com.example.gardenproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Random;

public class GardenController {
    @FXML
    private Button removeFlowerButton;

    private WorldUI worldUI;
    private final Random random = new Random();

    public void setWorldUI(WorldUI worldUI) {
        this.worldUI = worldUI;
    }

    @FXML
    protected void onRemoveFlowerButtonClick() {
        if (worldUI != null) {
            int i = random.nextInt(4); // Assuming grid size is 4x4
            int j = random.nextInt(4);
            worldUI.removeFlower(i, j);
        }
    }
}
