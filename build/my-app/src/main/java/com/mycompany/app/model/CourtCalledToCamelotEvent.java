package com.mycompany.app.model;

public class CourtCalledToCamelotEvent extends StoryBehaviour{

    public CourtCalledToCamelotEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        b.resetInPlay();
    }
}