package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateTournamentEnd extends GameState{
	public GameStateTournamentEnd (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.TOURNAMENT_END;
	}

	public void next(){

		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}

		model.log.gameStateAction(this,"Applying Tournament Logic","");
		model.board.applyStoryCardLogic(-1);
		model.gameState = new GameStateEndTurn(this,model.storyTurn.current());

		//changeState(GameStates.END_TURN,this.storyTurn.current());
		//this.state = GameStates.END_TURN;
	}
	public void decision(int playerId,boolean choice){
	}
	public boolean play(int id, List<Card> hand){

		return false;
	}
	public boolean quest(int playerId, TwoDimensionalArrayList<Card> quest){
		return false;

	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
