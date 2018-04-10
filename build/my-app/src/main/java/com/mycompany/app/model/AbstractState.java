package com.mycompany.app.model;

public abstract class AbstractState{
	protected GameModel model;
	protected GameStates state;

	public GameStates getState(){
		return state;
	}
}
