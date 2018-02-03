package com.mycompany.app.model;

import com.mycompany.app.model.*;

public abstract class AdventureCards extends Card{

	protected AdventureBehavior behavior;

	public AdventureCards(int id,String res,AdventureBehaviour behavior,String name){
		this.super(id,res,name);
		this.behavior = behavior;
		
	}
}
