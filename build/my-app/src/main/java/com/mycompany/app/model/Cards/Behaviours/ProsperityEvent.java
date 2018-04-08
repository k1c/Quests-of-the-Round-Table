package com.mycompany.app.model.Cards.Behaviours;
import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.Player;

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