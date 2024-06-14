package com.example.gardenproject;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Garden implements Runnable {
    public static boolean program_running_flag;
    public static Queue<int[]> commands = new LinkedList<>();
    public static Plant[][] garden = new Plant[4][4];
    private static Logger logger = new Logger("garden.log");

    public void run() {
        logger.log("Garden thread started");

        while (program_running_flag) {
            try {
                while (commands.isEmpty()) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                logger.log("Error: " + e.getMessage());
            }
            int[] command = commands.poll();
            if (command != null) {
                String result = processCommand(command);
                if (result != null && !result.isEmpty()) {
                    logger.log(result);
                }
            }
        }
    }

    private String processCommand(int[] command) {
        int x = command[0];
        int y = command[1];
        int action = command[2];

        if (x == 5 && y == 5 && action == 5) {
            StringBuffer pest_msg = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (garden[i][j] != null) {
                        String endOfDayResult = garden[i][j].end_of_Day(i, j, pest_msg);
                        logger.log(endOfDayResult);
                    }
                }
            }
            SystemAPI.date++;
            logger.log(pest_msg.toString());
            //automationSystem();
            return "End of previous day processed.";
        } else if (x == -1 && y == -1 && action == -1) {
            program_running_flag = false;
            return "Program terminated.";
        } else if (action == 0) {
            if (garden[x][y] == null) {
                int rand = new Random().nextInt(3);
                switch (rand) {
                    case 0:
                        garden[x][y] = new SunFlower();
                        break;
                    case 1:
                        garden[x][y] = new Tomato();
                        break;
                    case 2:
                        garden[x][y] = new Rose();
                        break;
                    default:
                        break;
                }
                return "Plant successfully planted at position " + x + "," + y;
            } else {
                return "A plant already exists at position " + x + "," + y;
            }
        } else {
            if (garden[x][y] != null) {
                if (action == 1) {
                    garden[x][y] = null;
                    return "Plant at position " + x + "," + y + " successfully removed.";
                } else {
                    return garden[x][y].command(action, x, y);
                }
            } else {
                return "There is no plant at the position for command " + action;
            }
        }
    }

    public static void main(String[] args) {
        Logger mainLogger = new Logger("garden.log");
        mainLogger.log("Garden main method started");

        program_running_flag = true;
        Garden garden = new Garden();
        Day_Pass_Timer timer = new Day_Pass_Timer();
        Thread gardenThread = new Thread(garden);
        Thread timerThread = new Thread(timer);
        gardenThread.start();
        timerThread.start();

//        if (SystemAPI.automation) {
//            Thread automationThread = new Thread(() -> {
//                while (Garden.program_running_flag) {
//                    automationSystem();
//                    try {
//                        Thread.sleep(1000  * SystemAPI.day_length);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            automationThread.start();
//        }

        mainLogger.log("Garden main method ended");
    }

    public static void automationSystem() {
        Logger logger = new Logger("garden.log");
        logger.log("Automation system running");

        // Check the status of every plant and perform corresponding actions
        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden[i].length; j++) {
                if (garden[i][j] != null) {
                    if (garden[i][j].health <= 0) {
                        commands.add(new int[]{i, j, 1}); // Remove dead plants
                        logger.log("Added command to remove dead plant at " + i + "," + j);
                    } else {
                        if (garden[i][j].water <= 0) {
                            commands.add(new int[]{i, j, 8}); // Water the plant
                            logger.log("Added command to water plant at " + i + "," + j);
                        }
                        if (garden[i][j].fertilizer_level <= 0) {
                            commands.add(new int[]{i, j, garden[i][j].fertilizer_type + 2}); // Add fertilizer
                            logger.log("Added command to fertilize plant at " + i + "," + j);
                        }
                        if (garden[i][j].pest != null) {
                            commands.add(new int[]{i, j, garden[i][j].pest.pest_killer_id + 5}); // Use pesticide
                            logger.log("Added command to use pesticide on plant at " + i + "," + j);
                        }
                    }
                } else {
                    // Randomly generate plant on empty grid
                    if (new Random().nextInt(100) < SystemAPI.plant_chance) { // 10% chance to plant a new one
                        commands.add(new int[]{i, j, 0});
                        logger.log("Added command to plant a new plant at " + i + "," + j);
                    }
                }
            }
        }

        logger.log("Automation system completed");
    }
}

class Day_Pass_Timer implements Runnable {
    public void run() {
        Logger logger = new Logger("garden.log");
        logger.log("Day_Pass_Timer thread started");

        try {
            while (Garden.program_running_flag) {
                Thread.sleep(1000 * SystemAPI.day_length);
                Garden.commands.add(new int[]{5, 5, 5});
                //logger.log("Added end-of-day command to queue");
            }
        } catch (Exception e) {
            logger.log("Error: " + e.getMessage());
        }
    }
}
