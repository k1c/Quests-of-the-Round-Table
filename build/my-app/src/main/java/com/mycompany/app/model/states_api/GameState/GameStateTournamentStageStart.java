package com.mycompany.app.model.states_api.GameState;
import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateTournamentStageStart extends GameState{
	public GameStateTournamentStageStart(GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);

		/* this needs to change */
		this.state = GameStates.TOURNAMENT_HANDLER;
	}

	public void next(){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}
		model.log.gameStateAction(this,"Beginning Tournament Stage","");
		/*
		 * some kind of quest logic here
		 */
		model.participants = new Cycle<Integer>(model.board.getParticipants(),0);


		//draw card
		model.board.beginEncounter();

		//this.state = GameStates.TOURNAMENT_STAGE;
		//changeState(GameStates.TOURNAMENT_STAGE,this.participants.current());
		


		model.gameState = new GameStateTournamentStage(this,model.participants.current());
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
