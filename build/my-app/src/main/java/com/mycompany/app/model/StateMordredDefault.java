package com.mycompany.app.model;


import com.mycompany.app.model.Cards.Card;

public class StateMordredDefault extends AbstractState{
	public StateMordredDefault(GameModel model){
		this.state = GameStates.MORDRED;
		this.model = model;
	}

	public boolean check(int player,int target,Card card){
		//Check for correct state
		switch(model.gameState.getState()){
			case QUEST_HANDLER : break;
			case STAGE_FOE: break;
			case STAGE_TEST: break;
			case STAGE_END: break;
			case TOURNAMENT_HANDLER: break;
			case TOURNAMENT_STAGE: break;
			case TOURNAMENT_STAGE_END: break;
			default: return false;
		}

		/* Check if stage 1 */

		/* Attempt to get Specified Stage Information */
		
		return false;
	}

}
