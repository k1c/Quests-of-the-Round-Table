package com.mycompany.app.model;

public class TestCard extends AdventureCards{

    public TestCard(int id, String res, AdventureBehaviour behaviour, String name){
        this.super(id, res, behaviour, name);
        this.type = Types.TEST;

    }


}
