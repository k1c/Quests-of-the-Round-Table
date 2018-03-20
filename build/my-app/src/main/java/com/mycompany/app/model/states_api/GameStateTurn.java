package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateTurn extends GameState{
	public GameStateTurn(GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		model.state = GameStates.BEGIN_TURN;
	}

	public void next(){
		/*
		 * Action : Check if any players have won
		 */
		List<GenericPlayer> winners = model.board.winningPlayers();	
		if(winners.size() > 0){
			//this.state = GameStates.WINNERS;
			return;
		}

		/*
		 * Action: Draw from Story Deck
		 */
		model.board.drawFromStoryDeck(model.players.get(model.currentPlayer));
		Card card = model.board.getCurrentStoryCard();



		if (Card.Types.EVENT == card.type){
			//changeState(GameStates.EVENT_LOGIC,storyTurn.current());
			//model.state = GameStates.EVENT_LOGIC;
			model.gameState = new GameStateEventLogic(this,model.storyTurn.current());
		}

		if (Card.Types.QUEST == card.type){
			// start a cycle  with the sponsor as current player
			model.questSponsor = new Cycle<Integer>(model.players,model.storyTurn.current());
			//changeState(GameStates.SPONSOR_QUEST,questSponsor.current());
			//model.state = GameStates.SPONSOR_QUEST;
		}

		if (Card.Types.TOURNAMENT == card.type){
			model.participants = new Cycle<Integer>(model.players,model.storyTurn.current());
			//changeState(GameStates.PARTICIPATE_TOURNAMENT,storyTurn.current());
			//model.state = GameStates.PARTICIPATE_TOURNAMENT;
			model.gameState = new GameStateParticipateTournament(this,model.storyTurn.current());
		}
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
