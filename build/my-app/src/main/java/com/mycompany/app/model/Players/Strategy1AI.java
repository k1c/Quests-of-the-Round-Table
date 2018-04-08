package com.mycompany.app.model.Players;

import java.util.ArrayList;
import java.util.List;

public class Strategy1AI extends AbstractAI {

    public Strategy1AI(String name, String shieldImage){
        super(name,shieldImage);
        this.behaviour = new Strategy1();
    }

}
