package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCardFactoryTest;
import junit.framework.TestCase;
import java.util.*;


public class AdventureCardFactoryTest extends TestCase{

	String[] dummyNames = {"a", "b", "c", "d"};
	public AdventureCardFactoryTest(String name){
		super(name);
	}

	public void testDefaultAllyCard() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4,0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());


		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.KING_ARTHUR);
		assertEquals(24,Temp.id);
		assertEquals("/A King Arthur.jpg",Temp.res);
		assertEquals("King Arthur",Temp.name);
		assertEquals(10,Temp.getBattlePoints(board));
		assertEquals(2,Temp.getBids(board));
		assertEquals(true,Temp.freeBid(board));

	}

	public void testSpecifiedAllyCard() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4,0, dummyNames,CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.SLAY_THE_DRAGON);

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.SIR_LANCELOT);
		assertEquals(23,Temp.id);
		assertEquals("/A Sir Lancelot.jpg",Temp.res);
		assertEquals("Sir Lancelot",Temp.name);
		assertEquals(15,Temp.getBattlePoints(board));
		assertEquals(1,Temp.getBids(board));
		assertEquals(false,Temp.freeBid(board));

	}

	public void testDefaultAmourCard() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4,0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.AMOUR);
		assertEquals(32,Temp.id);
		assertEquals("/Amour.jpg",Temp.res);
		assertEquals("Amour",Temp.name);
		assertEquals(10,Temp.getBattlePoints(board));
		assertEquals(1,Temp.getBids(board));
		assertEquals(true,Temp.freeBid(board));

	}

	public void testDefaultWeaponCard() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4,0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());


		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.EXCALIBUR);
		assertEquals(1,Temp.id);
		assertEquals("/W Excalibur.jpg",Temp.res);
		assertEquals("Excalibur",Temp.name);
		assertEquals(30,Temp.getBattlePoints(board));
		assertEquals(1,Temp.getBids(board));
		assertEquals(false,Temp.freeBid(board));

	}

	public void testDefaultFoeCard() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4,0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());


		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.GIANT);
		assertEquals(8,Temp.id);
		assertEquals("/F Giant.jpg",Temp.res);
		assertEquals("Giant",Temp.name);
		assertEquals(40,Temp.getBattlePoints(board));
		assertEquals(1,Temp.getBids(board));
		assertEquals(false,Temp.freeBid(board));

	}

	public void testSpecifiedFoeCard() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.BOAR_HUNT);

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.DRAGON);
		assertEquals(7,Temp.id);
		assertEquals("/F Dragon.jpg",Temp.res);
		assertEquals("Dragon",Temp.name);
		assertEquals(50,Temp.getBattlePoints(board));
		assertEquals(1,Temp.getBids(board));
		assertEquals(false,Temp.freeBid(board));

	}

	public void testSpecifiedFoeCardBoarHunt() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.BOAR_HUNT);

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.BOAR);
		assertEquals(16,Temp.id);
		assertEquals("/F Boar.jpg",Temp.res);
		assertEquals("Boar",Temp.name);
		assertEquals(15,Temp.getBattlePoints(board));
		assertEquals(1,Temp.getBids(board));
		assertEquals(false,Temp.freeBid(board));

	}

	public void testSpecifiedHolyGrail() throws Exception{
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.SEARCH_FOR_THE_HOLY_GRAIL);

		AdventureCard Temp1 = AdventureCardFactory.createCard(AdventureCardFactory.Types.BOAR);
		assertEquals(16,Temp1.id);
		assertEquals("/F Boar.jpg",Temp1.res);
		assertEquals("Boar",Temp1.name);
		assertEquals(15,Temp1.getBattlePoints(board));
		assertEquals(1,Temp1.getBids(board));
		assertEquals(false,Temp1.freeBid(board));

		AdventureCard Temp2 = AdventureCardFactory.createCard(AdventureCardFactory.Types.EVIL_KNIGHT);
		assertEquals(12,Temp2.id);
		assertEquals("/F Evil Knight.jpg",Temp2.res);
		assertEquals("Evil Knight",Temp2.name);
		assertEquals(30,Temp2.getBattlePoints(board));
		assertEquals(1,Temp2.getBids(board));
		assertEquals(false,Temp2.freeBid(board));

		AdventureCard Temp3 = AdventureCardFactory.createCard(AdventureCardFactory.Types.SIR_PERCIVAL);
		assertEquals(28,Temp3.id);
		assertEquals("/A Sir Percival.jpg",Temp3.res);
		assertEquals("Sir Percival",Temp3.name);
		assertEquals(20,Temp3.getBattlePoints(board));
		assertEquals(1,Temp3.getBids(board));
		assertEquals(false,Temp3.freeBid(board));

		AdventureCard Temp4 = AdventureCardFactory.createCard(AdventureCardFactory.Types.KING_PELLINORE);
		assertEquals(26,Temp4.id);
		assertEquals("/A King Pellinore.jpg",Temp4.res);
		assertEquals("King Pellinore",Temp4.name);
		assertEquals(10,Temp4.getBattlePoints(board));
		assertEquals(1,Temp4.getBids(board));
		assertEquals(false,Temp4.freeBid(board));

	}

	public void testSpecifiedPellinore() throws Exception {
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.SEARCH_FOR_THE_QUESTING_BEAST);

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.KING_PELLINORE);
		assertEquals(26, Temp.id);
		assertEquals("/A King Pellinore.jpg", Temp.res);
		assertEquals("King Pellinore", Temp.name);
		assertEquals(10, Temp.getBattlePoints(board));
		assertEquals(4, Temp.getBids(board));
		assertEquals(true, Temp.freeBid(board));
	}

	public void testDefaultTest4Player() throws Exception {
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.SEARCH_FOR_THE_QUESTING_BEAST);

		board.players = new ArrayList<Player>();

		for (int i = 0; i < 4; i++)
			board.players.add(new HumanPlayer("",""));

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.TEST_OF_VALOR);
		assertEquals(18, Temp.id);
		assertEquals("/T Test of Valor.jpg", Temp.res);
		assertEquals("Test of Valor", Temp.name);
		assertEquals(1, Temp.getBids(board));
	}

	public void testDefaultTest2Player() throws Exception {
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.SEARCH_FOR_THE_QUESTING_BEAST);

		board.players = new ArrayList<Player>();

		for (int i = 0; i < 2; i++)
			board.players.add(new HumanPlayer("",""));

		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.TEST_OF_VALOR);
		assertEquals(18, Temp.id);
		assertEquals("/T Test of Valor.jpg", Temp.res);
		assertEquals("Test of Valor", Temp.name);
		assertEquals(3, Temp.getBids(board));
	}


	public void testSpecifiedQuestingBeast() throws Exception {
		GameBoard board = new GameBoard();
		board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

		board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.SEARCH_FOR_THE_QUESTING_BEAST);

		board.players = new ArrayList<Player>();

		for (int i = 0; i < 4; i++)
			board.players.add(new HumanPlayer("",""));


		AdventureCard Temp = AdventureCardFactory.createCard(AdventureCardFactory.Types.TEST_OF_THE_QUESTING_BEAST);
		assertEquals(21, Temp.id);
		assertEquals("/T Test of the Questing Beast.jpg", Temp.res);
		assertEquals("Test of the Questing Beast", Temp.name);
		assertEquals(4, Temp.getBids(board));
	}

}

