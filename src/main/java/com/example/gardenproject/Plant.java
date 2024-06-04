package com.example.gardenproject;

import java.util.List;

class Plant {
    private String name;
    private int waterRequirement;
    private List<String> parasites;
    private int currentWaterLevel;
    private boolean alive;
    private String infestingPest;
    private boolean needsFertilizer;
    private String neededFertilizer;
    private int hoursWithoutWater;
    private int hoursWithPest;
    private String status;

    public Plant(String name, int waterRequirement, List<String> parasites, String neededFertilizer) {
        this.name = name;
        this.waterRequirement = waterRequirement;
        this.parasites = parasites;
        this.currentWaterLevel = 0;
        this.alive = true;
        this.infestingPest = null;
        this.needsFertilizer = true;
        this.neededFertilizer = neededFertilizer;
        this.hoursWithoutWater = 0;
        this.hoursWithPest = 0;
        this.status = "seed"; // Initially, the plant is in "seed" status
    }

    public String getName() {
        return name;
    }

    public int getWaterRequirement() {
        return waterRequirement;
    }

    public List<String> getParasites() {
        return parasites;
    }

    public boolean needsWater() {
        return currentWaterLevel < waterRequirement;
    }

    public boolean needsFertilizer() {
        return needsFertilizer;
    }

    public String getNeededFertilizer() {
        return neededFertilizer;
    }

    public boolean isInfested() {
        return infestingPest != null;
    }

    public String getInfestingPest() {
        return infestingPest;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getStatus() {
        return status;
    }

    public void water(int amount) {
        if (alive) {
            currentWaterLevel += amount;
            if (currentWaterLevel > waterRequirement) {
                currentWaterLevel = waterRequirement; // prevent overwatering
            }
            hoursWithoutWater = 0; // reset hours without water
        }
    }

    public void adjustTemperature(int temp) {
        // Add temperature handling logic
    }

    public void infestedBy(String parasite) {
        if (parasites.contains(parasite)) {
            infestingPest = parasite;
        }
    }

    public void applyFertilizer(String type) {
        if (alive && type.equals(neededFertilizer)) {
            needsFertilizer = false;
            status = "plant";
        }
    }

    public void applyPestControl(String pest) {
        if (parasites.contains(pest)) {
            infestingPest = null;
            hoursWithPest = 0; // reset hours with pest
        }
    }

    public void dailyUpdate() {
        if (needsWater()) {
            hoursWithoutWater++;
            if (hoursWithoutWater > 24) {
                alive = false;
            }
        } else {
            hoursWithoutWater = 0;
        }

        if (isInfested()) {
            hoursWithPest++;
            if (hoursWithPest > 24) {
                alive = false;
            }
        } else {
            hoursWithPest = 0;
        }
    }

    public void printStatus() {
        System.out.println("Plant: " + name + ", Status: " + status + ", Alive: " + alive + ", Water Level: " + currentWaterLevel);
    }
}
