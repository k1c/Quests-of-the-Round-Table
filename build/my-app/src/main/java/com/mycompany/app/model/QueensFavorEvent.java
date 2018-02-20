package com.mycompany.app.model;
import java.util.Comparator;
import java.util.Collections;

public class QueensFavor{

    public QueensFavorEvent(int numShields){
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player){

        int minBP = Integer.MAX_VALUE;

        for(Player p : b.players){
            minBP = Math.min(minBP, b.player.rank.getBP());
        }
        for(Player p : b.players){
            if(p.rank.getBP() == minBP){
                b.drawFromAdventureDeck(p);
                b.drawFromAdventureDeck(p);
            }
        }

    }

}