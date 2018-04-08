
package com.mycompany.app.model;

import java.util.*;


import com.mycompany.app.model.Cards.Card;
import com.mycompany.app.model.DataStructures.Cycle;

public abstract class DiscardState extends AbstractState {
	public abstract boolean discard(int playerId, List<Card> cards);
	public abstract boolean play(int playerId, List<Card> cards);
	public abstract boolean check(int id);

	protected void changeState(GameState state, int playerId){
		model.currentPlayers = new Cycle(model.players,model.players.indexOf(playerId));
		model.log.gameStateAction(this,"state change","");
		// Check if discard state needs to be made
	}

}
