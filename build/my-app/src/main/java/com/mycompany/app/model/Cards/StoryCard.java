package com.mycompany.app.model.Cards;

import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.Cards.Behaviours.StoryBehaviour;

public class StoryCard extends Card{

    protected StoryBehaviour behaviour;

    protected StoryCard(int id, String res, StoryBehaviour behaviour, String name, Types type){
        super(id,res,name,type);
        this.behaviour = behaviour;

    }

    public int getNumStages(){
        return behaviour.getNumShields();
    }

    public void apply(GameBoard board, int player){
	    this.behaviour.applyBehaviour(board, player);
    }


}

