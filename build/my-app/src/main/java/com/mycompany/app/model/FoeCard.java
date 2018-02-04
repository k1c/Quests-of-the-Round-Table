package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;

public class FoeCard extends AdventureCard{

	public FoeCard(int id, String res, AdventureBehaviour behaviour, String name){
		super(id,res,behaviour,name,Types.FOE);
	}
}

