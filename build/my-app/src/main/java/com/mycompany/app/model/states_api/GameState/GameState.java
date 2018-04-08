package com.mycompany.app.model;

import java.util.List;

public abstract class GameState extends com.mycompany.app.model.AbstractState {
	public abstract void next();
	public abstract void decision(int playerId,boolean choice); 
	public abstract boolean play(int playerId, List<Card> cards);
	public abstract boolean quest(int playerId, com.mycompany.app.model.TwoDimensionalArrayList<Card> quest);
	public abstract void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames);

	protected void changeState(GameState state,int playerId){
		model.currentPlayers = new com.mycompany.app.model.Cycle(model.players,model.players.indexOf(playerId));
		model.log.gameStateAction(state,"state change","");
		model.discardState.check(playerId);
		// Check if discard state needs to be made
	}


}
