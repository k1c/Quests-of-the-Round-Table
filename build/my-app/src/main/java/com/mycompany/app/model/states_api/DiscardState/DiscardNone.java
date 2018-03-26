
package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class DiscardNone extends DiscardState{
	public DiscardNone(DiscardState state){
		this.model = state.model;
		this.state = GameStates.DISCARD_NONE;
		/* change state */
		/*
		 * model.saved_idex 
		 */
		model.currentPlayers = new Cycle(model.players,model.savedIndex);
	}
	public DiscardNone(GameModel model){
		this.model = model;
		this.state = GameStates.DISCARD_NONE;
	}

	public boolean discard(int playerId, List<Card> cards){
		return false;
	}
	public boolean play(int playerId, List<Card> cards){
		return false;
	}

	public boolean check(int playerId){
		//get players over limit
		List<Integer> playersOverLimit = model.board.playersToDiscard();

		// Discard
		if(playersOverLimit.size() > 0){

			model.log.gameStateAction(this,"Discard Found",playersOverLimit);

			model.savedIndex = model.players.indexOf(playerId);
			model.discard = new Cycle(playersOverLimit,0);
			model.discardState = new DiscardDefault(this);
			return true;
		}

		return false;

	}

}
