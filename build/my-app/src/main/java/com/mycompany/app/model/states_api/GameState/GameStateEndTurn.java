package com.mycompany.app.model.states_api.GameState;
import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Cards.Card;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;
import com.mycompany.app.model.states_api.GameStates;

public class GameStateEndTurn extends GameState{

	public GameStateEndTurn(GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.END_TURN;
	}

	public void next(){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}
		model.log.gameStateAction(state,"End Turn","");
		model.turn++;
		model.currentPlayer = model.storyTurn.next();

		//this.state = GameStates.BEGIN_TURN;
		//changeState(GameStates.BEGIN_TURN,this.storyTurn.current());
		model.gameState = new GameStateTurn(this,model.storyTurn.current());
	}
	public void decision(int playerId,boolean choice){

	}
	public boolean play(int playerId, List<Card> cards){
		return false;
	}
	public boolean quest(int playerId, TwoDimensionalArrayList<Card> quest){
		return false;
	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
