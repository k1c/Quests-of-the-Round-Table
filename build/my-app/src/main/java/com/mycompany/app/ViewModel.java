package com.mycompany.app;

import com.mycompany.app.GameModel;
import com.mycompany.app.GameController;



public class ViewModel extends GameObserver{
	private final GameModel model;
	private final GameController contoller;
	public ViewModel(GameModel model, GameController contoller){
		this.model = model;
		this.contoller = contoller;

		model.registerObserver(this);
		
	}
	public void update(){

	}
}
