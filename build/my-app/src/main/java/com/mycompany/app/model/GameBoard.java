package com.mycompany.app.model;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.util.*;

public class GameBoard extends AbstractGameBoard{
	protected List<AdventureCard> 	adventureDeck;
	protected List<AdventureCard> 	adventureDeckDiscard;

	protected List<StoryCard> 	storyDeck;
	protected List<StoryCard> 	storyDeckDiscard;

	protected List<Player>		players;
	protected List<AbstractAI>	ais;
	protected List<Player> 		participants;

	protected Player		sponsor;

	protected StoryCard eventKingsRecognition;
	protected StoryCard currentStory;
	protected int currentQuestIndex;
	protected int currentTournamentStage;
	protected int numParticipants;
	GameLogger log = GameLogger.getInstanceUsingDoubleLocking();


	protected TwoDimensionalArrayList<AdventureCard> quest;

	public List<Player> getParticipantPlayers(){
		List<Player> participants = this.participants;
		return participants;
	}

	public void initGame(int numHumans, int numAI, String[] names, List<AdventureCard> ad, List<StoryCard> sd){
		log.gameState("Game Board Screen");

	    int num = numHumans+numAI;

		if( num > MAX_PLAYERS || num < MIN_PLAYERS)
			num = MIN_PLAYERS;



		this.currentStory = null;
		this.currentQuestIndex = 0;
		this.currentTournamentStage = 0;



		//assume that the model will not modify the loaded data
		this.adventureDeck 	  = ad;
		this.storyDeck		  = sd;
		this.adventureDeckDiscard = new ArrayList<AdventureCard>();	
		this.storyDeckDiscard 	  = new ArrayList<StoryCard>();
		this.players 		  = new ArrayList<Player>();
		this.participants 	  = new ArrayList<Player>();
		this.ais		  = new ArrayList();

		Collections.shuffle(adventureDeck);
		Collections.shuffle(storyDeck);

		log.gameState("Adventure Deck Shuffled");
		log.gameState("Story Deck Shuffled");
		log.count("Total Players",num);
		log.count("Humans",numHumans);
		log.count("AIs", numAI);

		String[] shieldImages = {"Shield Blue.png", "Shield Red.png", "Shield Green.png", "Shield Purple.png"};

		for(int i = 0; i < numHumans; i++) {
			this.players.add(new HumanPlayer(names[i], shieldImages[i]));
			log.objectCreation("Player","Player "+ players.get(players.size()-1).id() + " is named " + names[i]);
		}

		for(int i = 0; i < numAI; i++) {
			AbstractAI ai = new AIDefault("AI " + (i+1), shieldImages[i+numHumans]);
			this.players.add(ai);
			this.ais.add(ai);
			log.objectCreation("Player", "Player "+ players.get(players.size()-1).id() + " is named AI " + (i + 1));
		}

		for(int i = 0; i < INITIAL_CARDS; i++){
			for(Player p : players) {
				/*//placeholder
				if (i<5){
					p.inPlay.add(adventureDeck.remove(adventureDeck.size()-1));
				}
				//placeholder*/
				drawFromAdventureDeck(p);
			}
		}
	}

	public void initRig(List<AbstractAI> ai, List<HumanPlayer> hu,List<AdventureCard> ad, List<StoryCard> sd,boolean shuffle_adventure,boolean shuffle_story,boolean addCards){
		log.action("initRig","Rigging Game","");

		this.adventureDeck = new ArrayList<>();
		this.storyDeck = new ArrayList<>();
		this.adventureDeckDiscard = new ArrayList<AdventureCard>();
		this.storyDeckDiscard 	  = new ArrayList<StoryCard>();
		this.participants 	  = new ArrayList<Player>();
		this.players = new ArrayList<>();
		this.ais  = new ArrayList<>();

		this.currentStory = null;
		this.currentQuestIndex = 0;
		this.currentTournamentStage = 0;

		this.players.addAll(hu);
		this.players.addAll(ai);
		log.action("initRig","Adding Players : ",this.players);

		this.ais.addAll(ai);

		log.action("initRig","Adding Deck","");
		this.adventureDeck.addAll(ad);
		this.storyDeck.addAll(sd);

		if(shuffle_story){
			log.action("initRig","Shuffling Story","");
			Collections.shuffle(this.storyDeck);
		}

		if(shuffle_adventure){
			log.action("initRig","Shuffling Adventure","");
			Collections.shuffle(this.adventureDeck);
		}

		if(addCards){
			log.action("initRig","Adding Cards",this.players);
			for(Player p : this.players){
				for(int num=p.hand.size(); num < 12; num=p.hand.size()){
					drawFromAdventureDeck(p);
				}
			}
		}

	}

