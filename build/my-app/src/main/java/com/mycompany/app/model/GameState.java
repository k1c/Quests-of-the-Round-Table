package com.mycompany.app.model;

import com.mycompany.app.model.DataStructures.Cycle;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.util.List;
import java.util.*;

public abstract class GameState extends AbstractState {
	public abstract void next();
	public abstract void decision(int playerId,boolean choice);
	public abstract boolean play(int playerId, List<Card> cards);
	public abstract boolean quest(int playerId, TwoDimensionalArrayList<Card> quest);

	public void newGame(GameBoard b){
		model.board = b;
		model.turn = 0;
		model.storyTurn = new Cycle<Integer>(model.players,0);
		model.gameState = new GameStateTurn(this,model.storyTurn.current());
		return;
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

	protected void changeState(GameState state,int playerId){
		model.currentPlayers = new Cycle(model.players,model.players.indexOf(playerId));
		model.log.gameStateAction(state,"state change","");
		model.discardState.check(playerId);
		// Check if discard state needs to be made
	}
}
