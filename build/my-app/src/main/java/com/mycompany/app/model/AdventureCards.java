package com.mycompany.app.model;

import com.mycompany.app.model.*;

public abstract class AdventureCards extends Card{

	protected AdventureBehavior behavior;

	public AdventureCards(int id,String res, AdventureCards behavior){
		this.super(id,res);
		this.behavior = behavior;
		
	}
}