	public int getQuestIndex(){
		log.action("getQuestIndex","Getting Quest Index",this.currentQuestIndex);
		return this.currentQuestIndex;
	}

	public int getCurrentQuestBP(){
		int accumulator = 0;
		for(AdventureCard item : this.quest.get(this.currentQuestIndex)){
			accumulator += item.getBattlePoints(this);
		}
		log.action("getCurrentQuestBP","Current Quest BP",accumulator);
		return accumulator;
		
	}

	public List<Card> getStage(int index){
		if(index < 0 || index >= quest.size()){
			return null;
		}
		if(quest == null){
			return null;
		}

		List<Card> temp = new ArrayList();
		temp.addAll(quest.get(index));
		return temp;
	}


	public AbstractAI getAI(int id){
		log.action("getAI","Getting Ai","");
		for(AbstractAI ai : ais){
			if(ai.id() == id){
				log.action("getAI","AI found",ai);
				return ai;
			}
		}

		log.action("getAI","No AI found","");
		return null;
	}
	public boolean playerIsAI(int id){
		log.action("playerIsAI","is player AI",findPlayer(id));
		for(Player p : players){
			if(id == p.id() && p.type == AbstractPlayer.Type.AI){
				log.action("playerIsAI","is AI",p);
				return true;
			}
		}
		log.action("playerIsAI","player is not AI",findPlayer(id));
		return false;
	}

	public List<Integer> playersToDiscard(){
		log.action("playersToDiscard","finding players over 12","");
		List<Integer> temp = new ArrayList();	
		for(Player p : players){
			if(p.hand.size() > 12){
				temp.add(p.id());	
			}		
		}
		log.action("playersToDiscard","players ",temp);
		return temp;
	}

	public List<GenericPlayer> winningPlayers(){
		log.action("winningPlayers","finding winning players","");
		List<GenericPlayer> temp = new ArrayList();
		for(Player p : players){
			if(p.rank.getRank() == Rank.RankType.KNIGHT_OF_THE_ROUND_TABLE)
				temp.add(p.genericPlayer(this));
		}

		log.action("winningPlayers","players",temp);
		return temp;
	}

	protected void drawFromAdventureDeck(Player p){
		log.action("drawFromAdventureDeck","drawing player",p);

		if(adventureDeck.size() <= 0){
			log.action("drawFromAdventureDeck","reshuffling deck","");
			Collections.shuffle(adventureDeckDiscard);
			List<AdventureCard> temp = adventureDeck;
			adventureDeck = adventureDeckDiscard;
			adventureDeckDiscard = temp;
		}
		if(adventureDeck.size() <= 0){
			log.action("drawFromAdventureDeck","Error -- No Cards","");
			return;
		}

		AdventureCard ac = adventureDeck.remove(adventureDeck.size()-1);
		p.hand.add(ac);
		log.playerCard(p,ac,"Adventure Deck");

	}

	protected void drawFromStoryDeck(Player p){

		log.action("drawFromStoryDeck","drawing card from story",p);
		resetStory();

		if(storyDeck.size() <= 0){
			log.action("drawFromStoryDeck","reshuffling deck","");
			Collections.shuffle(storyDeckDiscard);
			List<StoryCard> temp = storyDeck;
			storyDeck = storyDeckDiscard;
			storyDeckDiscard = temp;
		}
		if(storyDeck.size() <= 0){
			log.action("drawFromStoryDeck","Error -- No Cards","");
			return;
		}


		currentStory = storyDeck.remove(storyDeck.size()-1);
		log.playerCard(p,currentStory,"Story Deck");
	}

	public void applyStoryCardLogic(int player) {

		log.action("applyStoryCardLogic","applying story card end logic",this.currentStory);

		currentStory.apply(this, player);
		resetQuest();

		for(Player p: this.players){
			resetTypeInPlay(p,Card.Types.AMOUR);
			resetTypeInPlay(p,Card.Types.WEAPON);
		}
	}
	
	public void drawFromStoryDeck(int id){
		Player p = findPlayer(id);

		if(p == null){
			return;
		}

		drawFromStoryDeck(p);

	}

	protected void resetInPlay(){
		log.action("resetInPlay","","");
		for(Player p : players){
			adventureDeckDiscard.addAll(p.inPlay);
			p.inPlay.clear();
		}
	}

