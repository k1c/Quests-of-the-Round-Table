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

	public void test1() throws Exception{
		GameBoard board = new GameBoard();	
		int numHumans = 4;
		int numAI= 0;
		String[] humanNames = dummyNames;

	        board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());


		assertEquals(4,board.players.size());
	}



}
