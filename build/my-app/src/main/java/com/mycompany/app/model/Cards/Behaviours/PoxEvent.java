package com.mycompany.app.model.Cards.Behaviours;

import com.mycompany.app.model.Board.GameBoard;
import com.mycompany.app.model.Players.Player;

public class PoxEvent extends StoryBehaviour{

    public PoxEvent(int numShields) {
        super(numShields);

    }

    public void applyBehaviour(GameBoard b, int player) {
        for (Player p : b.players) {
            if (p.id() != player) {
                p.rank.addRemoveShields(this.numShields);
            }
        }
    }
}