	protected void resetToBePlayed(){
		log.action("resetToBePlayed","","");
		for(Player p : players){
			adventureDeck.addAll(p.toBePlayed);
			p.toBePlayed.clear();
		}
	}

	protected void resetStory(){
		//add more things to reset later		
		log.action("resetStory","","");
		if(currentStory != null){
			storyDeckDiscard.add(currentStory);	
			currentStory = null;
		}
	}

	public boolean nextStage(){
		log.action("nextStage","","");
		if(this.quest.size()-1 <= this.currentQuestIndex){
		log.action("nextStage","no more stages","");
			return false;
		}
		this.currentQuestIndex = this.currentQuestIndex+1;
		log.action("nextStage","next stage",this.currentQuestIndex);
		return true;
	}

	public boolean nextTournament(){
		currentTournamentStage++;
		log.action("nextTournament","continue",!(this.participants.size() == 1) && (this.currentTournamentStage < 2));
		return !(this.participants.size() == 1) && (this.currentTournamentStage < 2);
	}

	public void completeTournamentStage(){

		log.action("completeTournamentStage","","");
		List<Player> tempParticipants = new ArrayList();
		List<Player> droppedPlayers = new ArrayList();
		int maxBP = Integer.MIN_VALUE;
		numParticipants = this.participants.size();

		// all to be played go into in play
		// find max player 
		for(Player participant : this.participants){
			participant.inPlay.addAll(participant.toBePlayed);
			participant.toBePlayed.clear();
			maxBP = Math.max(participant.getTotalBP(this),maxBP);
		}

		simulateBattle(tempParticipants, droppedPlayers, maxBP,"Tournament");
		cleanUpCards(tempParticipants,droppedPlayers,"Tournament");

		this.participants = tempParticipants;
		log.action("completeTournamentStage","remaining participants",this.participants);
	}


	public boolean playerCanSponsor(int id){


		Player p = findPlayer(id);
		Set<Integer>	bp = new TreeSet<Integer>();
		int numberOfTests = 0;

		log.action("playerCanSponsor","possible?",p);

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
		log.action("playerCanSponsor","",bp.size() + numberOfTests >= currentStory.getNumStages());
		return bp.size() + numberOfTests >= currentStory.getNumStages();
	}

	protected void resetQuest(){
		log.action("resetQuest","","");

		if(this.quest != null) {
			log.action("resetQuest","discard current quest",quest);
			adventureDeckDiscard.addAll(this.quest.toList());
			this.quest.clear();
		}

		log.action("resetQuest","reset other variables","");
		this.sponsor = null;
		this.currentQuestIndex = 0;
		this.currentTournamentStage = 0;
		this.participants = new ArrayList();
	}

	public boolean discardHand(int player, List<Card> hand){

		Player p = findPlayer(player);
		log.action("discardHand","",p);

		boolean validHand   = true;

		List<AdventureCard> tempPlayerHand = new ArrayList(p.hand);
		List<AdventureCard> submittedCards  = new ArrayList();

		List<AdventureCard> allies   = new ArrayList();
		List<AdventureCard> discards = new ArrayList();

		for(Card item: hand){
			AdventureCard temp = findCard(p.hand,item);
			if(temp == null) {
				return false;
			}
			submittedCards.add(temp);
		}

		for(AdventureCard card : submittedCards){
			validHand = validHand && tempPlayerHand.remove(card);
			if(card.type == Card.Types.ALLY){
				allies.add(card);
			}
			else{
				discards.add(card);
			}
		}
				


		 if(!validHand) {
			log.action("discardHand","Invalid Submitted Hand",p);
			log.action("discardHand","Invalid Submitted Hand",hand);
			 return false;
		 }
		
		log.action("discardHand","discarding",discards);
		log.action("discardHand","playing",allies);

		adventureDeckDiscard.addAll(discards);
		p.inPlay.addAll(allies);
		p.hand = tempPlayerHand;

		return true;
	}


