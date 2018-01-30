
package com.mycompany.app;

import com.mycompany.app.Observer;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;


public class GameModel{

	private ArrayList<Observer> observers;

	public GameModel(){
		observers = new ArrayList<Observer>();
	}

	public void registerObserver(Observer o){
		this.observer = o;
	}

	public void deregisterObserver(){
		this.observer = null;
	}

	public void notify(){
		for(Iterator<Observer> i = observers.Iterator(); .hasNext();){
			Observer item = item.next();
			item.notify()
		}
	}
}
