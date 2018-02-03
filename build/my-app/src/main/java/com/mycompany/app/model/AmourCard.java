package com.mycompany.app.model;

public class AmourCard extends AdventureCards{

    public AmourCard(int id, String res, AdventureBehaviour behaviour, String name){
        this.super(id, res, behaviour, name);
        this.type = Types.AMOUR;

    }


}
