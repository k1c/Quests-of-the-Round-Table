package com.mycompany.app.model.Cards.Behaviours;

import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.Player;

public class ChivalrousDeedEvent extends StoryBehaviour{

    public ChivalrousDeedEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        int minBP = Integer.MAX_VALUE;
        int minShields = Integer.MAX_VALUE;

        for(Player p : b.players){
            minBP = Math.min(minBP, p.rank.getBP());
        }

        for(Player p: b.players){
            if(p.rank.getBP() == minBP){
                minShields = Math.min(minShields, p.rank.getShields());
            }
        }

        for(Player p : b.players){
            if(p.rank.getBP() == minBP && p.rank.getShields() == minShields){
                p.rank.addRemoveShields(this.numShields);
            }
        }
    }
}