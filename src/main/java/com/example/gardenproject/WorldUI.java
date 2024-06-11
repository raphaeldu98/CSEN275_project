package com.example.gardenproject;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;

import java.util.Objects;

public class WorldUI {
    private static final String IMAGE_PATH = "/com/example/gardenproject/images/";
    private final Pane overlayPane;
    private final ImageView backgroundImageView;
    private final StackPane root;
    private final Image grassImage;
    private static final int GRID_SIZE = 4;
    private final ImageView[][] flowers;
    private final Button[][] gridButtons;
    private final Scene scene;

    public WorldUI(Scene scene, StackPane gardenStackPane) {
        // Load images
        this.grassImage = getImage("grass.png");

        this.flowers = new ImageView[GRID_SIZE][GRID_SIZE];
        this.gridButtons = new Button[GRID_SIZE][GRID_SIZE];
        this.backgroundImageView = makeBackground(grassImage);

        this.overlayPane = new Pane();

        GridPane gridPane = new GridPane();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Button button = new Button();
                // button.setOpacity(0); // Make button invisible
                button.setPrefWidth(21);
                button.setPrefHeight(20);
                final int x = i;
                final int y = j;
                button.setOnAction(e -> onGridButtonClick(x, y));
                gridButtons[i][j] = button;
                gridPane.add(button, j, i);
            }
        }

        this.scene = scene;

        this.root = new StackPane();
        this.root.getChildren().addAll(backgroundImageView, overlayPane, gridPane);

        gardenStackPane.getChildren().add(this.root);

        // Center the background image
        centerBackgroundImage();
        setSceneSizeListeners();
    }

    public static Image getImage(String filename) {
        return new Image(Objects.requireNonNull(WorldUI.class.getResourceAsStream(IMAGE_PATH + filename)));
    }

    public ImageView makeBackground(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void centerBackgroundImage() {
        backgroundImageView.setLayoutX((scene.getWidth() - backgroundImageView.getImage().getWidth()) / 2);
        backgroundImageView.setLayoutY((scene.getHeight() - backgroundImageView.getImage().getHeight()) / 2);

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setLayoutX((newVal.doubleValue() - backgroundImageView.getImage().getWidth()) / 2);
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundImageView.setLayoutY((newVal.doubleValue() - backgroundImageView.getImage().getHeight()) / 2);
        });
    }

    private void setSceneSizeListeners() {
        scene.widthProperty().addListener((obs, oldVal, newVal) -> centerBackgroundImage());
        scene.heightProperty().addListener((obs, oldVal, newVal) -> centerBackgroundImage());
    }

    public void showFlower(int i, int j, String imagePath) {
        Image flowerImage = getImage(imagePath);
        if (i < 0 || i >= GRID_SIZE || j < 0 || j >= GRID_SIZE) {
            throw new IllegalArgumentException("Invalid grid position");
        }

        double cellWidth = grassImage.getWidth() / GRID_SIZE;
        double cellHeight = grassImage.getHeight() / GRID_SIZE;

        removeFlower(i, j);

        ImageView flowerView = new ImageView(flowerImage);
        flowerView.setFitWidth(cellWidth / 2);
        flowerView.setFitHeight(cellHeight / 2);
        flowerView.setPreserveRatio(true);

        double x = j * cellWidth + (cellWidth - flowerView.getFitWidth()) / 2;
        double y = i * cellHeight + (cellHeight - flowerView.getFitHeight()) / 2;

        DoubleProperty bgLayoutX = backgroundImageView.layoutXProperty();
        DoubleProperty bgLayoutY = backgroundImageView.layoutYProperty();

        flowerView.layoutXProperty().bind(bgLayoutX.add(x));
        flowerView.layoutYProperty().bind(bgLayoutY.add(y));

        overlayPane.getChildren().add(flowerView);
        flowers[i][j] = flowerView;
    }

    public void removeFlower(int i, int j) {
        if (i < 0 || i >= GRID_SIZE || j < 0 || j >= GRID_SIZE) {
            throw new IllegalArgumentException("Invalid grid position");
        }

        ImageView flowerView = flowers[i][j];
        if (flowerView != null) {
            overlayPane.getChildren().remove(flowerView);
            flowers[i][j] = null;
        }
    }

    public void updateUI() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (Garden.garden[row][col] != null) {
                    showFlower(row, col, Garden.garden[row][col].flowerImage);
                } else {
                    removeFlower(row, col);
                }
            }
        }
    }

    private void onGridButtonClick(int x, int y) {
        if (GardenController.lastCommand >= 0) {
            Garden.commands.add(new int[]{x, y, GardenController.lastCommand});
            System.out.println("Adding command | x: " + x + ", y: " + y + ", command: " + GardenController.lastCommand);
            GardenController.lastCommand = -1; // Reset the last command
            updateUI();
        }
    }
}
