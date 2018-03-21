package com.mycompany.app.model;


import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;
import com.mycompany.app.model.*;


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



	public boolean discard (int playerId, List<Card> discards) {return true;}

	public GameModel(){
		observers = new ArrayList<GameObserver>();
		board = new GameBoard();
		gameState = new GameStateInit(this);
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

	public GameStates getState() {
	    return this.state;
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

	public List<GenericPlayer> getWaitingPlayers(){
		Cycle<Integer> waitingPlayersCycle = (new Cycle(currentPlayers));
		waitingPlayersCycle.removeCurrent();
		List<Integer>  waitingPlayers = waitingPlayersCycle.items();

		List<GenericPlayer> temp = new ArrayList();

		for(Integer id: waitingPlayers)
			temp.add(board.getGenericPlayer(id));

		return temp;
	}
	

	public void drawStoryCard(){
		System.out.println("drawStoryCard");
		this.gameState.next();
		this.updateObservers();
	} 	
	public void sponsorQuest(int player,boolean sponsor){
		System.out.println("sponsorQuest");
		this.gameState.decision(player,sponsor);	
		this.updateObservers();
	}
	public boolean submitQuest(int player,TwoDimensionalArrayList<Card> quest){
		System.out.println("submitQuest");
		boolean retValue = this.gameState.quest(player,quest);	
		this.updateObservers();
		return retValue;
	}
	public void participateQuest(int player,boolean participate){
		System.out.println("participateQuest");
		this.gameState.decision(player,participate);
		this.updateObservers();
	
	}
	public void stage(){
		System.out.println("stage");
		this.gameState.next();	
		this.updateObservers();
	}
	public boolean stageFoe(int playerID, List<Card> list){
		System.out.println("stageFoe");
		boolean retValue = this.gameState.play(playerID,list);
		this.updateObservers();
		return retValue;
			
	}
	public boolean stageTest(int playerID, List<Card> list){
		System.out.println("stageTest");
		boolean retValue = this.gameState.play(playerID,list);
		this.updateObservers();
		return retValue;
	}
	public void testGiveUp(Integer id){
		System.out.println("testGiveUp");
		this.gameState.decision(id,true);
		this.updateObservers();
	}
	public void stageEnd(){
		System.out.println("stageEnd");
		this.gameState.next();
		this.updateObservers();
	}
	public void endQuest() {
		System.out.println("endQuest");
		this.gameState.next();	
		this.updateObservers();
	}

	public void beginTournament(){
		System.out.println("beginTournament");
		this.gameState.next();	
		this.updateObservers();
	}
	public void participateTournament(int player, boolean participate){
		System.out.println("participateTournament");
		this.gameState.decision(player,participate);	
		this.updateObservers();
	}
	public void tournamentStageStart(){
		System.out.println("tournamentStageStart");
		this.gameState.next();
		this.updateObservers();
	}
	public boolean tournamentStage(int id,List<Card> hand){
		boolean retVal = this.gameState.play(id,hand);	
		this.updateObservers();
		return retVal;
	}
	public void tournamentStageEnd(){
		this.gameState.next();
		this.updateObservers();	
	}
	public void tournamentEnd(){
		this.gameState.next();	
		this.updateObservers();
	}

	public void applyEventLogic(){
		this.gameState.next();
		this.updateObservers();
	}

	public void endTurn(){
		this.gameState.next();	
		this.updateObservers();
	}

}

