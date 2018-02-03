package com.mycompany.app.model;

import com.mycompany.app.model.*;

public abstract class AdventureCards extends Card{

	protected AdventureBehaviour behaviour;

	public AdventureCards(int id,String res,AdventureBehaviour behaviour,String name){
		this.super(id,res,name);
		this.behaviour = behaviour;
		
	}
}
