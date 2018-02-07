package com.mycompany.app.model;

import com.mycompany.app.model.Card;

public class StoryCard extends Card{

    protected StoryBehaviour behaviour;

    protected StoryCard(int id, String res, StoryBehaviour behaviour, String name, Types type){
        super(id,res,name,type);
        this.behaviour = behaviour;

    }


}
