package com.mycompany.app.model;
import java.util.Comparator;
import java.util.Collections;

public class CourtCalledToCamelotEvent extends StoryBehaviour{

    public CourtCalledToCamelotEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        b.resetInPlay();
    }
}