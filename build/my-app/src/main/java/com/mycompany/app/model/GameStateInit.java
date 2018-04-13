

package com.mycompany.app.model;
import java.util.*;

import com.mycompany.app.model.Card;
import com.mycompany.app.model.DataStructures.Cycle;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

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
}
