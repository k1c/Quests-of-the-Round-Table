package com.mycompany.app.model;

import com.mycompany.app.GameObserver;
import java.util.*;


public class GameModel{

	private ArrayList<GameObserver> observers;

	public GameModel(){
		observers = new ArrayList<GameObserver>();
		AdventureBehaviour b = new DefaultBehaviour(10,2);	
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
}
