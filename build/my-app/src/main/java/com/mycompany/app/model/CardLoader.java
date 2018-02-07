package com.mycompany.app.model;


import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.AdventureCardFactory;
import java.util.*;


public class CardLoader{
	static public ArrayList<AdventureCard> loadAdventureCards (){
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		AdventureCardFactory F = new AdventureCardFactory();


		for(int i = 0; i < 15; i++)
			cards.add(F.defaultWeapon(1,"W Dagger.jpg","Dagger",5));
	
		for(int i = 0; i < 15; i++)
			cards.add(F.defaultAlly(2,"A King Arthur.jpg","King Arthur",10,2));
	
		for(int i = 0; i < 15; i++)
			cards.add(F.defaultFoe(3,"F Black Knight.jpg","Black Knight",35));
		
	
		for(int i = 0; i < 15; i++)
			cards.add(F.defaultAmour(4,"Amour.jpg","Love"));
		
		return  cards;
	}

}
