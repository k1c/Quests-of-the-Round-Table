package com.mycompany.app.model;
import java.util.Comparator;
import java.util.Collections;

public class ChivalrousDeedEvent {

    public ChivalrousDeedEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {

        int minBP = Integer.MAX_VALUE;
        int minShields = Integer.MAX_VALUE;

        for(Player p : b.players){
            minBP = Math.min(minBP, b.player.rank.getBP());
        }

        for(Player p: b.players){
            if(p.rank.getBP() == minBP){
                minShields = Math.min(minShields, p.rank.getShields());
            }
        }

        for(Player p : b.players){
            if(p.rank.getBP() == minBP && p.rank.getShields() == minShields){
                p.rank.addRemoveShields(3);
            }
        }
    }
}