	public boolean submitHand(int player, List<Card> hand){


		Player p = findPlayer(player);	

		log.action("submitHand","",p);

		boolean validHand   = true;
		boolean duplicates  = true;
		boolean correctType = true;

		List<AdventureCard> tempPlayerHand = new ArrayList(p.hand);
		List<AdventureCard> submittedCards  = new ArrayList();
		Set<AdventureCard>  set = new TreeSet();

		for(Card item: hand){
			AdventureCard temp = findCard(p.hand,item);
			if(temp == null) {
				return false;
			}
			submittedCards.add(temp);
		}

		//the player has all quest cards selected
		for(AdventureCard card : submittedCards){
			validHand = validHand && tempPlayerHand.remove(card);
		}
				
		correctType = !(cardListHas(submittedCards,Card.Types.ALLY)&&
		   	      cardListHas(submittedCards,Card.Types.AMOUR)&&
	 	   	      cardListHas(submittedCards,Card.Types.WEAPON));

		set.addAll(submittedCards);
		set.addAll(p.inPlay);

		duplicates = (set.size() == (submittedCards.size() + p.inPlay.size()));

		 if(!validHand) {
			 log.action("submitHand","Invalid Hand",p);
			 return false;
		 }
		 if(!correctType) {
			 log.action("submitHand","Incorrect Types",p);
			 return false;
		 }
		 if(!duplicates) {
			 log.action("submitHand","Duplicates",p);
			 return false;
		 }
		
		 p.toBePlayed = submittedCards;		
		 p.hand = tempPlayerHand;

		 log.action("submitHand","to be played",p.toBePlayed);

		 return true;
	}

