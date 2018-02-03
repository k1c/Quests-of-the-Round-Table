package com.mycompany.app.model;

public class FoeCard extends AdventureCards{

	public FoeCard(int id, String res, AdventureBehaviour behaviour, String name){
		this.super(id,res,behaviour,name);
		this.type = Types.FOE;
	}
}

