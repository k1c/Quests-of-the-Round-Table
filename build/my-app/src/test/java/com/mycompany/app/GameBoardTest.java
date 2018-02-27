package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCardFactoryTest;
import junit.framework.TestCase;
import java.util.*;

public class GameBoardTest extends TestCase{

	String[] dummyNames = {"a", "b", "c", "d"};

	public GameBoardTest(String name){
		super(name);
	}

	private int countId(List<Card> list, int id){
		int accumulator = 0;
		for(Card item : list)
			if (item.id == id)
				accumulator++;
		return accumulator;
	}

	public void test1() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		//players are initialized
		assertEquals(4, board.players.size());

		//player hands are initialized
		assertEquals(12, board.players.get(0).hand.size());
		assertEquals(12, board.players.get(1).hand.size());
		assertEquals(12, board.players.get(2).hand.size());
		assertEquals(12, board.players.get(3).hand.size());

		//cards can be drawn from Adventure Deck
		adventureDeckSize = board.adventureDeck.size();

		board.drawFromAdventureDeck(board.players.get(0));

		assertEquals(adventureDeckSize - 1, board.adventureDeck.size());
		assertEquals(13, board.players.get(0).hand.size());

		//cards can be drawn from Story Deck
		storyDeckSize = board.storyDeck.size();

		board.drawFromStoryDeck(board.players.get(0));

		assertEquals(storyDeckSize - 1, board.storyDeck.size());

		board.resetStory();
		assertEquals(board.currentStory, null);

	}

	public void test2() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		//start a 4 player game
		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		//give cards specified in Scenario 1 to players
		board.players.get(0).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.SAXONS));
		board.players.get(0).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.BOAR));
		board.players.get(0).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.DAGGER));
		board.players.get(0).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.SWORD));

		board.players.get(2).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.HORSE));
		board.players.get(2).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.EXCALIBUR));

		board.players.get(3).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.BATTLE_AX));
		board.players.get(3).hand.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.LANCE));

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.BOAR_HUNT);

		//check that Player 1 can Sponsor a Quest
		assertEquals(true, board.playerCanSponsor(board.players.get(0).id));

		//Player 1 sets up the Scenario 1 quest
		TwoDimensionalArrayList<Card> boarAndFriends = new TwoDimensionalArrayList<Card>();

		ArrayList<Card> stage1 = new ArrayList<Card>();
		ArrayList<Card> stage2 = new ArrayList<Card>();

		stage1.add(board.players.get(0).hand.get(12));
		stage2.add(board.players.get(0).hand.get(13));
		stage2.add(board.players.get(0).hand.get(14));
		stage2.add(board.players.get(0).hand.get(15));

		boarAndFriends.add(stage1);
		boarAndFriends.add(stage2);

		//Player 1 submits their quest
		board.submitQuest(boarAndFriends, board.players.get(0).id);

		//check that Player 1's quest is submitted proper;y
		assertEquals(12, board.players.get(0).hand.size());
		assertEquals(2, board.quest.size());
		assertEquals(1, board.quest.get(0).size());
		assertEquals(3, board.quest.get(1).size());

		//check that Player 1 is the sponsor
		assertEquals(board.sponsor, board.players.get(0));

		board.addParticipant(board.players.get(1).id);
		board.addParticipant(board.players.get(2).id);
		board.addParticipant(board.players.get(3).id);

		assertEquals(3, board.participants.size());

		//First quest stage (Saxons)
		board.currentQuestIndex = 0;

		//Player 2 plays nothing

		//Player 3 plays a Horse
		List<Card> p3SubmitCards = new ArrayList<Card>(12);
		p3SubmitCards.add(board.players.get(2).hand.get(12));

		board.submitHand(board.players.get(2).id, p3SubmitCards);

		//Player 3's Horse is in "to be played"
		assertEquals(1, board.players.get(2).toBePlayed.size());
		assertEquals(5, board.players.get(2).toBePlayed.get(0).id);
		assertEquals(13, board.players.get(2).hand.size());

		//Player 4 plays a Battle-ax
		List<Card> p4SubmitCards = new ArrayList<Card>(12);
		p4SubmitCards.add(board.players.get(3).hand.get(12));

		board.submitHand(board.players.get(3).id, p4SubmitCards);

		//Player 4's Battle-ax is in "to be played"
		assertEquals(1, board.players.get(3).toBePlayed.size());
		assertEquals(3, board.players.get(3).toBePlayed.get(0).id);
		assertEquals(13, board.players.get(3).hand.size());

		//resolve stage
		board.completeFoeStage();

		//check that Player 2 has been eliminated, i.e. that only Players 3 and 4 are still participating
		assertEquals(2, board.participants.size());
		assertEquals(board.participants.get(0), board.players.get(2));
		assertEquals(board.participants.get(1), board.players.get(3));

		//check that weapons have been cleared from players hands
		assertEquals(0, board.players.get(2).inPlay.size());
		assertEquals(0, board.players.get(3).inPlay.size());

		//Second & Final quest stage (Boar + Dagger + Sword)
		board.nextStage();

		//Player 3 plays Excalibur
		List<Card> p3SubmitCards2 = new ArrayList<Card>(12);
		p3SubmitCards2.add(board.players.get(2).hand.get(12));

		board.submitHand(board.players.get(2).id, p3SubmitCards2);

		//Player 3's Excalibur is in "to be played"
		assertEquals(1, board.players.get(2).toBePlayed.size());
		assertEquals(1, board.players.get(2).toBePlayed.get(0).id);
		assertEquals(12, board.players.get(2).hand.size());

		//Player 4 plays a Lance
		List<Card> p4SubmitCards2 = new ArrayList<Card>(12);
		p4SubmitCards2.add(board.players.get(3).hand.get(12));

		board.submitHand(board.players.get(3).id, p4SubmitCards2);

		//Player 4's Lance is in "to be played"
		assertEquals(1, board.players.get(3).toBePlayed.size());
		assertEquals(2, board.players.get(3).toBePlayed.get(0).id);
		assertEquals(12, board.players.get(3).hand.size());

		//resolve stage
		board.completeFoeStage();

		//Player 3 is the only remaining participant
		assertEquals(1, board.participants.size());
		assertEquals(board.participants.get(0), board.players.get(2));

		board.endQuest();

		//Player 3 is awarded 2 shields for the 2 stages of Boar Hunt
		assertEquals(0, board.players.get(1).rank.getShields());
		assertEquals(2, board.players.get(2).rank.getShields());
		assertEquals(0, board.players.get(3).rank.getShields());

		//check that quest is reset
		assertEquals(0, board.quest.size());

		//check that sponsor has drawn 2 + 4 = 6 adventure cards
		assertEquals(12 + 4 - 4 + 6, board.players.get(0).hand.size());

	}


	public void test3() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.BOAR_HUNT);

		//change the first stage of the previous scenario to a Test



	}

}
