
package com.mycompany.app;

public class GameModel{

	private Observer observer = null;

	public GameModel(){
	}
	public void notify(){
		observer.update()
	}
}
