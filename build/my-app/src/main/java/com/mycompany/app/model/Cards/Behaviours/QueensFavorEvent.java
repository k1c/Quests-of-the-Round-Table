package com.mycompany.app.model.Cards.Behaviours;
import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.Player;

public class QueensFavorEvent extends StoryBehaviour{

    public QueensFavorEvent(int numShields){
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player){

        int minBP = Integer.MAX_VALUE;

        for(Player p : b.players){
            minBP = Math.min(minBP, p.rank.getBP());
        }
        for(Player p : b.players){
            if(p.rank.getBP() == minBP){
                b.drawFromAdventureDeck(p);
                b.drawFromAdventureDeck(p);
            }
        }

    }

}