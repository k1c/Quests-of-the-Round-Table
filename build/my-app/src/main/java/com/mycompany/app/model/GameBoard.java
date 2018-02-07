package com.mycompany.app.model;

import java.util.ArrayList;
import java.lang.Math;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.Player;
import com.mycompany.app.model.StoryCard;

public class GameBoard{
	private final int MIN_PLAYERS = 2;
	private final int MAX_PLAYERS = 4;

	private static GameBoard board = null;

	protected ArrayList<AdventureCard> 	adventureDeck;
	protected ArrayList<AdventureCard> 	adventureDeckDiscard;
	protected ArrayList<StoryCard> 		storyDeck;
	protected ArrayList<StoryCard> 		storyDeckDiscard;
	protected ArrayList<Player>		players;

	private GameBoard(){
			
	}

	public static GameBoard getInstance(){
		if(board == null)
			board = new GameBoard();
		return board;
	}

	public void init_Game(){
		init_Game(MAX_PLAYERS);
	}

	public void init_Game(int num){
		num = Math.min(Math.max(MAX_PLAYERS,num),MIN_PLAYERS);

		this.adventureDeck 	  = new ArrayList<AdventureCard>();	
		this.adventureDeckDiscard = new ArrayList<AdventureCard>();	
		this.storyDeck		  = new ArrayList<StoryCard>();	
		this.storyDeckDiscard 	  = new ArrayList<StoryCard>();
		this.players 		  = new ArrayList<Player>();

		for(int i = 0; i < num; i++)
			this.players.add(new Player());
	}

	

}
