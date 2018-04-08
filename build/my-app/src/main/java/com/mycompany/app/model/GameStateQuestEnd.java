
package com.mycompany.app.model;
import java.util.*;

import com.mycompany.app.model.Card;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public class GameStateQuestEnd extends GameState{
	public GameStateQuestEnd (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.QUEST_END;
	}

	public void next(){
		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}
		model.log.gameStateAction(this,"Applying Quest Logic","");

		//apply story logic
		model.board.applyStoryCardLogic(model.questSponsor.current());

		//changeState(GameStates.END_TURN,questSponsor.current());
		model.gameState = new GameStateEndTurn(this,model.questSponsor.current());
		//this.state = GameStates.END_TURN;
	}
	public void decision(int player,boolean participate){
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
