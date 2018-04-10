package com.mycompany.app.model;

public class StateMerlinDefault extends AbstractState {
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
