package com.mycompany.app;

import com.mycompany.app.GameModel;
import com.mycompany.app.GameObserver;
import com.mycompany.app.ViewModel;

public class GameController extends GameObserver{

	private final GameModel model;
	private final ViewModel view;

	public GameController(GameModel model){
		this.model = model;
		this.model.registerObserver(this);
		this.view = new ViewModel(model,this);
	}

	public void update(){

	}

}


