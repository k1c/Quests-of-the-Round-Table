
package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateQuestSubmit extends GameState{
	public GameStateQuestSubmit (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.SPONSOR_SUBMIT;
	}

	public void next(){
	}
	public void decision(int player,boolean sponsor){
	}
	public boolean play(int id, List<Card> hand){

		return false;
	}
	public boolean quest(int player, TwoDimensionalArrayList<Card> quest){
		/*
		 * verify that it is a valid quest
		 */
		if(model.questSponsor.current() == player && model.board.submitQuest(quest,player)){
			model.log.gameStateAction(this,"Valid Submission",model.board.findPlayer(player));

			model.participants = new Cycle(model.players,model.players.indexOf(model.questSponsor.current()));
			model.participants.removeCurrent();
      
			//changeState(GameStates.PARTICIPATE_QUEST,this.participants.current());
			model.gameState = new GameStateQuestParticipate(this,model.participants.current());
			return true;
			//this.state = GameStates.PARTICIPATE_QUEST;
		}
		model.log.gameStateAction(this,"Invalid Submission",model.board.findPlayer(player));

		return false;

	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
