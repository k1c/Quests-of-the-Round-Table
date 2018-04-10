package com.mycompany.app.model;


import 	java.util.*;
import java.util.*;
import java.util.stream.Collectors;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.DataStructures.Cycle;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;
import com.mycompany.app.model.Interfaces.GameObserver;


public class GameModel{

	protected List<GameObserver> observers;
	protected GameStates state;

	//for when there is a discard interrupt
	protected GameStates savedState;
	protected int savedIndex;

	protected int numberOfPlayers;

	//Active Story Player
	protected int currentPlayer;
	
	//all Players In Game
	protected List<Integer> players;

	//Turn Number
	protected int turn;
	protected GameBoard board;

	//current player
	protected Cycle<Integer> storyTurn;
	protected Cycle<Integer> questSponsor;
	protected Cycle<Integer> participants;
	protected Cycle<Integer> discard;

	protected Cycle<Integer> currentPlayers;
	protected int participationCounter;
	protected GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

	protected GameState gameState;
	protected DiscardState discardState;




	public GameModel(){
		observers = new ArrayList<GameObserver>();
		board = new GameBoard();
		gameState = new GameStateInit(this);
		discardState = new DiscardNone(this);
	}

	public int getStageBp(){
		return board.getCurrentQuestBP();
	}

	public int getStageIndex(){
		return board.getQuestIndex();
	}

	public int getNumDiscards() {
	    	return discard.size();
	}

	public void initGame(int numHumans,int ai_type1,int ai_type2,String[] humanNames){
		gameState.newGame(numHumans,ai_type1,ai_type2,humanNames);	
	}

	public void rigGame1(){}

	public void rigGame2(){}

	public void rigGame3(){}

	public void registerObserver(GameObserver o){
		this.observers.add(o);
	}

	public void deregisterObserver(GameObserver o){
		this.observers.remove(o);
	}

	public void updateObservers(){
		for(GameObserver observer : observers){
			observer.update();
		}
	}

	public int getNumberOfStages(){
		return board.getCurrentQuestStages();
	}

	public ViewGameBoard getGameBoard(){
		return board.getViewCopy();
	}

	/* LEGACY */
	public GameStates getState() {
		if(discardState.getState() != GameStates.DISCARD_NONE){
			System.out.println(currentPlayers.items());
			System.out.println("DISCARD");
			return GameStates.DISCARD;
		}
		return gameState.getState();
	}

	/* Name : getGameState
	 * Description : Gets the game state that deals with turns, quests, tournaments, and events
	 */
	public GameStates getGameState(){
		return gameState.getState();
	}

	/* Name : getDiscardState
	 * Description : Gets the game state that deals with discard behaviours
	 */
	public GameStates getDiscardState(){
		return discardState.getState();
	}

	public int getNumParticipants() {
	    return this.participants.size();
	}

	public GenericPlayer getCurrentPlayer(){
		int p = currentPlayers.current();
		return board.getGenericPlayer(p);
	}

	public Card getCurrentStory(){
		return board.getCurrentStoryCard();	
	}


	/* LEGACY */
	public List<GenericPlayer> getWaitingPlayers(){
		Cycle<Integer> waitingPlayersCycle = (new Cycle(currentPlayers));
		waitingPlayersCycle.removeCurrent();
		List<Integer>  waitingPlayers = waitingPlayersCycle.items();

		List<GenericPlayer> temp = new ArrayList();

		for(Integer id: waitingPlayers)
			temp.add(board.getGenericPlayer(id));

		return temp;
	}

	/*
	 * name : setPlayerName
	 * Description : Sets all the players with the same id
	 */
	public void setPlayerName(int id,String name){
		board.players.stream()
			.filter(p -> p.id() == id)
			.forEach(p ->p.name = name);
	}

	/*
	 * name : getHumanPlayers
	 * Description : gets all human players in game
	 */
	public List<GenericPlayer> getHumanPlayers(){
	List<GenericPlayer> humanIds = this.board.getPlayerIds()
		.stream().map(c -> board.getGenericPlayer(c))
		.filter(c -> c.type == AbstractPlayer.Type.Player)
		.collect(Collectors.toList());
		return  humanIds;
	}

	/*
	 * name :getPlayer
	 * Description :used to get a player hand of a specific player
	 */
	public GenericPlayer getPlayer(int id) {
		return board.getGenericPlayer(id);
	}


	/*
	 * name : next
	 * Description : Used to proceed onto the next state when state appropriate
	 */
	public void next(){
		this.gameState.next();
		this.updateObservers();
		checkAITurn();
	}

