package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;

public class TestCard extends AdventureCard{

    public TestCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.TEST);

    }


}
