package com.mycompany.app.model;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Iterator;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.Card;
import com.mycompany.app.model.GameBoard;
import com.mycompany.app.model.Player;
import com.mycompany.app.model.GenericPlayer;
import com.mycompany.app.model.StoryCard;
import java.lang.*;
import java.util.*;

public class GameBoard extends AbstractGameBoard{
	protected List<AdventureCard> 	adventureDeck;
	protected List<AdventureCard> 	adventureDeckDiscard;
	protected List<StoryCard> 	storyDeck;
	protected List<StoryCard> 	storyDeckDiscard;
	protected List<Player>		players;

	public void initGame(int num, List<AdventureCard> ad, List<StoryCard> sd){

		if( num > MAX_PLAYERS || num < MIN_PLAYERS)
			num = MIN_PLAYERS;

		//assume that the model will not modify the loaded data
		this.adventureDeck 	  = ad;
		this.storyDeck		  = sd;
		this.adventureDeckDiscard = new ArrayList<AdventureCard>();	
		this.storyDeckDiscard 	  = new ArrayList<StoryCard>();
		this.players 		  = new ArrayList<Player>();

		Collections.shuffle(adventureDeck);
		Collections.shuffle(storyDeck);

		for(int i = 0; i < num; i++)
			this.players.add(new Player());

		for(int i = 0; i < 12; i++)
			for(Player p : players)
				drawFromAdventureDeck(p);
	}

	public void loadGame(){

	}

	protected void drawFromAdventureDeck(Player p){
		if(adventureDeck.size() <= 0){
			Collections.shuffle(adventureDeckDiscard);
			List<AdventureCard> temp = adventureDeck;
			adventureDeck = adventureDeckDiscard;
			adventureDeckDiscard = temp;
		}
		if(adventureDeck.size() <= 0)
			return;

		p.hand.add(adventureDeck.remove(adventureDeck.size()-1));

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

	public ViewGameBoard getViewCopy(){
		ViewGameBoard temp = new ViewGameBoard();	

		for(Player player:this.players){
			temp.players.add(player.genericPlayer());
		}

		temp.numCardsAdventure = this.adventureDeck.size();
		temp.numCardsAdventureDiscard = this.adventureDeckDiscard.size();

		temp.numCardsStory = this.storyDeck.size();
		temp.numCardsStoryDiscard = this.storyDeckDiscard.size();

		return temp;

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
