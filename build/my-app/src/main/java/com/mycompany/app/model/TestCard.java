package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCards;

public class TestCard extends AdventureCards{

    public TestCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.TEST);

    }


}
