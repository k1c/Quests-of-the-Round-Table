package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateQuestSponsor extends GameState{
	public GameStateQuestSponsor (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.SPONSOR_QUEST;
	}

	public void next(){

	}

	public void decision(int player,boolean sponsor){

		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}

		int currPlayer = model.questSponsor.current();
		Player p = model.board.findPlayer(currPlayer);
		model.log.playerAction(p,"is deciding whether to Sponsor the Quest");
		boolean willSponsor = (player == currPlayer && sponsor && model.board.playerCanSponsor(player)); 

		/*
		 * Verify that they can sponsor with current cards
		 */
		if(willSponsor){
			//this.state = GameStates.SPONSOR_SUBMIT;
			model.log.playerAction(p,"successfully sponsors the Quest");
			//changeState(GameStates.SPONSOR_SUBMIT,questSponsor.current());
		}
		else if(player == currPlayer && !sponsor){
			model.log.playerAction(p,"declines to sponsor the Quest");
			model.questSponsor.removeCurrent();
		}
		else if(player == currPlayer && !model.board.playerCanSponsor(player)){
			model.log.playerAction(p,"cannot sponsor the Quest");
			model.questSponsor.removeCurrent();	
		}


		/*
		 * Check if there are any more items
		 */
		if(model.questSponsor.size() <= 0){
			//this.state = GameStates.END_TURN;
			//changeState(GameStates.END_TURN, storyTurn.current());
			model.gameState = new GameStateEndTurn(this,model.storyTurn.current());
		//} else if (model.state != GameStates.SPONSOR_SUBMIT){
		} else if (willSponsor){
			//changeState(GameStates.SPONSOR_QUEST,questSponsor.current());
			model.gameState = new GameStateQuestSubmit(this,model.questSponsor.current());
		}
		else{
			model.gameState = new GameStateQuestSponsor(this,model.questSponsor.current());
		}
		
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
