package com.mycompany.app.model;

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
		L.addAll(F.createCardList(AdventureCardFactory.Types.MORDRED, 4));
		L.addAll(F.createCardList(AdventureCardFactory.Types.GREEN_KNIGHT, 2));
		L.addAll(F.createCardList(AdventureCardFactory.Types.BLACK_KNIGHT, 3));
		L.addAll(F.createCardList(AdventureCardFactory.Types.EVIL_KNIGHT, 6));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SAXON_KNIGHT, 8));
		L.addAll(F.createCardList(AdventureCardFactory.Types.ROBBER_KNIGHT, 7));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SAXONS, 5));
		L.addAll(F.createCardList(AdventureCardFactory.Types.BOAR, 4));
		L.addAll(F.createCardList(AdventureCardFactory.Types.THIEVES, 8));

		L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_VALOR, 2));
		L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_TEMPTATION, 2));
		L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_MORGAN_LE_FAY, 2));
		L.addAll(F.createCardList(AdventureCardFactory.Types.TEST_OF_THE_QUESTING_BEAST, 2));

		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_GALAHAD, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_LANCELOT, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.KING_ARTHUR, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_TRISTAN, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.KING_PELLINORE, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_GAWAIN, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.SIR_PERCIVAL, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.QUEEN_GUINEVERE, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.QUEEN_ISEULT, 1));
		L.addAll(F.createCardList(AdventureCardFactory.Types.MERLIN, 1));

		L.addAll(F.createCardList(AdventureCardFactory.Types.AMOUR, 8));



		return L;
	}


	static public List<StoryCard> loadStoryCards () {
		List<StoryCard> storyCards = new ArrayList<StoryCard>();
		StoryCardFactory F = new StoryCardFactory();

		List<StoryCard> L = new ArrayList<StoryCard>();
        
		//L.addAll(F.createCardList(StoryCardFactory.Types.SEARCH_FOR_THE_HOLY_GRAIL, 1));
		//L.addAll(F.createCardList(StoryCardFactory.Types.TEST_OF_THE_GREEN_KNIGHT, 1));
		//L.addAll(F.createCardList(StoryCardFactory.Types.SEARCH_FOR_THE_QUESTING_BEAST, 1));
		//L.addAll(F.createCardList(StoryCardFactory.Types.DEFEND_THE_QUEENS_HONOR, 1));
		//L.addAll(F.createCardList(StoryCardFactory.Types.RESCUE_THE_FAIR_MAIDEN, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.JOURNEY_THROUGH_THE_ENCHANTED_FOREST, 1));
		//L.addAll(F.createCardList(StoryCardFactory.Types.VANQUISH_KING_ARTHURS_ENEMIES, 2));
		//L.addAll(F.createCardList(StoryCardFactory.Types.SLAY_THE_DRAGON, 1));*/
		//L.addAll(F.createCardList(StoryCardFactory.Types.BOAR_HUNT, 2));
		/*L.addAll(F.createCardList(StoryCardFactory.Types.REPEL_THE_SAXON_RAIDERS, 2));

		L.addAll(F.createCardList(StoryCardFactory.Types.TOURNAMENT_AT_CAMELOT, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.TOURNAMENT_AT_ORKNEY, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.TOURNAMENT_AT_TINTAGEL, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.TOURNAMENT_AT_YORK, 1));
		*/
		//L.addAll(F.createCardList(StoryCardFactory.Types.KINGS_RECOGNITION, 2));
		/*L.addAll(F.createCardList(StoryCardFactory.Types.QUEENS_FAVOR, 2));
		L.addAll(F.createCardList(StoryCardFactory.Types.COURT_CALLED_TO_CAMELOT, 2));
		L.addAll(F.createCardList(StoryCardFactory.Types.POX, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.PLAGUE, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.CHIVALROUS_DEED, 1));
		L.addAll(F.createCardList(StoryCardFactory.Types.PROSPERITY_THROUGHOUT_THE_REALM, 1));*/
		//L.addAll(F.createCardList(StoryCardFactory.Types.KINGS_CALL_TO_ARMS, 1));

		return L;

	}


}
