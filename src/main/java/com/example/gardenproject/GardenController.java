package com.example.gardenproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.animation.AnimationTimer;
import java.util.Random;

public class GardenController {
    @FXML
    private Button seedButton;
    @FXML
    private Button removePlantButton;
    @FXML
    private Button nFertilizerButton;
    @FXML
    private Button pFertilizerButton;
    @FXML
    private Button kFertilizerButton;
    @FXML
    private Button flyKillerButton;
    @FXML
    private Button mothKillerButton;
    @FXML
    private Button spiderKillerButton;
    @FXML
    private Button waterButton;
    @FXML
    private TextArea logTextArea;
    @FXML
    private StackPane gardenStackPane;
    @FXML
    private Label dayLabel;
    @FXML
    private Label timerLabel;

    private WorldUI worldUI;
    static int lastCommand;
    private final Random random = new Random();

    public void startController() {
        Logger.setLogTextArea(logTextArea);
        Logger mainLogger = new Logger("garden.log");
        mainLogger.log("Garden main method started");

        Garden.program_running_flag = true;
        Garden garden = new Garden();
        Day_Pass_Timer timer = new Day_Pass_Timer();
        Thread gardenThread = new Thread(garden);
        Thread timerThread = new Thread(timer);

        gardenThread.start();
        timerThread.start();

        if (SystemAPI.automation) {
            Thread automationThread = new Thread(() -> {
                while (Garden.program_running_flag) {
                    Garden.automationSystem();
                    try {
                        Thread.sleep(1000 * SystemAPI.day_length);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            automationThread.start();
        }

        mainLogger.log("Garden main method ended");

        AnimationTimer uiUpdater = new AnimationTimer() {
            private long lastUpdate = 0;
            private long nextDayTime = System.currentTimeMillis() + (SystemAPI.day_length * 1000);

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) { // update every second
                    long remainingTime = (nextDayTime - System.currentTimeMillis()) / 1000;
                    dayLabel.setText("Day: " + SystemAPI.date);
                    timerLabel.setText("Time to next day: " + remainingTime + " seconds");
                    lastUpdate = now;

                    if (remainingTime <= 0) {
                        nextDayTime = System.currentTimeMillis() + (SystemAPI.day_length * 1000);
                    }
                }
            }
        };
        uiUpdater.start();
    }

    public void setWorldUI(WorldUI worldUI) {
        this.worldUI = worldUI;
    }

    @FXML
    protected void onSeedButtonClick() {
        setLastCommand(0);
    }

    @FXML
    protected void onRemovePlantButtonClick() {
        setLastCommand(1);
    }

    @FXML
    protected void onNFertilizerButtonClick() {
        setLastCommand(2);
    }

    @FXML
    protected void onPFertilizerButtonClick() {
        setLastCommand(3);
    }

    @FXML
    protected void onKFertilizerButtonClick() {
        setLastCommand(4);
    }

    @FXML
    protected void onFlyKillerButtonClick() {
        setLastCommand(5);
    }

    @FXML
    protected void onMothKillerButtonClick() {
        setLastCommand(6);
    }

    @FXML
    protected void onSpiderKillerButtonClick() {
        setLastCommand(7);
    }

    @FXML
    protected void onWaterButtonClick() {
        setLastCommand(8);
    }

    private void setLastCommand(int action) {
        lastCommand = action;
    }

    private void addCommand(int x, int y, int action) {
        Garden.commands.add(new int[]{x, y, action});
    }
}
