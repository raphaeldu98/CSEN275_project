package com.example.gardenproject;

import java.util.List;
public class GardenSimulator {
    public static void main(String[] args) {
        Logger logger = new Logger("garden_log.txt");
        GardenSimulationAPI gardenAPI = new GardenSimulationAPI(10, 1, logger);

        gardenAPI.initializeGarden();
        gardenAPI.seed("Cucumber", 12, List.of("fly"));
        gardenAPI.removePlant("Tomato");

        gardenAPI.simulateGarden(240);

        logger.close();
    }
}
