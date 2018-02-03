package com.mycompany.app.model;

import com.mycompany.app.model.*;

public abstract class AdventureCards extends Card{

	protected AdventureBehaviour behaviour;

	public AdventureCards(int id,String res,AdventureBehaviour behaviour,String name,Types type){
		this.super(id,res,name,type);
		this.behaviour = behaviour;
		
	}
}
