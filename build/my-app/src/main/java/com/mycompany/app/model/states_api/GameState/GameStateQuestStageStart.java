


package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateQuestStageStart extends GameState{
	public GameStateQuestStageStart (GameState state,int currentPlayer){
		this.model = state.model;
		changeState(this,currentPlayer);
		this.state = GameStates.QUEST_HANDLER;
	}

	public void next(){

		if(model.discardState.getState() != GameStates.DISCARD_NONE){
			return;
		}

		model.participants = new Cycle<Integer>(model.board.getParticipants(),0);


		//draw card
		model.board.beginEncounter();

		if(model.board.stageType(Card.Types.FOE)){
			//this.state = GameStates.STAGE_FOE;
			model.log.gameStateAction(this,"Foe Stage Next","");
			//changeState(GameStates.STAGE_FOE,this.participants.current());
			model.gameState = new GameStateQuestStageFoe(this,model.participants.current());
		}
		if(model.board.stageType(Card.Types.TEST)){
			//this.state = GameStates.STAGE_TEST;
			model.log.gameStateAction(this.state,"Test Stage Next","");
			//changeState(GameStates.STAGE_TEST,this.participants.current());
			model.gameState = new GameStateQuestStageTest(this,model.participants.current());
		}
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
