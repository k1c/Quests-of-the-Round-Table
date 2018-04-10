package com.mycompany.app.model;
import java.util.*;

import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public class GameStateEventLogic extends GameState{

	public GameStateEventLogic(GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.EVENT_LOGIC;
	}

	public void next(){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}
		/*
		 * ACTION : Apply events logic to players
		 */
		model.log.gameStateAction(state,"Applying Event Logic","");
		model.board.applyStoryCardLogic(model.storyTurn.current());

		//this.state = GameStates.END_TURN;
		//changeState(GameStates.END_TURN,this.storyTurn.current());
		model.gameState = new GameStateEndTurn(this,model.storyTurn.current());
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
