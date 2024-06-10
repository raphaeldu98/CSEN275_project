package com.example.gardenproject;

public class Plant {
    public String alert_msg;
    public String log_msg;
    public String plant_type;
    public int health;
    public int water;
    public Pest pest;
    public int fertilizer_type;
    public int fertilizer_level;

    public Plant(String plant_type, int fertilizer_type) {
        this.plant_type = plant_type;
        health = 5;
        water = 0;
        pest = null;
        this.fertilizer_type = fertilizer_type;
        fertilizer_level = 0;
    }

    public String command(int command_id, int x, int y) {
        String ret = "";
        if (command_id >= 2 && command_id <= 4) {
            command_id -= 2;
            if (command_id == fertilizer_type) {
                fertilizer_level += 2;
                ret = plant_type + " at (" + x + "," + y + ") successfully fertilized with type " + command_id + ".";
            } else {
                ret = "Fertilizer type " + command_id + " does not match " + plant_type + "'s needs at (" + x + "," + y + ").";
            }
        } else if (command_id >= 5 && command_id <= 7) {
            command_id -= 5;
            if (pest != null) {
                boolean flag = pest.pesticide(command_id);
                if (flag) {
                    ret = pest.Pest_type + " at (" + x + "," + y + ") successfully eliminated.";
                    pest = null;
                } else {
                    ret = "Wrong pesticide used at (" + x + "," + y + ").";
                }
            } else {
                ret = "There is no pest on " + plant_type + " at (" + x + "," + y + ").";
            }
        } else {
            water += 2;
            ret = plant_type + " at (" + x + "," + y + ") successfully watered.";
        }
        update_alert_msg();
        return ret;
    }

    public void update_alert_msg() {
        StringBuilder msg = new StringBuilder();
        if (water <= 0) msg.append("Drying out!\n");
        if (water >= 3) msg.append("Drowning!\n");
        if (fertilizer_level <= 0) {
            switch (fertilizer_type) {
                case 0:
                    msg.append("N Fertilizer ");
                    break;
                case 1:
                    msg.append("P Fertilizer ");
                    break;
                case 2:
                    msg.append("K Fertilizer ");
                    break;
            }
            msg.append("needed!\n");
        }
        if (fertilizer_level >= 3) msg.append("Over fertilized!");
        if (pest != null) msg.append(pest.Pest_type + " attacking!");
        alert_msg = msg.toString();
    }

    public String end_of_Day(int x, int y) {
        if (health == 0) return "Plant at (" + x + "," + y + ") is dead.";
        StringBuilder ret = new StringBuilder();
        boolean healthy_flag = true;
        water -= 1 + SystemAPI.water_change;
        fertilizer_level--;
        ret.append("Plant ").append(plant_type).append(" at (").append(x).append(",").append(y).append(")\n");
        ret.append("Plant current water level is ").append(water).append(".\n");
        if (water < 0) {
            ret.append("Plant is drying out.\n");
            health--;
            healthy_flag = false;
        } else if (water > 3) {
            ret.append("Plant is drowning.\n");
            health--;
            healthy_flag = true;
        }
        ret.append("Plant current fertilizer level is ").append(fertilizer_level).append(".\n");
        if (fertilizer_level < 0) {
            ret.append("Plant is under fertilized.\n");
            health--;
            healthy_flag = false;
        } else if (fertilizer_level > 3) {
            ret.append("Plant is over fertilized.\n");
            health--;
            healthy_flag = false;
        }
        if (pest != null) {
            ret.append("Plant is affected by ").append(pest.Pest_type).append(".\n");
            health--;
            healthy_flag = false;
        }
        if (healthy_flag) {
            ret.append("Plant is currently very healthy.\n");
            health++;
        }
        ret.append("Plant current HP is: ").append(health).append(".\n");

        return ret.toString();
    }
}

class SunFlower extends Plant {
    public SunFlower() {
        super("SunFlower", 0);
    }
}

class Rose extends Plant {
    public Rose() {
        super("Rose", 1);
    }
}

class Tomato extends Plant {
    public Tomato() {
        super("Tomato", 2);
    }
}
