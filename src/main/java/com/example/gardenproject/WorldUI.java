package com.example.gardenproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
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

    public WorldUI(FXMLLoader fxmlLoader, double sceneWidth, double sceneHeight) throws IOException {
        this.grassImage = getImage("grass.png");
        this.flowerImage = getImage("sunflower.png");

        this.backgroundImageView = makeBackground(grassImage);

        this.overlayPane = new Pane();
        this.overlayPane.getChildren().add(backgroundImageView);

        this.root = new StackPane();
        this.root.getChildren().addAll(overlayPane, fxmlLoader.load());

        this.scene = new Scene(root, sceneWidth, sceneHeight);
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
        double cellWidth = grassImage.getWidth() / GRID_SIZE;
        double cellHeight = grassImage.getHeight() / GRID_SIZE;

        ImageView flowerView = new ImageView(flowerImage);
        flowerView.setFitWidth(cellWidth / 2);
        flowerView.setFitHeight(cellHeight / 2);
        flowerView.setPreserveRatio(true);

        double x = j * cellWidth + (cellWidth - flowerView.getFitWidth()) / 2;
        double y = i * cellHeight + (cellHeight - flowerView.getFitHeight()) / 2;

        flowerView.layoutXProperty().bind(scene.widthProperty().subtract(grassImage.getWidth()).divide(2).add(x));
        flowerView.layoutYProperty().bind(scene.heightProperty().subtract(grassImage.getHeight()).divide(2).add(y));

        overlayPane.getChildren().add(flowerView);
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
