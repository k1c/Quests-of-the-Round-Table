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
	protected List<Player> 		participants;

	protected Player		sponsor;


	protected StoryCard currentStory;

	public void initGame(int num, List<AdventureCard> ad, List<StoryCard> sd){

		if( num > MAX_PLAYERS || num < MIN_PLAYERS)
			num = MIN_PLAYERS;


		this.currentStory = null;



		//assume that the model will not modify the loaded data
		this.adventureDeck 	  = ad;
		this.storyDeck		  = sd;
		this.adventureDeckDiscard = new ArrayList<AdventureCard>();	
		this.storyDeckDiscard 	  = new ArrayList<StoryCard>();
		this.players 		  = new ArrayList<Player>();
		this.participants 	  = new ArrayList<Player>();

		Collections.shuffle(adventureDeck);
		Collections.shuffle(storyDeck);

		for(int i = 0; i < num; i++)
			this.players.add(new Player());

		for(int i = 0; i < INITIAL_CARDS; i++){
			for(Player p : players) {
				drawFromAdventureDeck(p);
			}
		}
	}

	public void rigGame(){

	}
	public void loadGame(){

	}

	public List<GenericPlayer> winningPlayers(){
		List<GenericPlayer> temp = new ArrayList();
		for(Player p : players){
			if(p.rank.getRank() == Rank.RankType.KNIGHT_OF_THE_ROUND_TABLE)
				temp.add(p.genericPlayer());
		}
		return temp;
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

		//if (p.hand.size > 12) discardAdventureCards()
	}

	protected void drawFromStoryDeck(Player p){
		if(storyDeck.size() <= 0){
			Collections.shuffle(storyDeckDiscard);
			List<StoryCard> temp = storyDeck;
			storyDeck = storyDeckDiscard;
			storyDeckDiscard = temp;
		}
		if(storyDeck.size() <= 0)
			return;

		softResetGameBoard();	
		
		currentStory = storyDeck.remove(storyDeck.size()-1);
	}
	
	public void drawFromStoryDeck(int id){
		Player p = findPlayer(id);

		if(p == null)
			return;

		drawFromStoryDeck(p);

	}

	protected void resetInPlay(){
		for(Player p : players){
			adventureDeckDiscard.addAll(p.inPlay);
			p.inPlay.clear();
		}
	}

	protected void resetToBePlayed(){
		for(Player p : players){
			adventureDeck.addAll(p.toBePlayed);
			p.toBePlayed.clear();
		}
	}

	protected void softResetGameBoard(){
		//add more things to reset later		
		if(currentStory != null){
			storyDeckDiscard.add(currentStory);	
			currentStory = null;
		}
		
	}

	public boolean playerCanSponsor(int id){
		Player p = findPlayer(id);	
		Set<Integer>	bp = new TreeSet<Integer>();
		int numberOfTests = 0;

		for(AdventureCard c : p.hand){
			if(c.type == Card.Types.FOE)
				bp.add(c.getBattlePoints());
			if(c.type == Card.Types.TEST)
				numberOfTests++;

		}
		if (numberOfTests > 0)
			numberOfTests = 1;
		/*
		 * Replace the '2' with the current Story stages
		 */
		return bp.size() + numberOfTests >= 2;
	}

	public Card getCurrentStoryCard(){
		return ((Card)currentStory).instance();
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
