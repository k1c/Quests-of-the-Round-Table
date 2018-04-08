package com.mycompany.app.model.Cards.Behaviours;
import com.mycompany.app.model.GameBoard;

public class CourtCalledToCamelotEvent extends StoryBehaviour{

    public CourtCalledToCamelotEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        b.resetInPlay();
    }
}