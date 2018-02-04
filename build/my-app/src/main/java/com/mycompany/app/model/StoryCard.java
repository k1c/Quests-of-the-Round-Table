package com.mycompany.app.model;

import com.mycompany.app.model.Card;

public abstract class StoryCard extends Card{

    //protected StoryBehaviour behaviour;

    public StoryCard(int id, String res, String name, Types type, int numStages, int numBonusShields){
        super(id,res,name,type,numStages,numBonusShields);
        //this.behaviour = behaviour;

    }


}
