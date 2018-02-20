package com.mycompany.app.model;
import java.util.Comparator;
import java.util.Collections;

public class PoxEvent extends StoryBehaviour{

    public PoxEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        for (Player p : b.players) {
            if (p.id() != player) {
                p.rank.addRemoveShields(-1);
            }
        }
    }
}