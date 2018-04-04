package com.mycompany.app.model;
import java.util.Comparator;
import java.util.Collections;

public class ProsperityEvent extends StoryBehaviour{

    public ProsperityEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        for (Player p : b.players) {
            b.drawFromAdventureDeck(p);
            b.drawFromAdventureDeck(p);
        }
    }
}