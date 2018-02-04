package com.mycompany.app.model;
import com.mycompany.app.model.AdventureCard;
public class WeaponCard extends AdventureCard{

    public WeaponCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.WEAPON);

    }


}