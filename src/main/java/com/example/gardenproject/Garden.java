package com.example.gardenproject;

import java.util.Queue;

public class Garden implements Runnable{
    public static boolean program_running_flag;
    public static Queue<int[]> commands = new Queue<int[]>();
    public static Plant[][] garden = new Plant[4][4];
    public void run() {
        while(program_running_flag){
            while (commands.isEmpty()) {
                Thread.sleep(1000);
            }
            int[] command = commands.poll();
            if(command.equals(new int[]{5,5,5})){
                for(int i=0; i<4; i++){
                    for(int j=0; j<4; j++){
                        if(garden[i][j]!=null) garden[i][j].end_of_day();
                    }
                }
            }else if(command.equals(new int[]{-1,-1,-1})){
                program_running_flag = false;
                break;
            }else if(command[2] == 0){
                if(garden[command[0]][command[1]] == null){
                    int rand = (int)(Math.random()*3.0);
                    switch(rand){
                        case 0:
                            garden[command[0]][command[1]] = new Plant.SunFlower();
                            break;
                        case 1:
                            garden[command[0]][command[1]] = new Plant.Tomato();
                            break;
                        case 2:
                            garden[command[0]][command[1]] = new Plant.Rose();
                            break;
                        default:
                            break;
                    }
                }
            }else{
                if(garden[command[0]][command[1]]!=null){
                    if(command[2] == 1){
                        garden[command[0]][command[1]]=null;
                    }else{
                        garden[command[0]][command[1]].command(command[2]);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        program_running_flag = true;
        Garden garden = new Garden();
        Day_Pass_Timer timer = new Day_Pass_Timer();
        Thread t1 = new Thread(garden);
        Thread t2 = new Thread(timer);
        t1.start();
        t2.start();
    }
}

class Day_Pass_Timer implements Runnable{
    public void run() {
        while(program_running_flag){
            Thread.sleep(1000*60*SystemAPI.day_length);
            Garden.commands.add(new int[]{5,5,5});
        }
    }
}