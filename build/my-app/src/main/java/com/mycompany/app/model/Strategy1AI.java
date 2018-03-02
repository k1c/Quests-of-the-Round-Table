package com.mycompany.app.model;

import java.util.ArrayList;
import java.util.List;

public class Strategy1AI extends AbstractAI {

    public Strategy1AI(){
        this.behaviour = new Strategy1();
    }

}