	/*
	 * name : decision
	 * Description : used to make a decision when state appropriate
	 */
	public void decision(int id, boolean choice){
		this.gameState.decision(id,choice);
		this.updateObservers();
		checkAITurn();
	}

	/*
	 * name : play
	 * Description : Used to play cards in hand when state appropriate
	 */
	public boolean play(int id, List<Card> cards){
		boolean retVal = this.gameState.play(id,cards);	
		this.updateObservers();
		checkAITurn();
		return retVal;
	}
	
	/* 
	 * Name : quest
	 * Description : Used for submitting quests when state appropriate
	 */
	public boolean quest(int id, TwoDimensionalArrayList<Card> quest){
		boolean retVal = this.gameState.quest(id,quest);
		this.updateObservers();
		checkAITurn();
		return retVal;
	}


	/*
	 * Description 	: Checks with merlin state to see if merlin can be used
	 * Return 	: Return necessary information for Merlin
	 */
	public void checkMerlin(int pid,int stage){
		/* WIP */
		/* Create an individual Merlin State which handles this interaction */
		return;
	}

	/*
	 * Description 	: Checks with Mordred State to eliminate an ally/amour in hand
	 * Return 	: None
	 */
	public void MordredSpecial(int pid, int epid,Card inplay){
		/* WIP */
	}







	/* LEGACY */
	public boolean discard (int playerId, List<Card> discards) {
		boolean retValue = discardState.discard(playerId,discards);
		this.updateObservers();
		checkAITurn();
		return retValue;
	}

	/* LEGACY */
	public void drawStoryCard(){
		System.out.println("drawStoryCard");
		this.gameState.next();
		this.updateObservers();
		checkAITurn();
	} 	
	/* LEGACY */
	public void sponsorQuest(int player,boolean sponsor){
		System.out.println("sponsorQuest");
		this.gameState.decision(player,sponsor);	
		this.updateObservers();
	}
	/* LEGACY */
	public boolean submitQuest(int player,TwoDimensionalArrayList<Card> quest){
		System.out.println("submitQuest");
		boolean retValue = this.gameState.quest(player,quest);	
		this.updateObservers();
		checkAITurn();
		return retValue;
	}
	/* LEGACY */
	public void participateQuest(int player,boolean participate){
		System.out.println("participateQuest");
		this.gameState.decision(player,participate);
		this.updateObservers();
		checkAITurn();	
	}
	/* LEGACY */
	public void stage(){
		System.out.println("stage");
		this.gameState.next();	
		this.updateObservers();
		checkAITurn();
	}
	/* LEGACY */
	public boolean stageFoe(int playerID, List<Card> list){
		System.out.println("stageFoe");
		boolean retValue = this.gameState.play(playerID,list);
		this.updateObservers();
		checkAITurn();
		return retValue;
			
	}
	/* LEGACY */
	public boolean stageTest(int playerID, List<Card> list){
		System.out.println("stageTest");
		boolean retValue = this.gameState.play(playerID,list);
		this.updateObservers();
		checkAITurn();
		return retValue;
	}
	/* LEGACY */
	public void testGiveUp(Integer id){
		System.out.println("testGiveUp");
		this.gameState.decision(id,true);
		this.updateObservers();
		checkAITurn();
	}
	/* LEGACY */
	public void stageEnd(){
		System.out.println("stageEnd");
		this.gameState.next();
		this.updateObservers();
		checkAITurn();
	}
	/* LEGACY */
	public void endQuest() {
		System.out.println("endQuest");
		this.gameState.next();	
		this.updateObservers();
		checkAITurn();
	}

	/* LEGACY */
	public void beginTournament(){
		System.out.println("beginTournament");
		this.gameState.next();	
		this.updateObservers();
		checkAITurn();
	}
	/* LEGACY */
	public void participateTournament(int player, boolean participate){
		System.out.println("participateTournament");
		this.gameState.decision(player,participate);	
		this.updateObservers();
		checkAITurn();
	}
	/* LEGACY */
	public void tournamentStageStart(){
		System.out.println("tournamentStageStart");
		this.gameState.next();
		this.updateObservers();
		checkAITurn();
	}
	/* LEGACY */
	public boolean tournamentStage(int id,List<Card> hand){
		boolean retVal = this.gameState.play(id,hand);	
		this.updateObservers();
		checkAITurn();
		return retVal;
	}
	/* LEGACY */
	public void tournamentStageEnd(){
		this.gameState.next();
		this.updateObservers();	
		checkAITurn();
	}
	/* LEGACY */
	public void tournamentEnd(){
		this.gameState.next();	
		this.updateObservers();
		checkAITurn();
	}

	/* LEGACY */
	public void applyEventLogic(){
		this.gameState.next();
		this.updateObservers();
		checkAITurn();
	}

