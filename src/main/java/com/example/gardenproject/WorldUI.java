package com.example.gardenproject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Objects;

public class WorldUI {
    private static final String IMAGE_PATH = "/com/example/gardenproject/images/";
    private final Pane overlayPane;
    private final ImageView backgroundImageView;
    private final StackPane root;
    private final Scene scene;
    private final Image grassImage;
    private final Image flowerImage;
    private static final int GRID_SIZE = 4;
    private final ImageView[][] flowers;

    public WorldUI(Parent root, double sceneWidth, double sceneHeight) {
        // Load images
        this.grassImage = getImage("grass.png");
        this.flowerImage = getImage("sunflower.png");

        this.flowers = new ImageView[GRID_SIZE][GRID_SIZE];
        this.backgroundImageView = makeBackground(grassImage);

        this.overlayPane = new Pane();
        this.overlayPane.getChildren().add(backgroundImageView);

        this.root = new StackPane();
        this.root.getChildren().addAll(overlayPane, root);

        this.scene = new Scene(this.root, sceneWidth, sceneHeight);
        updateUI();
    }

    public static Image getImage(String filename) {
        return new Image(Objects.requireNonNull(WorldUI.class.getResourceAsStream(IMAGE_PATH + filename)));
    }

    public ImageView makeBackground(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void showFlower(int i, int j) {
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

        flowerView.layoutXProperty().bind(scene.widthProperty().subtract(grassImage.getWidth()).divide(2).add(x));
        flowerView.layoutYProperty().bind(scene.heightProperty().subtract(grassImage.getHeight()).divide(2).add(y));

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
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        double imageWidth = grassImage.getWidth();
        double imageHeight = grassImage.getHeight();

        backgroundImageView.setX((sceneWidth - imageWidth) / 2);
        backgroundImageView.setY((sceneHeight - imageHeight) / 2);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                showFlower(row, col);
            }
        }
    }

    public Scene getScene() {
        return scene;
    }
}
