package com.mycompany.app.model;


import com.mycompany.app.GameObserver;
import com.mycompany.app.model.*;
import com.mycompany.app.model.GameStates;
import java.util.*;


public class GameModel{

	private ArrayList<GameObserver> observers;
	private GameStates state;
	private int numberOfPlayers;
	private int currentPlayer;
	private int turn;

	public GameModel(){
		observers = new ArrayList<GameObserver>();
	}

	public void registerObserver(GameObserver o){
		this.observers.add(o);
	}

	public void deregisterObserver(GameObserver o){
		this.observers.remove(o);
	}

	public void updateObservers(){
		for(Iterator<GameObserver> i = this.observers.iterator(); i.hasNext();){
			i.next().update();
		}
	}


	public void nextTurn(){
		if (this.state != GameStates.BEGIN_TURN)
			return;

		this.currentPlayer = (this.currentPlayer + 1) % numberOfPlayers;

		/*
		 * Action: Draw from Story Deck
		 */

		
		if (Card.Types.EVENT == Card.Types.EVENT){
			this.state = GameStates.DRAW_EVENT;
		}
		else if (Card.Types.QUEST == Card.Types.QUEST){
			this.state = GameStates.DRAW_QUEST;
		}
		else if (Card.Types.TOURNAMENT == Card.Types.TOURNAMENT){
			this.state = GameStates.DRAW_TOURNAMENT;
		}

		this.updateObservers();

	}

}

