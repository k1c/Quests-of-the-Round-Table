package com.mycompany.app.model;

public class WeaponCard extends AdventureCards{

    public WeaponCard(int id, String res, AdventureBehaviour behaviour, String name){
        this.super(id, res, behaviour, name, Types.WEAPON);

    }


}