	/* LEGACY */
	public void endTurn(){
		this.gameState.next();	
		this.updateObservers();
		checkAITurn();
	}

	protected void checkAITurn(){
		if(this.board.playerIsAI(this.currentPlayers.current())){
			AI_Move();	
		}
	}

	protected void AI_Move(){
		while(this.board.playerIsAI(this.currentPlayers.current())){
			GameStates state = getState();
			AbstractAI ai = this.board.getAI(this.currentPlayers.current());

			log.gameStateAction(state,"Turn ",ai);
			switch(state){
				case BEGIN_TURN:
					log.gameStateAction(state,"Draw Story",ai);

					/* this can be a user interaction */
					//drawStoryCard();
					this.gameState.next();
					break;
					/* Quest Setup */
					// decide whether sponsor a quest
				case SPONSOR_QUEST:
					log.gameStateAction(state,"Sponsor Quest",ai);
					//sponsorQuest(ai.id(),ai.doISponsorAQuest(this.board));
					this.gameState.decision(ai.id(),ai.doISponsorAQuest(this.board));
					break;
					// decide the quest setup
				case SPONSOR_SUBMIT:
					log.gameStateAction(state,"Submitting Sponsor",ai);
					//submitQuest(ai.id(),ai.sponsorQuest(this.board));
					this.gameState.quest(ai.id(),ai.sponsorQuest(this.board));
					break;
					// decide to participate in a quest
				case PARTICIPATE_QUEST:
					log.gameStateAction(state,"Participate Quest",ai);
					//participateQuest(ai.id(),ai.doIParticipateInQuest(this.board));
					this.gameState.decision(ai.id(),ai.doIParticipateInQuest(this.board));
					break;
					// continue onto next stage : is AI needed
				case QUEST_HANDLER:
					log.gameStateAction(state,"Begin Quest Stage",ai);

					/* This can be a user interaction */
					//stage();
					this.gameState.next();
					break;
					// submit foes for a stage
				case STAGE_FOE:
					log.gameStateAction(state,"Move Foe",ai);
					//stageFoe(ai.id(),ai.playQuest(this.board));
					this.gameState.play(ai.id(),ai.playQuest(this.board));
					break;
					// submit bids for a stage
					// decide whether to give up
				case STAGE_TEST:
					log.gameStateAction(state,"Bidding",ai);

					// if the AI cannot bid more, then give up
					/*
					if(!stageTest(ai.id(),ai.nextBid(this.board))){
						testGiveUp(ai.id());
					}
					*/
					if(!this.gameState.play(ai.id(),ai.nextBid(this.board))){
						this.gameState.decision(ai.id(),true);
					}
					break;
					// flip up cards : is AI needed
				case STAGE_END:
					log.gameStateAction(state,"Quest Stage End",ai);

					/* Can be a user interaction */
					//stageEnd();
					this.gameState.next();
					break;
					// clear up quest : is AI needed here
				case QUEST_END:
					log.gameStateAction(state,"Quest End",ai);

					/* Can be a user interaction */
					//endQuest();
					this.gameState.next();
					break;
				case PARTICIPATE_TOURNAMENT:
					log.gameStateAction(state,"Participate Tournament",ai);
					//participateTournament(ai.id(),ai.doIParticipateInTournament(this.board));
					this.gameState.decision(ai.id(),ai.doIParticipateInTournament(this.board));
					break;
				case TOURNAMENT_HANDLER:
					log.gameStateAction(state,"Stage Begin",ai);

					/* Can be a user interaction */
					//tournamentStageStart();
					this.gameState.next();
					break;
				case TOURNAMENT_STAGE:
					log.gameStateAction(state,"Play Tournament Stage",ai);

					//tournamentStage(ai.id(),ai.playInTournament(this.board));
					this.gameState.play(ai.id(),ai.playInTournament(this.board));
					break;
				case TOURNAMENT_STAGE_END:
					log.gameStateAction(state,"End Tournament Stage",ai);

					/* Can be a user interaction */
					//tournamentStageEnd();
					this.gameState.next();
					break;
				case TOURNAMENT_END:
					log.gameStateAction(state,"End Tournament",ai);

					/* Can be a user interaction */
					//tournamentEnd();
					this.gameState.next();
				case EVENT_LOGIC:
					log.gameStateAction(state,"Apply Event",ai);

					/* Can be a user interaction */
					//applyEventLogic();
					this.gameState.next();
					// end turn : is AI needed here
				case END_TURN:
					log.gameStateAction(state,"End Turn",ai);

					/* Can be a user interaction */
					//endTurn();
					this.gameState.next();
					break;
			}
			this.updateObservers();
		}
	}
}

