package com.mycompany.app.model.Cards.Behaviours;
import com.mycompany.app.model.Board.GameBoard;

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