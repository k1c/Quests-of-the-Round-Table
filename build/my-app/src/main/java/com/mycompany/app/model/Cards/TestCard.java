package com.mycompany.app.model.Cards;


import com.mycompany.app.model.Cards.Behaviours.AdventureBehaviour;

public class TestCard extends AdventureCard{

    public TestCard(int id, String res, AdventureBehaviour behaviour, String name){
        super(id, res, behaviour, name, Types.TEST);

    }


}
