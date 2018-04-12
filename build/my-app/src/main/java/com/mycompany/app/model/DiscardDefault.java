
package com.mycompany.app.model;

import com.mycompany.app.model.DataStructures.Cycle;

import java.util.List;

public class DiscardDefault extends DiscardState{
	public DiscardDefault(DiscardState state){
		this.model = state.model;
		this.state = GameStates.DISCARD;
		model.currentPlayers = new Cycle(model.players,model.players.indexOf(model.discard.current()));

	}

	public boolean discard(int playerId, List<Card> discards){
		if (model.discard.current() != playerId) {
			model.log.gameStateAction(this.state, "Incorrect Player", model.board.findPlayer(playerId));
			return false;
		}

		boolean valid = model.board.discardHand(playerId, discards);
		if (!valid) {
			model.log.gameStateAction(this.state, "Invalid Discard", model.board.findPlayer(playerId));
			return false;
		}

		model.discard.removeCurrent();

		if (model.discard.size() <= 0){
			model.discardState = new DiscardNone(this);
		}else{
			model.currentPlayers = new Cycle(model.players,model.players.indexOf(model.discard.current()));
		}

		return true;
	}
	public boolean play(int playerId, List<Card> cards){
		return false;
	}

	public boolean check(int playerId){
		return true;
	}

}
