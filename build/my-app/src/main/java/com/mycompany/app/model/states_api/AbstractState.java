package com.mycompany.app.model;

import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;

public abstract class AbstractState{
	protected GameModel model;
	protected GameStates state;

	public GameStates getState(){
		return state;
	}
}
