package com.mycompany.app.model;
import com.mycompany.app.model.AdventureCards;
public class WeaponCard extends AdventureCards{

    public WeaponCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.WEAPON);

    }


}