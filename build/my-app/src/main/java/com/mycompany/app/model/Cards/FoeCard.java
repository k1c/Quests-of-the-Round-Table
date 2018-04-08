package com.mycompany.app.model.Cards;

import com.mycompany.app.model.Cards.Behaviours.AdventureBehaviour;

public class FoeCard extends AdventureCard{

	public FoeCard(int id, String res, AdventureBehaviour behaviour, String name){
		super(id,res,behaviour,name,Types.FOE);
	}
}

