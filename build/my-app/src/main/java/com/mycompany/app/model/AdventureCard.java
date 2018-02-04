package com.mycompany.app.model;

import com.mycompany.app.model.Card;

public abstract class AdventureCard extends Card{

	protected AdventureBehaviour behaviour;

	public AdventureCard(int id,String res,AdventureBehaviour behaviour,String name,Types type){
		super(id,res,name,type);
		this.behaviour = behaviour;
		
	}

	public int getBattlePoints(){
		return behaviour.getBP();
	}

	public int getBids(){
		return behaviour.getBids();
	}

	public boolean freeBid(){
		return behaviour.isFreeBid();
	}

}
