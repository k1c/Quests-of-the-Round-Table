package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateTournamentStageEnd extends GameState{
	public GameStateTournamentStageEnd (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		model.state = GameStates.TOURNAMENT_STAGE_END;
	}

	public void next(){
		//check for winner
		model.board.completeTournamentStage();

		//check which round we are on
		boolean anotherRound = model.board.nextTournament();
		//TIE round 1 
		if(anotherRound){
			//clean up round 1, 
			//this.state = GameStates.TOURNAMENT_HANDLER;
			model.log.gameStateAction(model.state,"Tie Breaker","");
			model.gameState = new GameStateTournamentStageStart(this,model.board.getParticipants().get(0));
			//changeState(GameStates.TOURNAMENT_HANDLER,this.board.getParticipants().get(0));
		}
		//TIE round 2 
		if(!anotherRound){
			//clean up all	
			model.log.gameStateAction(model.state,"Winner Found","");
			model.gameState = new GameStateTournamentEnd(this,model.storyTurn.current());
			//changeState(GameStates.TOURNAMENT_END,this.storyTurn.current());
			//this.state = GameStates.TOURNAMENT_END;
		}
	}
	public void decision(int playerId,boolean choice){
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
