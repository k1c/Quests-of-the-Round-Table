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

	public void testInitGame() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		//board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());
		initGameTest(board, numHumans, numAI, humanNames);

		//drawFromAdventureDeckTest(board, )


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

	public void initGameTest(GameBoard board, int numHumans, int numAI, String[] names) {

		board.initGame(numHumans, numAI, names, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		//players are initialized
		assertEquals(4, board.players.size());

		//player hands are initialized
		assertEquals(12, board.players.get(0).hand.size());
		assertEquals(12, board.players.get(1).hand.size());
		assertEquals(12, board.players.get(2).hand.size());
		assertEquals(12, board.players.get(3).hand.size());
	}


	public void testScenario1() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		//start a 4 player game
		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		//discard some cards to make room for Scenario 1 cards & test discard functionality
		List<Card> discardList = new ArrayList<Card>();

		for(int i = 0; i < 4; i++){
			discardList.add(board.players.get(0).hand.get(i));
		}

		discardHandTest(board, board.players.get(0).id, discardList);

		discardList.clear();

		for(int i = 0; i < 2; i++){
			discardList.add(board.players.get(2).hand.get(i));
		}

		board.discardHand(board.players.get(2).id, discardList);
		discardList.clear();

		for(int i = 0; i < 2; i++){
			discardList.add(board.players.get(3).hand.get(i));
		}
		board.discardHand(board.players.get(3).id, discardList);
		discardList.clear();

		//remove automatically played Allies from the board
		board.resetInPlay();


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

		//check that Player 1 can sponsor a quest
		playerCanSponsorTest(board, board.players.get(0).id);

		//Player 1 sets up the Scenario 1 quest
		TwoDimensionalArrayList<Card> boarAndFriends = new TwoDimensionalArrayList<Card>();

		ArrayList<Card> stage1 = new ArrayList<Card>();
		ArrayList<Card> stage2 = new ArrayList<Card>();

		stage1.add(board.players.get(0).hand.get(8));
		stage2.add(board.players.get(0).hand.get(9));
		stage2.add(board.players.get(0).hand.get(10));
		stage2.add(board.players.get(0).hand.get(11));

		boarAndFriends.add(stage1);
		boarAndFriends.add(stage2);

		submitQuestTest(board,boarAndFriends, board.players.get(0).id);

		//Player 1 submits their quest
		//board.submitQuest(boarAndFriends, board.players.get(0).id);

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
		p3SubmitCards.add(board.players.get(2).hand.get(10));

		//board.submitHand(board.players.get(2).id, p3SubmitCards);

		submitHandTest(board, board.players.get(2).id, p3SubmitCards);

		//Player 4 plays a Battle-ax
		List<Card> p4SubmitCards = new ArrayList<Card>(12);
		p4SubmitCards.add(board.players.get(3).hand.get(10));

		board.submitHand(board.players.get(3).id, p4SubmitCards);


		//Player 4's Battle-ax is in "to be played"
		/*assertEquals(1, board.players.get(3).toBePlayed.size());
		assertEquals(3, board.players.get(3).toBePlayed.get(0).id);
		assertEquals(11, board.players.get(3).hand.size());*/

		//resolve stage
		//board.completeFoeStage();
		completeFoeStageTest(board);


		//Second & Final quest stage (Boar + Dagger + Sword)
		//board.nextStage();
		nextStageTest(board);

		//Player 3 plays Excalibur
		List<Card> p3SubmitCards2 = new ArrayList<Card>(12);
		p3SubmitCards2.add(board.players.get(2).hand.get(10));

		board.submitHand(board.players.get(2).id, p3SubmitCards2);


		//Player 3's Excalibur is in "to be played"
		/*assertEquals(1, board.players.get(2).toBePlayed.size());
		assertEquals(1, board.players.get(2).toBePlayed.get(0).id);
		assertEquals(10, board.players.get(2).hand.size());*/

		//Player 4 plays a Lance
		List<Card> p4SubmitCards2 = new ArrayList<Card>(12);
		p4SubmitCards2.add(board.players.get(3).hand.get(10));

		board.submitHand(board.players.get(3).id, p4SubmitCards2);


		//Player 4's Lance is in "to be played"
		/*assertEquals(1, board.players.get(3).toBePlayed.size());
		assertEquals(2, board.players.get(3).toBePlayed.get(0).id);
		assertEquals(10, board.players.get(3).hand.size());*/

		//resolve stage
		board.completeFoeStage();

		//Player 3 is the only remaining participant
		/*assertEquals(1, board.participants.size());
		assertEquals(board.participants.get(0), board.players.get(2));*/

		//board.endQuest();
		endQuestTest(board);

	}

	public void testTournament() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.TOURNAMENT_AT_ORKNEY);

		board.addParticipant(board.players.get(0).id);
		board.addParticipant(board.players.get(1).id);
		board.addParticipant(board.players.get(2).id);
		board.addParticipant(board.players.get(3).id);

		//Add cards to players' hands
		board.players.get(0).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.DAGGER));
		board.players.get(0).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.SWORD));
		board.players.get(0).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.SIR_TRISTAN));

		board.players.get(1).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.HORSE));
		board.players.get(1).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.BATTLE_AX));
		board.players.get(1).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.LANCE));

		board.players.get(2).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.AMOUR));
		board.players.get(2).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.EXCALIBUR));

		//check total BPs
		assertEquals(30, board.players.get(0).getTotalBP(board));
		assertEquals(50, board.players.get(1).getTotalBP(board));
		assertEquals(45, board.players.get(2).getTotalBP(board));
		assertEquals(5, board.players.get(3).getTotalBP(board)); //Player 4 just has their rank BP

		board.completeTournamentStage();

		//check Player 2 wins
		assertEquals(1, board.participants.size());
		assertEquals(board.participants.get(0), board.players.get(1));

		board.applyStoryCardLogic(-1);

		//check that Player 2 gets 4 (# participants) + 2 (# bonus shields) shields --> Ranks up to a KNIGHT with 1 shield
		assertEquals(0, board.players.get(0).rank.shields);
		assertEquals(Rank.RankType.KNIGHT, board.players.get(1).rank.getRank());
		assertEquals(1, board.players.get(1).rank.shields);
		assertEquals(0, board.players.get(2).rank.shields);
		assertEquals(0, board.players.get(3).rank.shields);
	}

	public void testQuestTest() throws Exception {
		GameBoard board = new GameBoard();
		int numHumans = 4;
		int numAI = 0;
		int adventureDeckSize = 0;
		int storyDeckSize = 0;

		String[] humanNames = dummyNames;

		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.BOAR_HUNT);

		TwoDimensionalArrayList<Card> boarAndFriends = new TwoDimensionalArrayList<Card>();

		ArrayList<Card> stage1 = new ArrayList<Card>();
		ArrayList<Card> stage2 = new ArrayList<Card>();

		stage1.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.TEST_OF_VALOR));
		stage2.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.BOAR));
		stage2.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.DAGGER));
		stage2.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.SWORD));

		boarAndFriends.add(stage1);
		boarAndFriends.add(stage2);

		board.submitQuest(boarAndFriends, board.players.get(0).id);

		board.addParticipant(board.players.get(1).id);
		board.addParticipant(board.players.get(2).id);
		board.addParticipant(board.players.get(3).id);


		//submitBids(int player, List<Card> hand)

		//checkTestWinner()

		//giveUp(Integer id)

	}


	public void playerCanSponsorTest(GameBoard board, int playerID){
		board.playerCanSponsor(playerID);

		assertEquals(true, board.playerCanSponsor(playerID));
	}

	public void discardHandTest(GameBoard board, int playerID, List<Card> cardsToDiscard){
		board.discardHand(playerID, cardsToDiscard);

		assertEquals(8, board.players.get(0).hand.size());
	}


	public void submitQuestTest(GameBoard board,TwoDimensionalArrayList<Card> playerQuest, int playerID) {
		//Player 1 submits their quest
		board.submitQuest(playerQuest, playerID);

		//check that Player 1's quest is submitted proper;y
		assertEquals(8, board.players.get(0).hand.size());
		assertEquals(2, board.quest.size());
		assertEquals(1, board.quest.get(0).size());
		assertEquals(3, board.quest.get(1).size());

		assertEquals(0, board.currentQuestIndex);

	}

	public void submitHandTest(GameBoard board, int playerID, List<Card> cardsToSubmit) {
		//Player 3 submits their cards to be played
		board.submitHand(playerID, cardsToSubmit);

		//Player 3's Excalibur is in "to be played"
		assertEquals(1, board.players.get(2).toBePlayed.size());
		assertEquals(5, board.players.get(2).toBePlayed.get(0).id);
		assertEquals(11, board.players.get(2).hand.size());

	}

	public void completeFoeStageTest(GameBoard board){
		board.completeFoeStage();

		//check that Player 2 has been eliminated, i.e. that only Players 3 and 4 are still participating
		assertEquals(2, board.participants.size());
		assertEquals(board.participants.get(0), board.players.get(2));
		assertEquals(board.participants.get(1), board.players.get(3));

		//check that weapons have been cleared from players hands

		assertEquals(0, board.players.get(2).inPlay.size());
		assertEquals(0, board.players.get(3).inPlay.size());

		//!! check that weapons are in the discard pile

	}

	public void nextStageTest(GameBoard board){
		board.nextStage();

		assertEquals(1, board.currentQuestIndex);
	}

	public void endQuestTest(GameBoard board){
		board.endQuest();

		//Player 3 is awarded 2 shields for the 2 stages of Boar Hunt
		assertEquals(0, board.players.get(1).rank.getShields());
		assertEquals(2, board.players.get(2).rank.getShields());
		assertEquals(0, board.players.get(3).rank.getShields());

		//check that quest is reset
		assertEquals(0, board.quest.size());
		//!! check that quest cards are in the adventure deck discard pile

		//check that sponsor has drawn 2 + 4 = 6 adventure cards
		assertEquals(12 - 4 + 6, board.players.get(0).hand.size());

	}





}
