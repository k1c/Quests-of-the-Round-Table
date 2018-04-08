package com.mycompany.app.model.states_api.GameState;

import java.util.*;

import com.mycompany.app.model.Cards.Card;
import com.mycompany.app.model.DataStructures.Cycle;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public abstract class GameState extends AbstractState {
	public abstract void next();
	public abstract void decision(int playerId,boolean choice); 
	public abstract boolean play(int playerId, List<Card> cards);
	public abstract boolean quest(int playerId, TwoDimensionalArrayList<Card> quest);
	public abstract void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames);

	protected void changeState(GameState state,int playerId){
		model.currentPlayers = new Cycle(model.players,model.players.indexOf(playerId));
		model.log.gameStateAction(state,"state change","");
		model.discardState.check(playerId);
		// Check if discard state needs to be made
	}


}
