package com.example.gardenproject;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GardenSimulationAPI {
    private HashMap<String, Plant> plants;
    private int pestChance;
    private int waterLevel;
    private int dayLength;
    private Random random;
    private Logger logger;

    public GardenSimulationAPI(int pestChance, int dayLength, Logger logger) {
        this.pestChance = pestChance;
        this.dayLength = dayLength;
        this.random = new Random();
        this.logger = logger;
        plants = new HashMap<>();
    }

    public void initializeGarden() {
        plants.put("Rose", new Plant("Rose", 10, List.of("fly", "moth"), "N_fertilizer"));
        plants.put("Tomato", new Plant("Tomato", 15, List.of("spider"), "P_fertilizer"));
        logger.log("Garden initialized with Rose and Tomato.");
    }

    public Map<String, Object> getPlants() {
        Map<String, Object> plantDetails = new HashMap<>();
        List<String> plantNames = new ArrayList<>();
        List<Integer> waterRequirements = new ArrayList<>();
        List<List<String>> parasites = new ArrayList<>();

        for (Plant plant : plants.values()) {
            plantNames.add(plant.getName());
            waterRequirements.add(plant.getWaterRequirement());
            parasites.add(plant.getParasites());
        }

        plantDetails.put("plants", plantNames);
        plantDetails.put("waterRequirement", waterRequirements);
        plantDetails.put("parasites", parasites);

        return plantDetails;
    }

    public void rain(int amount) {
        for (Plant plant : plants.values()) {
            plant.water(amount);
        }
        logger.log("Rain applied: " + amount + " units.");
    }

    public void temperature(int temp) {
        for (Plant plant : plants.values()) {
            plant.adjustTemperature(temp);
        }
        if (temp > 100) {
            waterLevel = -1;
            water();
        }
        logger.log("Temperature set to: " + temp + " degrees.");
    }

    public void parasites(String type) {
        for (Plant plant : plants.values()) {
            plant.infestedBy(type);
        }
        logger.log("Parasites applied: " + type + ".");
    }

    public void getState() {
        for (Plant plant : plants.values()) {
            plant.printStatus();
        }
        logger.log("Garden status checked.");
    }

    public void seed(String name, int waterRequirement, List<String> parasites) {
        String[] fertilizers = {"N_fertilizer", "P_fertilizer", "K_fertilizer"};
        String neededFertilizer = fertilizers[random.nextInt(fertilizers.length)];
        plants.put(name, new Plant(name, waterRequirement, parasites, neededFertilizer));
        logger.log("Seeded plant: " + name + " with water requirement: " + waterRequirement + " and parasites: " + parasites + ".");
    }

    public void removePlant(String name) {
        plants.remove(name);
        logger.log("Removed plant: " + name + ".");
    }

    public void applyFertilizer(String type) {
        for (Plant plant : plants.values()) {
            plant.applyFertilizer(type);
        }
        logger.log("Fertilizer applied: " + type + ".");
    }

    public void applyPestControl(String pest) {
        for (Plant plant : plants.values()) {
            plant.applyPestControl(pest);
        }
        logger.log("Pest control applied for: " + pest + ".");
    }

    public void water() {
        int amount = (waterLevel == 1) ? 20 : (waterLevel == -1) ? 5 : 10;
        rain(amount);
        logger.log("Watering plants with amount: " + amount + ".");
    }

    public void simulateHour() {

        int action = random.nextInt(3);
        switch (action) {
            case 0:
                rain(random.nextInt(21) + 10);
                break;
            case 1:
                temperature(random.nextInt(81) + 40);
                break;
            case 2:
                String[] pests = {"fly", "moth", "spider"};
                String randomPest = pests[random.nextInt(pests.length)];
                parasites(randomPest);
                break;
        }

        // Randomly generate pests
        if (random.nextInt(100) < pestChance) {
            String[] pests = {"fly", "moth", "spider"};
            String randomPest = pests[random.nextInt(pests.length)];
            parasites(randomPest);
        }

        // Check each plant and apply necessary actions
        List<String> plantsToRemove = new ArrayList<>();
        for (Plant plant : plants.values()) {
            plant.dailyUpdate();
            if (!plant.isAlive()) {
                plantsToRemove.add(plant.getName());
            } else {
                if (plant.needsWater() && waterLevel == 0) {
                    water();
                }
                if (plant.needsFertilizer()) {
                    applyFertilizer(plant.getNeededFertilizer());
                }
                if (plant.isInfested()) {
                    applyPestControl(plant.getInfestingPest());
                }
            }
        }

        // Remove dead plants
        for (String plantName : plantsToRemove) {
            removePlant(plantName);
        }

        getState();
        logger.log("Hour simulation completed.");
    }

    public void simulateGarden(int hours) {
        for (int i = 0; i < hours; i++) {
            logger.log("Hour " + (i + 1) + ":");
            simulateHour();
            sleepOneHour();
        }
    }

    private void sleepOneHour() {
        try {
            Thread.sleep(dayLength * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
