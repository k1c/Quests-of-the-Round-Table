package com.mycompany.app.model;

public class AllyCard extends AdventureCards{
	public AllyCard(int id, String res, AdventureBehaviour behaviour, String name){
		this.super(id,res,behaviour,name);
		this.type = Types.ALLY;
	}
}

