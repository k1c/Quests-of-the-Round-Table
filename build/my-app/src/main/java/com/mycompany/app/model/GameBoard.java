package com.mycompany.app.model;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		init_Game();					
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

	public List<Integer> getPlayerIds(){
		List<Integer> ids = new ArrayList<Integer>();
		for(Player player : this.players)
			ids.add(player.id());
		return ids;
	}

	public List<Card> getPlayerHand(int id){
		Player p = findPlayer(id);	
		if (p == null)
			return new ArrayList<Card>();
		return copyAdventureCards(p.hand);
	}

	public List<Card> getPlayerToBePlayed(int id){
		Player p = findPlayer(id);	
		if (p == null)
			return new ArrayList<Card>();
		return copyAdventureCards(p.toBePlayed);
	}

	public List<Card> getPlayerInPlay(int id){
		Player p = findPlayer(id);	
		if (p == null)
			return new ArrayList<Card>();
		return copyAdventureCards(p.inPlay);
	}

	protected List<Card> copyAdventureCards(List<AdventureCard> hand){
		List<Card> cards = new ArrayList<Card>();
		for(Card card : hand)
			cards.add(card.instance());
		return cards;
	}


	protected Player findPlayer(int id){
		for(Player p : players)
			if (p.id() == id)
				return p;
		return null;
	}


	

}
