package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;

public class AllyCard extends AdventureCard{
	public AllyCard(int id, String res, AdventureBehaviour behaviour, String name){
		super(id,res,behaviour,name,Types.ALLY);

	}
}

