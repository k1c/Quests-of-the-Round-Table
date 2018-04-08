package com.mycompany.app.model;

public class AdventureCard extends Card{

	protected AdventureBehaviour behaviour;

	protected AdventureCard(int id,String res,AdventureBehaviour behaviour,String name,Types type){
		super(id,res,name,type);
		this.behaviour = behaviour;
		
	}

	public int getBattlePoints(GameBoard board){
		return behaviour.getBP(board);
	}

	public int getBids(GameBoard board){
		return behaviour.getBids(board);
	}

	public boolean freeBid(GameBoard board){
		return behaviour.isFreeBid(board);
	}

}
