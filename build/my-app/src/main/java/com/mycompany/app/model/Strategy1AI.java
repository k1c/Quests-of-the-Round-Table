package com.mycompany.app.model;

public class Strategy1AI extends AbstractAI {

    public Strategy1AI(String name, String shieldImage){
        super(name,shieldImage);
        this.behaviour = new Strategy1();
    }

}
