package com.mycompany.app.model;


import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.AdventureCardFactory;
import java.util.*;


public class CardLoader{
	static public List<AdventureCard> loadAdventureCards (){
		List<AdventureCard> cards = new ArrayList<AdventureCard>();
		AdventureCardFactory F = new AdventureCardFactory();

		//WEAPONS

		for(int i = 0; i < 2; i++)
			cards.add(F.defaultWeapon(1,"W Excalibur.jpg","Excalibur",30));

		for(int i = 0; i < 6; i++)
			cards.add(F.defaultWeapon(2,"W Lance.jpg","Lance",20));

		for(int i = 0; i < 8; i++)
			cards.add(F.defaultWeapon(3,"W Battle-Ax.jpg","Battle-ax",15));

		for(int i = 0; i < 16; i++)
			cards.add(F.defaultWeapon(4,"W Sword.jpg","Sword",10));

		for(int i = 0; i < 11; i++)
			cards.add(F.defaultWeapon(5,"W Horse.jpg","Horse",10));

		for(int i = 0; i < 6; i++)
			cards.add(F.defaultWeapon(6,"W Dagger.jpg","Dagger",5));


	
		for(int i = 0; i < 15; i++)
			cards.add(F.defaultAlly(2,"A King Arthur.jpg","King Arthur",10,2));
	
		for(int i = 0; i < 15; i++)
			cards.add(F.defaultFoe(3,"F Black Knight.jpg","Black Knight",35));

		for(int i = 0; i < 8; i++)
			cards.add(F.defaultAmour(99,"Amour.jpg","Amour"));
		
		return cards;
	}




}
