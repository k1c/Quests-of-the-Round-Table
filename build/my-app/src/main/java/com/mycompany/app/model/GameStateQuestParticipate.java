

package com.mycompany.app.model;
import java.util.*;

import com.mycompany.app.model.Card;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public class GameStateQuestParticipate extends GameState{
	public GameStateQuestParticipate(GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.PARTICIPATE_QUEST;
	}

	public void next(){
	}
	public void decision(int player,boolean participate){

		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}
		/*
		 * ACTION : add player to quest
		 */
		int currPlayer = model.participants.current();
		Player p = model.board.findPlayer(currPlayer);
		model.log.playerAction(p, "is deciding whether to participate in the Quest");

		if(player == currPlayer && participate){
			model.log.playerAction(p, "successfully participates in the Quest");
			model.board.addParticipant(model.participants.removeCurrent());
		}else if(player == currPlayer && !participate){
			model.log.playerAction(p, "declines to participate in the Quest");
			//this.board.addParticipant(this.participants.removeCurrent());
			model.participants.removeCurrent();
		}

		// change state
		if(model.participants.size() <= 0 && model.board.getParticipants().size()>0){
			//this.state = GameStates.QUEST_HANDLER;
			model.log.gameStateAction(this,"Starting Quest",model.board.findPlayer(player));
			//changeState(GameStates.QUEST_HANDLER, currPlayer);
			model.gameState = new GameStateQuestStageStart(this,model.questSponsor.current());
		}
		else if(model.participants.size() <= 0 && model.board.getParticipants().size() <= 0){
			//this.state = GameStates.QUEST_END;	
			model.log.gameStateAction(this,"Not Enough Players",model.board.findPlayer(player));
			//changeState(GameStates.QUEST_END, currPlayer);
			model.gameState = new GameStateQuestEnd(this,model.questSponsor.current());
		}
		//else if(model.state != GameStates.QUEST_HANDLER) {
		else{
			model.log.gameStateAction(this,"Next Player",model.board.findPlayer(player));
			//changeState(GameStates.PARTICIPATE_QUEST, this.participants.current());
			model.gameState = new GameStateQuestParticipate(this,model.participants.current());
		}

	}
	public boolean play(int id, List<Card> hand){
		return false;
	}
	public boolean quest(int player, TwoDimensionalArrayList<Card> quest){
		return false;
	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
