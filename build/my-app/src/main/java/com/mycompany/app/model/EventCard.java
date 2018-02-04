package com.mycompany.app.model;

import com.mycompany.app.model.StoryCard;

public class EventCard extends StoryCard{
    public EventCard(int id, String res, String name){
        super(id,res,name,Types.EVENT,0,0);

    }
}
