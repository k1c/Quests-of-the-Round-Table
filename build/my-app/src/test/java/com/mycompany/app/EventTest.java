package com.mycompany.app.model;

import com.mycompany.app.model.EventTest;
import junit.framework.TestCase;
import java.util.*;


public class EventTest extends TestCase {

    String[] dummyNames = {"a", "b", "c", "d"};

    public EventTest(String name) {
        super(name);
    }

    public void testProsperity() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.PROSPERITY_THROUGHOUT_THE_REALM);

        board.applyStoryCardLogic(board.players.get(0).id);

        assertEquals(14, board.players.get(0).hand.size());
        assertEquals(14, board.players.get(1).hand.size());
        assertEquals(14, board.players.get(2).hand.size());
        assertEquals(14, board.players.get(3).hand.size());
    }

    public void testQueensFavor() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.QUEENS_FAVOR);

        board.applyStoryCardLogic(board.players.get(0).id);

    }

    public void testPox() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.POX);

        board.applyStoryCardLogic(board.players.get(0).id);

    }

    public void testPlague() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.PLAGUE);

        board.applyStoryCardLogic(board.players.get(0).id);

    }

    public void testCourtCalledtoCamelot() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.COURT_CALLED_TO_CAMELOT);

        board.applyStoryCardLogic(board.players.get(0).id);

    }

    public void testChivalrousDeed() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.CHIVALROUS_DEED);

        board.applyStoryCardLogic(board.players.get(0).id);

    }
}








/*package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCardFactoryTest;
import junit.framework.TestCase;
import java.util.*;


public class EventTest extends TestCase{
    public EventTest(String name){
        super(name);
    }

    public void testQueensFavor() throws Exception{
        GameBoard board = new GameBoard();
        board.initGame(4, new ArrayList<AdventureCard>(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.QUEENS_FAVOR);

        board.players = new ArrayList<Player>();

        for (int i = 0; i < 4; i++)
            board.players.add(new Player());


        board.applyStoryCardLogic(board.questSponsor);

        //assertEquals()




    }*/