package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCards;

public class FoeCard extends AdventureCards{

	public FoeCard(int id, String res, AdventureBehaviour behaviour, String name){
		super(id,res,behaviour,name,Types.FOE);
	}
}

