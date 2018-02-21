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

	protected StoryCard eventKingsRecognition;
	protected StoryCard currentStory;
	protected int currentQuestIndex;

	protected TwoDimensionalArrayList<AdventureCard> quest;

	public void initGame(int num, List<AdventureCard> ad, List<StoryCard> sd){

		if( num > MAX_PLAYERS || num < MIN_PLAYERS)
			num = MIN_PLAYERS;


		this.currentStory = null;
		this.currentQuestIndex = null;



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
				temp.add(p.genericPlayer(this));
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

		resetStory();

		currentStory = storyDeck.remove(storyDeck.size()-1);
	}

	public void applyStoryCardLogic(int player){
		currentStory.apply(this,player);		
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

	protected void resetStory(){
		//add more things to reset later		
		if(currentStory != null){
			storyDeckDiscard.add(currentStory);	
			currentStory = null;
		}
		
	}

	public void nextStage(){
		this.currentQuestIndex = Math.max(this.currentQuestIndex,this.quest.size()-1);
	}

	public boolean playerCanSponsor(int id){
		Player p = findPlayer(id);	
		Set<Integer>	bp = new TreeSet<Integer>();
		int numberOfTests = 0;

		for(AdventureCard c : p.hand){
			if(c.type == Card.Types.FOE)
				bp.add(c.getBattlePoints(this));
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

	protected void resetQuest(){
		adventureDeckDiscard.addAll(this.quest.toList());
		this.quest = new TwoDimensionalArrayList();		
		this.sponsor = null;
		this.currentQuestIndex = null;
	}

	public boolean submitQuest(TwoDimensionalArrayList<Card> playerQuest,int player){
		Player p = findPlayer(player);	
		TwoDimensionalArrayList<AdventureCard> quest = new TwoDimensionalArrayList<AdventureCard>();
		List<AdventureCard> tempPlayerHand = new ArrayList(p.hand);

		boolean validStage = true;
		boolean validBP    = true;
		boolean validHand  = true;

		int testNumber = 0;
		int lastBP = 0;

		//create an adventure card copy
		int stage = 0;

		for(ArrayList<Card> stageList : playerQuest){
			for(Card item : stageList){
				AdventureCard temp = findCard(p.hand,item);
				if(temp == null)
					return false;
				quest.addToInnerArray(stage,temp);
			}
			stage++;
		}

		List<AdventureCard> questCards = quest.toList();

		//the player has all quest cards selected
		for(AdventureCard card : questCards){
			validHand = validHand && tempPlayerHand.remove(card);
		}
				
		//validate each quest
		for(ArrayList<AdventureCard> stageList : quest){

			int currentBP = calculateBP(stageList);
			validStage = validateStage(stageList) && validStage;

			if(stageHas(stageList,Card.Types.TEST)){
				testNumber++;
			}

			if(stageHas(stageList,Card.Types.FOE) && currentBP >= lastBP){
				validBP = false;
			}
		}

		//any stage has invalid setup
		if(!validStage)
			return false;
		//too many tests
		if(testNumber > 1)
			return false;
		//BP does not follow BP order
		if(!validBP)
			return false;
		//Player does not have the hand to support quest
		if(!validHand)
			return false;


		//submit final changes
		resetQuest();
		p.hand = tempPlayerHand;	
		this.quest = quest;
		this.sponsor = p;
		this.currentQuestIndex = 0;

		return true;
	}

	protected int calculateBP(List<AdventureCard> list){
		int BP = 0;

		for(AdventureCard item: list){
			BP += item.getBattlePoints(this); 	
		}

		return BP;
	}

	protected boolean stageHas(List<AdventureCard> stage,Card.Types type){
		for(AdventureCard item : stage){
			if(item.type == type)
				return true;
		}
		return false;
	}

	protected boolean validateStage(List<AdventureCard> stage){
		int foeNumber = 0;
		int testNumber = 0;
		int weaponNumber = 0;
		Set<AdventureCard> cardSet = new TreeSet();

		for(AdventureCard item : stage){
			cardSet.add(item);

			if(item.type == Card.Types.FOE)
				foeNumber++;
			if(item.type == Card.Types.TEST)
				testNumber++;
			if(item.type == Card.Types.WEAPON)
				weaponNumber++;
		}

		//the stage is not unique
		if(cardSet.size() != stage.size())
			return false;
		//the stage does not have a foe nor a test
		if(foeNumber != 1 || testNumber != 1)
			return false;
		//the stage has too many tests for 1 foe
		if(foeNumber == 1 && testNumber > 0)
			return false;
		//the stage has too many foes for 1 test 
		if(testNumber == 1 && stage.size() > 1)
			return false;
		//there only exists foes weapons and tests
		if((foeNumber+testNumber+weaponNumber) != stage.size())
			return false;

		return true;
	}

	protected AdventureCard findCard(List<AdventureCard> list, Card card){
		for(AdventureCard item : list){
			Card temp = item;
			if(card.equals(temp)){
				return item;		
			}
		}
		return null;
	}

	public void addParticipants(List<Integer> players){
		List<Player> temp = new ArrayList();
		for(Integer p : players){
			temp.add(findPlayer(p));	
		}
		this.participants = temp;
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
			temp.players.add(player.genericPlayer(this));
		}

		temp.numCardsAdventure = this.adventureDeck.size();
		temp.numCardsAdventureDiscard = this.adventureDeckDiscard.size();

		temp.numCardsStory = this.storyDeck.size();
		temp.numCardsStoryDiscard = this.storyDeckDiscard.size();

		return temp;

	}


	public GenericPlayer getGenericPlayer(int id){
		Player p = findPlayer(id);
		return p.genericPlayer(this);
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
