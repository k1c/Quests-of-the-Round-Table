package com.mycompany.app.model;

import com.mycompany.app.GameObserver;
import java.util.*;


public class GameModel{

	private ArrayList<GameObserver> observers;

	public GameModel(){
		observers = new ArrayList<GameObserver>();

		AdventureCards KA = new AllyCard(1, "ally1.png", new DefaultBehaviour(10, 2), "King Arthur");
		//System.out.println(c.getBattlePoints());
		//System.out.println(c.type);

		AdventureCards c = AdventureCardFactory.defaultAlly(1, "ally1.png", "King Arthur", 10, 2);
		System.out.println(c.getBattlePoints());
		System.out.println(c.type);


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