	public boolean submitQuest(TwoDimensionalArrayList<Card> playerQuest,int player){
		Player p = findPlayer(player);	

		log.action("submitQuest","tries to submit",p);
		TwoDimensionalArrayList<AdventureCard> quest = new TwoDimensionalArrayList<AdventureCard>();
		List<AdventureCard> tempPlayerHand = new ArrayList(p.hand);
		Set<AdventureCard> questIntegrity = new TreeSet();

		boolean validStage = true;
		boolean validBP    = true;
		boolean validHand  = true;
		boolean correctNumberOfStages = true;
		boolean validFoeTestIntegrity = true;

		int testNumber = 0;
		int lastBP = 0;

		//create an adventure card copy
		int stage = 0;


		correctNumberOfStages = currentStory.getNumStages() == playerQuest.size();

		for(ArrayList<Card> stageList : playerQuest){
			for(Card item : stageList){
				AdventureCard temp = findCard(p.hand,item);
				if(temp == null) {
					return false;
				}
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


			questIntegrity.addAll(getListTypes(stageList,Card.Types.TEST));	
			questIntegrity.addAll(getListTypes(stageList,Card.Types.FOE));	

			int currentBP = calculateBP(stageList);
			validStage = validateStage(stageList) && validStage;

			if(cardListHas(stageList,Card.Types.TEST)){
				testNumber++;
			}

			if(cardListHas(stageList,Card.Types.FOE) && currentBP > lastBP){
				lastBP = currentBP;
			}
			else if (cardListHas(stageList, Card.Types.FOE)){
				validBP = false;
			}
		}

		validFoeTestIntegrity = questIntegrity.size() == currentStory.getNumStages();
	
		
		if(!validFoeTestIntegrity){
			log.action("submitQuest","Invalid : Same type of foe",p);
			return false;
		}
		
		if(!correctNumberOfStages){
			log.action("submitQuest","Invalid : Incorrect Number Of Stages",p);
			return false;
		}
		//any stage has invalid setup
		if(!validStage) {
			log.action("submitQuest","Invalid : Incorrect Stage",p);
			return false;
		}
		//too many tests
		if(testNumber > 1) {
			log.action("submitQuest","Invalid : Too Many Tests",p);
			return false;
		}
		//BP does not follow BP order
		if(!validBP) {
			log.action("submitQuest","Invalid : Invalid BP Ordering",p);
			return false;
		}
		//Player does not have the hand to support quest
		if(!validHand) {
			log.action("submitQuest","Invalid : Hand Mis-match",p);
			return false;
		}


		//submit final changes
		resetQuest();

		p.hand = tempPlayerHand;
		this.quest = quest;
		this.sponsor = p;
		this.currentQuestIndex = 0;

		log.action("submitQuest","Quest :",quest);

		return true;
	}

	public void beginEncounter(){
		log.action("beginEncounter","","");
		for(Player p : participants){
			drawFromAdventureDeck(p);
		}
	}

	public void endQuest(){
		// remove weapons and amours from allies
		/*
		for(Player participant : this.participants){
			resetTypeInPlay(participant,Card.Types.WEAPON);	
			resetTypeInPlay(participant,Card.Types.AMOUR);	
		}
		
		this.currentStory.apply(this,this.sponsor.id());		

		//remove any quest information
		resetQuest();
		*/
		this.applyStoryCardLogic(this.sponsor.id());
	}

	public void completeFoeStage(){

		List<AdventureCard> quest = this.quest.get(currentQuestIndex);
		List<Player> tempParticipants = new ArrayList();
		List<Player> droppedPlayers = new ArrayList();
		int questBP = 0;

		// get total BP
		for(AdventureCard card : quest){
			questBP += card.getBattlePoints(this);
		}

		// play all cards to be played
		for(Player participant : this.participants){
			participant.inPlay.addAll(participant.toBePlayed);
			participant.toBePlayed.clear();
		}

		simulateBattle(tempParticipants, droppedPlayers, questBP,"Foe");
		cleanUpCards(tempParticipants,droppedPlayers, "Foe");

		this.participants = tempParticipants;

	}

	public boolean submitBids(int player, List<Card> hand){
		Player p = findPlayer(player);

		log.action("submitBids","bid submit",p);

		boolean validHand   = true;
		boolean amourInPlay = false;
		int testBids = 0;
		boolean validBid = false;

		List<AdventureCard> tempPlayerHand = new ArrayList(p.hand);
		List<AdventureCard> tempToBePlayed = new ArrayList(p.toBePlayed);
		List<AdventureCard> tempInPlay = new ArrayList(p.inPlay);
		List<AdventureCard> submittedCards  = new ArrayList();

		amourInPlay = cardListHas(tempInPlay,Card.Types.AMOUR);

		for(AdventureCard card : quest.get(currentQuestIndex)){
			testBids += card.getBids(this);
		}

		for(Card item: hand){
			AdventureCard temp = findCard(p.hand,item);
			if(temp == null)
				return false;
			submittedCards.add(temp);
		}

		//the player has all bid cards selected
		for(AdventureCard card : submittedCards){
			validHand = validHand && tempPlayerHand.remove(card);
		}

		if(!validHand){
			log.action("submitBids","Invalid : Hand",p);
			return false;
		}

		for(AdventureCard item : submittedCards){
			if(item.type == Card.Types.AMOUR && amourInPlay){
				tempToBePlayed.add(item);
			}
			if(item.type == Card.Types.AMOUR && !amourInPlay){
				tempInPlay.add(item);
			}
			else if(item.type != Card.Types.AMOUR && (item.freeBid(this) /*|| item.type == Card.Types.ALLY*/)){
				tempInPlay.add(item);
			}
			else{
				tempToBePlayed.add(item);
			}

		}

		log.action("submitBids","to bid discard",tempToBePlayed);
		log.action("submitBids","as free bid",tempInPlay);

		p.toBePlayed = tempToBePlayed;
		p.inPlay = tempInPlay;
		p.hand = tempPlayerHand;
		
		validBid = maxBidder(p) && totalPlayerBids(p) >= testBids;

		log.action("submitBids","enough to pass",validBid);

		return validBid;
	}

	protected int totalPlayerBids(Player p){
		int sum = p.toBePlayed.size();

		for(AdventureCard card : p.inPlay) {
			sum += card.getBids(this);
		}
		return sum;
	}

	public int maxBid(){

		int max = 0;
		for(Player participant : participants){
			max = Math.max(max,totalPlayerBids(participant));
		}
		return max;
	}


	protected boolean maxBidder(Player p){
		int max = 0;
		for(Player player : players){
			if(player == p){
				continue;
			}
			max = Math.max(totalPlayerBids(player), max);
		}
		return totalPlayerBids(p) > max;
	}


	protected boolean checkTestWinner(){
		int testBids = 0;
		for(AdventureCard card : quest.get(currentQuestIndex)){
			testBids += card.getBids(this);
		}

		if(participants.size() == 1) {
			log.action("checkTestWinner","is there winner ",totalPlayerBids(participants.get(0)) >= testBids);
			return totalPlayerBids(participants.get(0)) >= testBids;
		}
		log.action("checkTestWinner","is there winner ",participants.size() <= 0);
		return participants.size() <= 0;
	}

	protected void giveUp(Integer id){
		Player p = findPlayer(id);
		log.action("giveUp","player is giving up test",p);

		p.hand.addAll(p.toBePlayed);
		p.toBePlayed.clear();
		participants.remove(p);
		resetTypeInPlay(p, Card.Types.AMOUR);
	}


	public void completeTestStage(){
		
		log.action("completeTestStage","Cleaning up stage","");
		for (Player p : participants){
			this.adventureDeckDiscard.addAll(p.toBePlayed);
			p.toBePlayed.clear();
		}

	}



	public void resetTypeInPlay(Player p,Card.Types type){
		log.action("resetTypeInPlay","player discarding type",p);
		for(AdventureCard card : new ArrayList<>(p.inPlay)){
			if(card.type == type){
				log.action("resetTypeInPlay","player discards"+ p.toString(),card);
				adventureDeckDiscard.add(card);
				p.inPlay.remove(card);
			}
		}
	}

	protected int calculateBP(List<AdventureCard> list){
		int BP = 0;

		for(AdventureCard item: list){
			BP += item.getBattlePoints(this); 	
		}

		log.action("calculateBP","Battle Points",BP);

		return BP;
	}


	public boolean stageType(Card.Types type){
		return cardListHas(quest.get(this.currentQuestIndex),type);
	}

	protected boolean cardListHas(List<AdventureCard> stage,Card.Types type){
		for(AdventureCard item : stage){
			if(item.type == type)
				return true;
		}
		return false;
	}

	protected List<AdventureCard> getListTypes(List<AdventureCard> cards, Card.Types type){
		List<AdventureCard> temp = new ArrayList();
		for(AdventureCard item : cards){
			if(item.type == type)
				temp.add(item);
		}
		return temp;
			
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
		if(cardSet.size() != stage.size()) {
			log.action("validStage","Invalid : Stage is not unique",stage);
			return false;
		}
		//the stage does not have a foe nor a test
		if(foeNumber != 1 && testNumber != 1) {
			log.action("validStage","Invalid : Stage has no foes or tests",stage);
			return false;
		}
		//the stage has too many tests for 1 foe
		if(foeNumber == 1 && testNumber > 0) {
			log.action("validStage","Invalid : stage foe with a test",stage);
			return false;
		}
		if(foeNumber > 1){
			log.action("validStage","Invalid : Too many Foes",stage);
			return false;
		}
		//the stage has too many foes for 1 test 
		if(testNumber == 1 && stage.size() > 1) {
			log.action("validStage","Invalid : test with other items in stage",stage);
			return false;
		}
		//there only exists foes weapons and tests
		if((foeNumber+testNumber+weaponNumber) != stage.size()) {
			log.action("validStage","Invalid: invalid type in stage",stage);
			return false;
		}

		log.action("validStage","Valid Stage",stage);
		return true;
	}

	protected AdventureCard findCard(List<AdventureCard> list, Card card){
		for(AdventureCard item : list){
			Card temp = item;
			if(card.isSame(temp)){
				return item;		
			}
		}
		return null;
	}

	public void addParticipant(Integer player){
		this.participants.add(findPlayer(player));
	}

	public List<Integer> getParticipants(){
		List<Integer> temp = new ArrayList();
		for(Player p : this.participants){
			temp.add(p.id());
		}
		return temp;
	}
	public Card getCurrentStoryCard(){
		if (currentStory == null)
			return null;
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

	public int getCurrentQuestStages(){
		if(currentStory != null && currentStory.type == Card.Types.QUEST){
			return currentStory.getNumStages();
		}
		return 0;
	}

	public int getNumQuestCards(){
		int counter = 0;
		for(ArrayList<AdventureCard> stage : quest) {
			for (Card item : stage) {
				counter++;
			}
		}
		return counter;
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

	//helper functions

	private void cleanUpCards(List<Player> tempParticipants, List<Player> droppedPlayers, String location){
		// clean up the cards
		for(Player participant : tempParticipants){
			log.action("complete" + location + "Stage","clearing weapons",participant);
			resetTypeInPlay(participant,Card.Types.WEAPON);
		}

		for(Player participant : droppedPlayers){
			log.action("complete" + location + "Stage","clearing dropped weapons and amour",participant);
			resetTypeInPlay(participant,Card.Types.WEAPON);
			resetTypeInPlay(participant,Card.Types.AMOUR);
		}
	}

	private void simulateBattle(List<Player> tempParticipants, List<Player> droppedPlayers, int bpToBeat, String location){
		// simulate the battle
		for (Player participant : this.participants) {
			if (participant.getTotalBP(this) >= bpToBeat) {
				tempParticipants.add(participant);
				log.action("complete" + location + "Stage", "player continues", participant);
			} else {
				droppedPlayers.add(participant);
				log.action("complete" + location + "Stage", "player dropped", participant);
			}
		}
	}
}
