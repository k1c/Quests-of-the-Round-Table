package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCardFactoryTest;
import junit.framework.TestCase;
import java.util.*;

public class CardLoaderTest extends TestCase{


	public CardLoaderTest(String name){
		super(name);
	}

	private int countId(List<Card> list, int id){
		int accumulator = 0;
		for(Card item : list)
			if (item.id == id)
				accumulator++;
		return accumulator;
	}

	public void test1() throws Exception{
		/*
		List<Card> cards = new ArrayList();
		cards.addAll(CardLoader.loadAdventureCards());


		assertEquals(125,cards.size());

		// Weapons 
		//Excalibur
		assertEquals(2,countId(cards,1));
		//Lance
		assertEquals(6,countId(cards,2));
		//Battle-Axe
		assertEquals(8,countId(cards,3));
		//Sword
		assertEquals(16,countId(cards,4));
		//Horse
		assertEquals(11,countId(cards,5));
		//Dagger
		assertEquals(6,countId(cards,6));



		// Foes 
		// Dragon
		assertEquals(1,countId(cards,7));
		// Giant
		assertEquals(2,countId(cards,8));
		// Mordred
		assertEquals(4,countId(cards,9));
		// Green Knight
		assertEquals(2,countId(cards,10));
		// Black Knight
		assertEquals(3,countId(cards,11));
		// Evil Knight
		assertEquals(6,countId(cards,12));
		// Saxon Knight
		assertEquals(8,countId(cards,13));
		// Robber Knight
		assertEquals(7,countId(cards,14));
		// Saxons
		assertEquals(5,countId(cards,15));
		// Boar
		assertEquals(4,countId(cards,16));
		// Thieves
		assertEquals(8,countId(cards,17));


		// Tests 

		assertEquals(2,countId(cards,18));
		assertEquals(2,countId(cards,19));
		assertEquals(2,countId(cards,20));
		assertEquals(2,countId(cards,21));

		// Allies 

		// Sir Galahad
		assertEquals(1,countId(cards,22));
		// Sir Lancelot
		assertEquals(1,countId(cards,23));
		// King Authur
		assertEquals(1,countId(cards,24));
		// Sir Tristan
		assertEquals(1,countId(cards,25));
		// Sir Pelinore
		assertEquals(1,countId(cards,26));
		//Sir Gawain
		assertEquals(1,countId(cards,27));
		// Sir Percival
		assertEquals(1,countId(cards,28));
		// Queen Guinevere
		assertEquals(1,countId(cards,29));
		// Queen Iseult
		assertEquals(1,countId(cards,30));
		// Merlin
		assertEquals(1,countId(cards,31));

		// Amours 
		assertEquals(8,countId(cards,32));


		List<Card> storyDeck = new ArrayList();
		storyDeck.addAll(CardLoader.loadStoryCards());

		// Story Cards 

		assertEquals(28,storyDeck.size());
		
		// Quests 

		// search for the holy grail
		assertEquals(1,countId(storyDeck,33));
		// the of the green knight
		assertEquals(1,countId(storyDeck,34));
		// search of the questing beast 
		assertEquals(1,countId(storyDeck,35));
		// defend the queen's honour
		assertEquals(1,countId(storyDeck,36));
		// rescue the fair maiden
		assertEquals(1,countId(storyDeck,37));
		// journey though the enchanted forest
		assertEquals(1,countId(storyDeck,38));
		// vanquish king Arthur's enemies
		assertEquals(2,countId(storyDeck,39));
		// slay the dragon
		assertEquals(1,countId(storyDeck,40));
		// boar hunt
		assertEquals(2,countId(storyDeck,41));
		// repel the Saxon raiders
		assertEquals(2,countId(storyDeck,42));
		*/

	}



}
