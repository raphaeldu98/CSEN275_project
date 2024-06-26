package com.example.gardenproject;

public class SystemAPI {
    //this is an API for you to change some variables to influence the functioning of the project
    //day_length is the number of seconds for one round of garden, 5 seconds suggested for human testing and 20 seconds suggested for stability testing
    public static int day_length = 5;
    //keep tracking of how many days have passed in the simulation for log writing
    public static int date = 1;
    //automation means if you want to run the program in automation mode to test program's stability
    public static boolean automation = true;
    //plant_chance is the percentage chance the automation system will seed an unplanted grid at the beginning of a new day
    public static int plant_chance = 10;
    //pest_chance is the percentage chance a random pest will generate on a plant with no pest, 10 means 10%chance
    public static int pest_chance = 10;
    //water_level should be one of the following three, "rain", "normal", or "drought", affecting the time interval plants require watering
    public static String water_level = "normal".toLowerCase();
    public static int water_change = water_level.equals("normal")? 0 :water_level.equals("drought")?-1:1;
}