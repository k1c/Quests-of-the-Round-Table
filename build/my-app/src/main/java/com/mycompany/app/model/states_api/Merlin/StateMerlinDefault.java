package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class StateMerlinDefault extends AbstractState{

	private Map<Integer,Integer> checked;

	public StateMerlinDefault(GameModel model){
		this.state = GameStates.MERLIN;
		this.model = model;
		reset();
	}

	public List<Card> check(int player, int stage){
		//Not in correct State
		if(model.gameState.getState() != GameStates.QUEST_HANDLER){
			return new ArrayList<Card>();
		}
		//Not at the beginning of the quest
		if(model.board.getQuestIndex() > 0){
			return new ArrayList<Card>();
		}
		if(!checked.replace(player,null,stage)){
			return new ArrayList<Card>();
		}


		/* Attempt to get Specified Stage Information */
		return model.board.stage(stage);
	}

	public void reset(){
		this.checked = new HashMap();
	}

}
