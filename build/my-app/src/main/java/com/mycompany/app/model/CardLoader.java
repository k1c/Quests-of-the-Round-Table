package com.mycompany.app.model;


import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.AdventureCardFactory;
import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class CardLoader{
	static public List<AdventureCard> loadAdventureCards () {
		List<AdventureCard> advCards = new ArrayList<AdventureCard>();
		AdventureCardFactory F = new AdventureCardFactory();

		List<AdventureCard> L = new ArrayList<AdventureCard>();

		L.addAll(F.createCardList(AdventureCardFactory.Types.EXCALIBUR, 2));
		L.addAll(F.createCardList(AdventureCardFactory.Types.LANCE, 6));
		L.addAll(F.createCardList(AdventureCardFactory.Types.BATTLE_AX, 8));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SWORD, 16));
		L.addAll(F.createCardList(AdventureCardFactory.Types.HORSE, 11));
		L.addAll(F.createCardList(AdventureCardFactory.Types.DAGGER, 6));

		L.addAll(F.createCardList(AdventureCardFactory.Types.DRAGON, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.GIANT, 2));
		//L.addAll(F.createCardList(AdventureCardFactory.Types.MORDRED, 4));
		L.addAll(F.createCardList(AdventureCardFactory.Types.GREEN_KNIGHT, 2));
		L.addAll(F.createCardList(AdventureCardFactory.Types.BLACK_KNIGHT, 3));
		L.addAll(F.createCardList(AdventureCardFactory.Types.EVIL_KNIGHT, 6));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SAXON_KNIGHT, 8));
		L.addAll(F.createCardList(AdventureCardFactory.Types.ROBBER_KNIGHT, 7));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SAXONS, 5));
		L.addAll(F.createCardList(AdventureCardFactory.Types.BOAR, 4));
		L.addAll(F.createCardList(AdventureCardFactory.Types.THIEVES, 8));

		//L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_VALOR, 2));
		//L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_TEMPTATION, 2));
		//L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_MORGAN_LE_FAY, 2));
		//L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_THE_QUESTING_BEAST, 2));

		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_GALAHAD, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_LANCELOT, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.KING_ARTHUR, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_TRISTAN, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.KING_PELLINORE, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_GAWAIN, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_PERCIVAL, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.QUEEN_GUINEVERE, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.QUEEN_ISEULT, 1));
		//L.addAll(F.createCardList(AdventureCardFactory.Types.MERLIN, 1));

		L.addAll(F.createCardList(AdventureCardFactory.Types.AMOUR, 8));



		return L;
	}
/*
		//WEAPONS

		for(int i = 0; i < 2; i++)
			advCards.add(F.defaultWeapon(1,"W Excalibur.jpg","Excalibur",30));

		for(int i = 0; i < 6; i++)
			advCards.add(F.defaultWeapon(2,"W Lance.jpg","Lance",20));

		for(int i = 0; i < 8; i++)
			advCards.add(F.defaultWeapon(3,"W Battle-Ax.jpg","Battle-ax",15));

		for(int i = 0; i < 16; i++)
			advCards.add(F.defaultWeapon(4,"W Sword.jpg","Sword",10));

		for(int i = 0; i < 11; i++)
			advCards.add(F.defaultWeapon(5,"W Horse.jpg","Horse",10));

		for(int i = 0; i < 6; i++)
			advCards.add(F.defaultWeapon(6,"W Dagger.jpg","Dagger",5));

		//FOES

		advCards.add(F.specifiedFoe(7, "F Dragon.jpg", "Dragon", 50, 70));

		for(int i = 0; i < 2; i++)
			advCards.add(F.defaultFoe(8,"F Giant.jpg","Giant",40));

		//MORDRED HERE (id = 9)

		for(int i = 0; i < 2, i++)
			advCards.add(F.specifiedFoe(10, "F Green Knight.jpg", "Green Knight", 25, 40));

		for(int i = 0; i < 3, i++)
			advCards.add(F.specifiedFoe(11, "F Black Knight.jpg", "Black Knight", 25, 35));

		for(int i = 0; i < 6, i++)
			advCards.add(F.specifiedFoe(12, "F Evil Knight.jpg", "Evil Knight", 20, 30));

		for(int i = 0; i < 8, i++)
			advCards.add(F.specifiedFoe(13, "F Saxon Knight.jpg", "Saxon Knight", 15, 25));

		for(int i = 0; i < 7; i++)
			advCards.add(F.defaultFoe(14,"F Robber Knight.jpg","Robber Knight",15));

		for(int i = 0; i < 5, i++)
			advCards.add(F.specifiedFoe(15, "F Saxons.jpg", "Saxons", 10, 20));

		for(int i = 0; i < 4, i++)
			advCards.add(F.specifiedFoe(16, "F Boar.jpg", "Boar", 5, 15));

		for(int i = 0; i < 8; i++)
			advCards.add(F.defaultFoe(17,"F Thieves.jpg","Thieves",5));


		//ALLIES

		advCards.add(F.defaultAlly(18,"A Sir Galahad.jpg","Sir Galahad",15,1));

		public static AdventureCard specifiedAlly(int id, String res, String name, int defaultBP, int defaultbids, int specifiedBP, int specifiedBids, boolean freeBid){
			return new AllyCard(id, res, new (defaultBP, defaultBids, specifiedBP, specifiedBids, freeBid), name);
		}

		advCards.add(F.specifiedAlly(19, "A Sir Lancelot.jpg","Sir Lancelot",15,25,1,1));


		advCards.add(F.defaultAlly(20,"A King Arthur.jpg","King Arthur",10,2,true));

		advCards.add(F.specifiedAlly(21, "A Sir Tristan.jpg","Sir Tristan",15,25,1,1));

		advCards.add(F.defaultAlly(2,"A Queen Guinevere.jpg","Queen Guinevere",0,3,true));


		for(int i = 0; i < 8; i++)
			advCards.add(F.defaultAmour(99,"Amour.jpg","Amour"));
		
		return advCards;
	} */


	/*static public List<StoryCard> loadStoryCards () {
		List<StoryCard> storyCards = new ArrayList<StoryCard>();
		StoryCardFactory F = new StoryCardFactory();

		//QUESTS



	}*/


}
