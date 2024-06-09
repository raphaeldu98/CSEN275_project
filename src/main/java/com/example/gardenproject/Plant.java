package com.example.gardenproject;

import java.util.List;

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
    public String command(int command_id){
        String ret = null;
        if(command_id>=2 && command_id<=4){
            command_id-=2;
            if(command_id==fertilizer_type) fertilizer_level+=2;
        }else if(command_id>=5 && command_id<=7){
            command_id -= 5;
            if(pest!=null){
                boolean flag = pest.pesticide(command_id);
                if(flag){
                    ret = pest.Pest_type +" successfully eliminated.";
                    pest = null;
                }else{
                    ret = "Wrong pesticide used.";
                }
            }else{
                ret = "There is no pest on this plant.";
            }
        }else{
            water += 2;
            ret = plant_type+" successfully watered.";
        }
        update_alert_msg();
        return ret;
    }
    public void update_alert_msg(){
        StringBuffer msg = new StringBuffer();
        if(water<=0) msg.append("Drying out!\n");
        if(water>=3) msg.append("Drowning!\n");
        if(fertilizer_level<=0){
            switch (fertilizer_type) {
                case 0:
                    msg.append("N Fertilizer ");
                    break;
                case 1:
                    msg.append("P Fertilizer ");
                    break;
                case 2:
                    msg.append("K Fertilizer ");
                default:
                    break;
            }
            msg.append("needed!\n");
        }
        if(fertilizer_level>=3) msg.append("Over fertilized!");
        if(pest != null) msg.append(pest.Pest_type + " attacking!");
        alert_msg = msg.toString();
    }
    public String end_of_Day(){
        if(health==0) return "Plant is dead.";
        StringBuffer ret = new StringBuffer();
        boolean healthy_flag = true;
        water-=1+SystemAPI.water_change;
        fertilizer_level--;
        ret.append("Plant "+plant_type+"\n");
        ret.append("Plant current water level is "+water+".\n");
        if(water<0){
            ret.append("Plant is drying out.\n");
            health--;
            healthy_flag = false;
        }else if(water>3){
            ret.append("Plant is drowning.\n");
            health--;
            healthy_flag = true;
        }
        ret.append("Plant current fertilizer level is "+fertilizer_level+".\n");
        if(fertilizer_level<0){
            ret.append("Plant is under fertilized.\n");
            health--;
            healthy_flag = false;
        }else if(fertilizer_level>3){
            ret.append("Plant is over fertilized.\n");
            health--;
            healthy_flag = false;
        }
        if(pest!=null){
            ret.append("Plant is affected by "+pest.Pest_type+".\n");
            health--;
            healthy_flag = false;
        }
        if(healthy_flag){
            ret.append("Plant is currently very healthy.\n");
            health++;
        }
        ret.append("Plant current HP is: "+health+".\n");

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