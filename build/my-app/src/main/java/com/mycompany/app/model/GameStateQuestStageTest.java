
package com.mycompany.app.model;
import java.util.*;

import com.mycompany.app.model.Card;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public class GameStateQuestStageTest extends GameState{
	public  GameStateQuestStageTest (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.STAGE_TEST;
	}

	public void next(){
	}
	/* give up */
	public void decision(int id,boolean giveUp){

		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}

		if(model.participants.current() != id){
			model.log.gameStateAction(this,"Invalid Player",model.board.findPlayer(id));
			return;
		}

		if(!giveUp){
			model.log.gameStateAction(this,"Keep Fighting Youngling!",model.board.findPlayer(id));
			return;
		}

		model.log.gameStateAction(this,"Given Up",model.board.findPlayer(id));
		model.board.giveUp(id);
		model.participants.removeCurrent();

		if(model.board.checkTestWinner()){
			model.log.gameStateAction(this,"Someone Has Won",model.board.findPlayer(id));
			//this.state = GameStates.STAGE_END;
			//changeState(GameStates.STAGE_END,this.participants.current());
			model.gameState = new GameStateQuestStageEnd(this,model.participants.current());
		} else {
			//changeState(GameStates.STAGE_TEST, this.participants.current());
			model.gameState = new GameStateQuestStageTest(this,model.participants.current());
		}
	}

	public boolean play(int playerID, List<Card> list){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return false;
		}

		if(playerID != model.participants.current()){
			model.log.gameStateAction(this,"Invalid Player",model.board.findPlayer(playerID));
			return false;
		}

		boolean validSubmit = model.board.submitBids(playerID,list);

		if(!validSubmit) {
			model.log.gameStateAction(this,"Invalid Bid",model.board.findPlayer(playerID));
			//changeState(GameStates.STAGE_TEST, this.participants.current());
			model.gameState = new GameStateQuestStageTest(this,model.participants.current());
			return false;
		}

		if(validSubmit && model.board.checkTestWinner()){
			//this.state = GameStates.STAGE_END;
			model.log.gameStateAction(this,"Wins Bid",model.board.findPlayer(playerID));
			//changeState(GameStates.STAGE_END,this.participants.current());
			model.gameState = new GameStateQuestStageEnd(this,model.participants.current());
		} else if(validSubmit && !model.board.checkTestWinner()){
			model.log.gameStateAction(this,"Passes Bid",model.board.findPlayer(playerID));
			model.participants.next();
			//changeState(GameStates.STAGE_TEST, this.participants.current());
			model.gameState = new GameStateQuestStageTest(this,model.participants.current());
		}

		return true;
	}
	public boolean quest(int player, TwoDimensionalArrayList<Card> quest){
		return false;
	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
