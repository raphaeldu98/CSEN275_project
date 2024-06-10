package com.example.gardenproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    private WorldUI worldUI;
    private final Random random = new Random();
    public static int lastCommand;

    public void startController() {
        System.out.println("Starting Controller.");
        Logger mainLogger = new Logger("garden.log");
        mainLogger.log("Garden main method started");
        System.out.println("Logging Generated.");

        Garden.program_running_flag = true;
        Garden garden = new Garden();
        Day_Pass_Timer timer = new Day_Pass_Timer();
        Thread gardenThread = new Thread(garden);
        Thread timerThread = new Thread(timer);

        gardenThread.start();
        timerThread.start();

        System.out.println("Threads started.");

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
        System.out.println("Last command set: " + action);
    }

    private void addCommand(int x, int y, int action) {
        Garden.commands.add(new int[]{x, y, action});
        System.out.println("Command added: " + x + ", " + y + ", " + action);
    }
}
