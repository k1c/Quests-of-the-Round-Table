package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateParticipateTournament extends GameState{
	public GameStateParticipateTournament(GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.PARTICIPATE_TOURNAMENT;
	}

	public void next(){
	}
	public void decision(int playerId,boolean choice){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}

		int currentPlayer = model.participants.current();
		Player p = model.board.findPlayer(currentPlayer);

		model.log.playerAction(p,"is deciding whether to participate in the Tournament");

		if(playerId == currentPlayer && choice){
			model.log.gameStateAction(this,"Participates",model.board.findPlayer(playerId));
			model.board.addParticipant(model.participants.removeCurrent());
		}else if(playerId == currentPlayer && !choice){
			model.log.gameStateAction(this,"Does Not Participate",model.board.findPlayer(playerId));
			model.participants.removeCurrent();
		}

		if(model.participants.size() <= 0 && model.board.getParticipants().size() > 0){
			model.log.gameStateAction(this,"Beginning Tournament",model.board.findPlayer(playerId));
			/*
			changeState(GameStates.TOURNAMENT_HANDLER,currentPlayer);
			*/
			model.gameState = new GameStateTournamentStageStart(this,currentPlayer);
		}
		else if(model.participants.size() <= 0 && model.board.getParticipants().size() <= 0){
			model.log.gameStateAction(this,"Not Enough Participants",model.board.findPlayer(playerId));
			/*
			changeState(GameStates.END_TURN,this.storyTurn.current());
			*/
			//this.state = GameStates.END_TURN;
			
			model.gameState = new GameStateEndTurn(this,model.storyTurn.current());
		}
		else{
			model.log.gameStateAction(this,"Next Participant",model.board.findPlayer(model.participants.current()));
			this.model.gameState = new GameStateParticipateTournament(this,model.participants.current());
		}

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
