package com.mycompany.app.model.Cards;

import com.mycompany.app.model.Cards.Behaviours.StoryBehaviour;

public class EventCard extends StoryCard{
    public EventCard(int id, String res, StoryBehaviour behaviour, String name){
        super(id,res,behaviour,name,Types.EVENT);

    }
}
