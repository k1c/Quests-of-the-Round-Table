package com.mycompany.app.model;

import com.mycompany.app.model.DefaultBehaviourTest;
import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.AdventureBehaviour;
import junit.framework.TestCase;
import java.util.*;


public class DefaultBehaviourTest extends TestCase{

    String[] dummyNames = {"a", "b", "c", "d"};

    public DefaultBehaviourTest(String name){
        super(name);
    }

    public void testDefaultBehaviour3Param() throws Exception{
	GameBoard board = new GameBoard();
	board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards() ,new ArrayList<StoryCard>());

        AdventureBehaviour Temp = new DefaultBehaviour(10, 2, true);
        assertEquals(10, Temp.getBP(board));
        assertEquals(2, Temp.getBids(board));
        assertTrue(Temp.isFreeBid(board));

    }

    public void testDefaultBehaviour2Param() throws Exception{
	GameBoard board = new GameBoard();
	board.initGame(4, 0, dummyNames, CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());

        AdventureBehaviour Temp = new DefaultBehaviour(30, 0);
        assertEquals(30, Temp.getBP(board));
        assertEquals(0, Temp.getBids(board));
        assertFalse(Temp.isFreeBid(board));

    }


}


