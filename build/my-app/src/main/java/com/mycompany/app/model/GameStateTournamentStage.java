package com.mycompany.app.model;
import java.util.*;

import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public class GameStateTournamentStage extends GameState{
	public GameStateTournamentStage (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.TOURNAMENT_STAGE;
	}

	public void next(){
	}
	public void decision(int playerId,boolean choice){
	}
	public boolean play(int id, List<Card> hand){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return false;
		}

		if(id != model.participants.current()){
			model.log.gameStateAction(this,"Invalid Player",model.board.findPlayer(id));
			return false;
		}

		model.log.gameStateAction(this,"Participants : ",model.participants.items());
		if(model.participants.size() <= 0){
			model.log.gameStateAction(this,"All Players Have Played",model.board.findPlayer(id));
			return false;
		}

		// implement the tournament methods
		boolean validSubmit = model.board.submitHand(id,hand);

		if(!validSubmit){
			model.log.gameStateAction(this,"Incorrect Submission",model.board.findPlayer(id));
			return false;
		}

		if(validSubmit){
			model.log.gameStateAction(this,"Correct Submission",model.board.findPlayer(id));
			model.participants.removeCurrent();
		}

		
		if(model.participants.size() <= 0){
			model.log.gameStateAction(this,"Ending Stage",model.board.findPlayer(id));
			/*
			changeState(GameStates.TOURNAMENT_STAGE_END,id);
			*/
			model.gameState = new GameStateTournamentStageEnd(this,model.board.getParticipants().get(0));
		} else {
			//changeState(GameStates.TOURNAMENT_STAGE, this.participants.current());
			model.gameState = new GameStateTournamentStage(this,model.participants.current());
		}

		return true;
	}
	public boolean quest(int playerId, TwoDimensionalArrayList<Card> quest){
		return false;

	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
