package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCards;

public class AllyCard extends AdventureCards{
	public AllyCard(int id, String res, AdventureBehaviour behaviour, String name){
		super(id,res,behaviour,name,Types.ALLY);

	}
}

