

package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public class GameStateInit extends GameState{
	public GameStateInit(GameState state){
		this.model = state.model;
	}

	public GameStateInit(GameModel model){
		this.model = model;
	}

	public void next(){

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

		String[] shieldImages = {"Shield Blue.png", "Shield Red.png", "Shield Green.png", "Shield Purple.png"};
		List<HumanPlayer> h = new ArrayList();
		List<AbstractAI> a = new ArrayList();

		model.turn = 0;

		int num = Math.min(numHumans + ai_type1 + ai_type2, 4);

		for(int i = 0; i < num; i++){
			if(i < numHumans){
				h.add(new HumanPlayer(humanNames[i],shieldImages[i]));			
			}
			else if(i < numHumans + ai_type1){
				a.add(new Strategy1AI(String.format("AI%d", i+1-numHumans),shieldImages[i]));			
			}
			else if(i < numHumans + ai_type1 + ai_type2){
				a.add(new Strategy1AI(String.format("AI%d", i+1-numHumans-ai_type1),shieldImages[i]));			
			}
		}

		model.board.initRig(a,h,CardLoader.loadAdventureCards(),CardLoader.loadStoryCards(),true,true,true);
			

		model.players = model.board.getPlayerIds();		
		model.storyTurn = new Cycle<Integer>(model.players,0);
		
		model.gameState = new GameStateTurn(this,model.storyTurn.current());
	}
}
