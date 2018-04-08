
package com.mycompany.app.model.states_api.GameState;
import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateQuestStageEnd extends GameState{
	public  GameStateQuestStageEnd (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.STAGE_END;
	}

	public void next(){

		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}

		if(model.board.stageType(Card.Types.FOE)){
			model.log.gameStateAction(this,"Cleaning Up Foe Stage","");
			model.board.completeFoeStage();
		}
		if(model.board.stageType(Card.Types.TEST)){
			model.log.gameStateAction(this,"Cleaning Up Test Stage","");
			model.board.completeTestStage();

		}

		if(model.board.getParticipants().size() == 0){
			model.log.gameStateAction(this,"No More Participants","");
			model.gameState = new GameStateQuestEnd(this,model.questSponsor.current());
			//changeState(GameStates.QUEST_END,this.questSponsor.current());
		}

		//distribute cards
		else if(!model.board.nextStage()){
			model.log.gameStateAction(this,"Completed All Stages","");
			//changeState(GameStates.QUEST_END,this.questSponsor.current());
			model.gameState = new GameStateQuestEnd(this,model.questSponsor.current());
		}
		else{
			model.log.gameStateAction(this,"Next Stage","");
			//changeState(GameStates.QUEST_HANDLER,this.questSponsor.current());
			model.gameState = new GameStateQuestStageStart(this,model.questSponsor.current());
		}
	}
	public void decision(int player,boolean participate){
	}

	public boolean play(int playerID, List<Card> list){

		return false;
	}
	public boolean quest(int player, TwoDimensionalArrayList<Card> quest){
		return false;
	}
	public void newGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
	}
}
