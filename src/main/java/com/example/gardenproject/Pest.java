package com.example.gardenproject;

public class Pest {
    public String Pest_type;
    public int pest_killer_id;
    public Pest(String Pest_type, int pest_killer_id) {
        this.Pest_type = Pest_type;
        this.pest_killer_id = pest_killer_id;
    }
}

class Fly {
    public Fly(){
        super("Fly", 0);
    }
}

class Moth {
    public Moth(){
        super("Moth",1);
    }
}

class Spider {
    public Spider(){
        super("Spider",2);
    }
}