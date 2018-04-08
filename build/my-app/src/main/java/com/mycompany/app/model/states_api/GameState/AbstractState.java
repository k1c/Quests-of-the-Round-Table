package com.mycompany.app.model.states_api.GameState;

import com.mycompany.app.model.states_api.GameState.GameModel;
import com.mycompany.app.model.states_api.GameStates;

public abstract class AbstractState{
	protected GameModel model;
	protected GameStates state;

	public GameStates getState(){
		return state;
	}
}
