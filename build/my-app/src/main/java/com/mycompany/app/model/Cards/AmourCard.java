package com.mycompany.app.model.Cards;


import com.mycompany.app.model.Cards.Behaviours.AdventureBehaviour;

public class AmourCard extends AdventureCard{

    public AmourCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.AMOUR);

    }


}
