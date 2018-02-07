package com.mycompany.app.model;

import java.util.ArrayList;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.StoryCard;

public class GameBoard{
	private static GameBoard board = null;

	protected ArrayList<AdventureCard> 	adventureDeck;
	protected ArrayList<AdventureCard> 	adventureDeckDiscard;
	protected ArrayList<StoryCard> 		storyDeck;
	protected ArrayList<StoryCard> 		storyDeckDiscard;

	private GameBoard(){
		this.adventureDeck 	  = new ArrayList<AdventureCard>();	
		this.adventureDeckDiscard = new ArrayList<AdventureCard>();	
		this.storyDeck		  = new ArrayList<StoryCard>();	
		this.storyDeckDiscard 	  = new ArrayList<StoryCard>();	
	}

	public static GameBoard getInstance(){
		if(board == null)
			board = new GameBoard();
		return board;
	}

	

}
