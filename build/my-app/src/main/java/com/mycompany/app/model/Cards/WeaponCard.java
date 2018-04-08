package com.mycompany.app.model.Cards;

import com.mycompany.app.model.Cards.Behaviours.AdventureBehaviour;

public class WeaponCard extends AdventureCard{

    public WeaponCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.WEAPON);

    }


}