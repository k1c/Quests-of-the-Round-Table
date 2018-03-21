package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateQuestStageFoe extends GameState{
	public  GameStateQuestStageFoe (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		model.state = GameStates.STAGE_FOE;
	}

	public void next(){
	}
	public void decision(int player,boolean participate){
	}

	public boolean play(int playerID, List<Card> list){

		if(playerID != model.participants.current()){
			model.log.gameStateAction(model.state,"Invalid Player",model.board.findPlayer(playerID));
			return false;
		}


		boolean validSubmit = model.board.submitHand(playerID,list);

		if(validSubmit){
			model.log.gameStateAction(model.state,"valid Submission",model.board.findPlayer(playerID));
			model.participants.removeCurrent();
		}
		if(!validSubmit){
			model.log.gameStateAction(model.state,"invalid Submission",model.board.findPlayer(playerID));
			return false;
		}

		if(model.participants.size() <= 0 ){
			model.log.gameStateAction(model.state,"last participant",model.board.findPlayer(playerID));
			//changeState(GameStates.STAGE_END,playerID);
			model.gameState = new GameStateQuestStageEnd(this,playerID);
			//this.state = GameStates.STAGE_END;
		} else {
		    //changeState(GameStates.STAGE_FOE, this.participants.current());
		    model.gameState = new GameStateQuestStageFoe(this,playerID);
		}

		return true;
	}
	public boolean quest(int player, TwoDimensionalArrayList<Card> quest){
		return false;
	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
