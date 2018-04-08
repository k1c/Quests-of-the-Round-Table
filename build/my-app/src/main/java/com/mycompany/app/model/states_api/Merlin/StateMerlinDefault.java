package com.mycompany.app.model.states_api.Merlin;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class StateMerlinDefault extends AbstractState{
	public StateMerlinDefault(GameModel model){
		this.state = GameStates.MERLIN;
		this.model = model;
	}

	public boolean check(int player, int stage){
		if(model.gameState.getState() != GameStates.QUEST_HANDLER){
			return false;
		}

		/* Check if stage 1 */

		/* Attempt to get Specified Stage Information */
		
		return false;
	}

}
