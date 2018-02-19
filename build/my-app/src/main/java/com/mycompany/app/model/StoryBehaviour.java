package com.mycompany.app.model;

import java.util.ArrayList;

public abstract class StoryBehaviour {

    protected int numShields; //On a Quest = number of stages, On a Tournament = number of bonus shields
    protected ArrayList<Integer> specified;

    public StoryBehaviour(int numShields){
        this.numShields = numShields;
    };

    public int getNumShields(){
        return this.numShields;
    }

    public abstract void applyBehaviour(GameBoard b, int player);

}



