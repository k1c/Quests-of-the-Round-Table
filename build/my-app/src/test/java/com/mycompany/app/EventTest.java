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

        //all players draw 2 adventure cards
        assertEquals(14, board.players.get(0).hand.size());
        assertEquals(14, board.players.get(1).hand.size());
        assertEquals(14, board.players.get(2).hand.size());
        assertEquals(14, board.players.get(3).hand.size());
    }

    public void testQueensFavor() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.QUEENS_FAVOR);

        board.applyStoryCardLogic(board.players.get(2).id);

        //all players are of rank squire --> all players draw 2 adventure cards
        assertEquals(14, board.players.get(0).hand.size());
        assertEquals(14, board.players.get(1).hand.size());
        assertEquals(14, board.players.get(2).hand.size());
        assertEquals(14, board.players.get(3).hand.size());

        //change player's rank & number of shields
        for(int i = 0; i < board.players.size(); i++){
            board.players.get(i).rank.addRemoveShields(i*4);
        }
        //Player 1 is a Squire with 0 shields
        //Player 2 is a Squire with 4 shields
        //Player 3 is a Knight with 8 - 5 = 3 shields
        //Player 4 is a Champion Knight with 12 - 5 - 7 = 0 shields

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.QUEENS_FAVOR);

        board.applyStoryCardLogic(board.players.get(1).id);

        //check Players 1 and 2 draw 2 adventure cards; Players 3 and 4 do not
        assertEquals(16, board.players.get(0).hand.size());
        assertEquals(16, board.players.get(1).hand.size());
        assertEquals(14, board.players.get(2).hand.size());
        assertEquals(14, board.players.get(3).hand.size());

    }

    public void testPox() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.POX);

        //Give players different #s of shields
        for(int i = 0; i < board.players.size(); i++){
            board.players.get(i).rank.addRemoveShields(i);
        }
        //Player 1 has 0 shields
        //Player 2 has 1 shield
        //Player 3 has 2 shields
        //Player 4 has 3 shields

        //Player 3 draws Pox
        board.applyStoryCardLogic(board.players.get(2).id);

        //check that all players except player 3 lose 1 shield, but do not go below 0
        assertEquals(0, board.players.get(0).rank.shields); //Player 1 cannot go lower than 0 shields
        assertEquals(0, board.players.get(1).rank.shields); //Player 2 goes down to 0 shields
        assertEquals(2, board.players.get(2).rank.shields); //Player 3 keeps their 2 shields
        assertEquals(2, board.players.get(3).rank.shields); //Player 4 goes down to 2 shields

    }

    public void testPlague() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.PLAGUE);

        //Give players different #s of shields
        for(int i = 0; i < board.players.size(); i++){
            board.players.get(i).rank.addRemoveShields(i);
        }
        //Player 1 has 0 shields
        //Player 2 has 1 shield
        //Player 3 has 2 shields
        //Player 4 has 3 shields

        //Player 4 draws Plague
        board.applyStoryCardLogic(board.players.get(3).id);

        //check that Player 4 loses two shields
        assertEquals(0, board.players.get(0).rank.shields); //Player 1 stays at 0 shields
        assertEquals(1, board.players.get(1).rank.shields); //Player 2 keeps their 1 shield
        assertEquals(2, board.players.get(2).rank.shields); //Player 3 keeps their 2 shields
        assertEquals(1, board.players.get(3).rank.shields); //Player 4 loses 2 shields, goes down to 1

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.PLAGUE);

        //Player 2 draws Plague
        board.applyStoryCardLogic(board.players.get(1).id);

        //check that Player 2 loses their 1 shield but does not go below 0 shields
        assertEquals(0, board.players.get(0).rank.shields); //Player 1 stays at 0 shields
        assertEquals(0, board.players.get(1).rank.shields); //Player 2 loses their 1 shield, goes down to 0
        assertEquals(2, board.players.get(2).rank.shields); //Player 3 keeps their 2 shields
        assertEquals(1, board.players.get(3).rank.shields); //Player 4 keeps their 1 shield

    }

    public void testCourtCalledtoCamelot() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.COURT_CALLED_TO_CAMELOT);

        //Player 1 draws Court Called to Camelot
        board.applyStoryCardLogic(board.players.get(0).id);

        //No cards are in play; check that nothing happens
        assertEquals(0, board.players.get(0).inPlay.size());
        assertEquals(0, board.players.get(1).inPlay.size());
        assertEquals(0, board.players.get(2).inPlay.size());
        assertEquals(0, board.players.get(3).inPlay.size());

        //add some Allies to the board
        board.players.get(0).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.KING_ARTHUR));
        board.players.get(0).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.QUEEN_GUINEVERE));
        board.players.get(2).inPlay.add(AdventureCardFactory.createCard(AdventureCardFactory.Types.MERLIN));

        //ensure Allies are on the board
        assertEquals("King Arthur", board.players.get(0).inPlay.get(0).name);
        assertEquals("Queen Guinevere", board.players.get(0).inPlay.get(1).name);
        assertEquals("Merlin", board.players.get(2).inPlay.get(0).name);

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.COURT_CALLED_TO_CAMELOT);

        //Player 4 draws Court Called to Camelot
        board.applyStoryCardLogic(board.players.get(3).id);

        //Check that allies in play are removed
        assertEquals(0, board.players.get(0).inPlay.size());
        assertEquals(0, board.players.get(1).inPlay.size());
        assertEquals(0, board.players.get(2).inPlay.size());
        assertEquals(0, board.players.get(3).inPlay.size());

    }

    public void testChivalrousDeed() throws Exception {
        GameBoard board = new GameBoard();

        board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(), new ArrayList<StoryCard>());

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.CHIVALROUS_DEED);

        //Player 1 draws Chivalrous Deed
        board.applyStoryCardLogic(board.players.get(0).id);

        //check that all players get 3 shields
        assertEquals(3, board.players.get(0).rank.shields);
        assertEquals(3, board.players.get(1).rank.shields);
        assertEquals(3, board.players.get(2).rank.shields);
        assertEquals(3, board.players.get(3).rank.shields);

        //Make different players different ranks with different #'s of shields
        for(int i = 0; i < board.players.size(); i++){
            board.players.get(i).rank.addRemoveShields((i+1)/2);
        }
        //Player 1 is a Squire with 3 shields
        //Player 2 is a Squire with 4 shields
        //Player 3 is a Squire with 4 shields
        //Player 4 is a Knight with 0 shields

        board.currentStory = StoryCardFactory.createCard(StoryCardFactory.Types.CHIVALROUS_DEED);

        //Player 2 draws Chivalrous Deed
        board.applyStoryCardLogic(board.players.get(1).id);

        //check that Player 1 gets 3 shields & becomes a Knight with 1 shield
        assertEquals(1, board.players.get(0).rank.shields);
        assertEquals(4, board.players.get(1).rank.shields);
        assertEquals(4, board.players.get(2).rank.shields);
        assertEquals(0, board.players.get(3).rank.shields);



    }
}