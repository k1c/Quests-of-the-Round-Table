package com.mycompany.app.model.Cards;

import com.mycompany.app.model.Cards.Behaviours.AdventureBehaviour;

public class AllyCard extends AdventureCard{
	public AllyCard(int id, String res, AdventureBehaviour behaviour, String name){
		super(id,res,behaviour,name,Types.ALLY);

	}
}

