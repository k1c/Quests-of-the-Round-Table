
package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateQuestStageTest extends GameState{
	public  GameStateQuestStageTest (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		model.state = GameStates.STAGE_TEST;
	}

	public void next(){
	}
	/* give up */
	public void decision(int id,boolean giveUp){


		if(model.participants.current() != id){
			model.log.gameStateAction(model.state,"Invalid Player",model.board.findPlayer(id));
			return;
		}

		if(!giveUp){
			model.log.gameStateAction(model.state,"Keep Fighting Youngling!",model.board.findPlayer(id));
			return;
		}

		model.log.gameStateAction(model.state,"Given Up",model.board.findPlayer(id));
		model.board.giveUp(id);
		model.participants.removeCurrent();

		if(model.board.checkTestWinner()){
			model.log.gameStateAction(model.state,"Someone Has Won",model.board.findPlayer(id));
			//this.state = GameStates.STAGE_END;
			//changeState(GameStates.STAGE_END,this.participants.current());
			model.gameState = new GameStateQuestStageEnd(this,model.participants.current());
		} else {
			//changeState(GameStates.STAGE_TEST, this.participants.current());
			model.gameState = new GameStateQuestStageTest(this,model.participants.current());
		}
	}

	public boolean play(int playerID, List<Card> list){

		if(playerID != model.participants.current()){
			model.log.gameStateAction(model.state,"Invalid Player",model.board.findPlayer(playerID));
			return false;
		}

		boolean validSubmit = model.board.submitBids(playerID,list);

		if(!validSubmit) {
			model.log.gameStateAction(model.state,"Invalid Bid",model.board.findPlayer(playerID));
			//changeState(GameStates.STAGE_TEST, this.participants.current());
			model.gameState = new GameStateQuestStageTest(this,model.participants.current());
			return false;
		}

		if(validSubmit && model.board.checkTestWinner()){
			//this.state = GameStates.STAGE_END;
			model.log.gameStateAction(model.state,"Wins Bid",model.board.findPlayer(playerID));
			//changeState(GameStates.STAGE_END,this.participants.current());
			model.gameState = new GameStateQuestStageEnd(this,model.participants.current());
		} else if(validSubmit && !model.board.checkTestWinner()){
			model.log.gameStateAction(model.state,"Passes Bid",model.board.findPlayer(playerID));
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
