package com.mycompany.app.model;


import java.util.*;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;


public class GameModel{

	private List<GameObserver> observers;
	private GameStates state;

	//for when there is a discard interrupt
	private GameStates savedState;
	private int savedIndex;

	private int numberOfPlayers;

	//Active Story Player
	private int currentPlayer;
	
	//all Players In Game
	private List<Integer> players;

	//Turn Number
	private int turn;
	private GameBoard board;

	//current player
	private Cycle<Integer> storyTurn;
	private Cycle<Integer> questSponsor;
	private Cycle<Integer> participants;
	private Cycle<Integer> discard;

	private Cycle<Integer> currentPlayers;
	private int participationCounter;
	GameLogger log = GameLogger.getInstanceUsingDoubleLocking();



	public boolean discard (int playerId, List<Card> discards){
		if(this.state != GameStates.DISCARD)
			return false;

		if(this.discard.current() != playerId)
			return false;

		boolean valid = board.discardHand(playerId,discards);

		if(!valid)
			return false;

		discard.removeCurrent();

		if(discard.size() <= 0)
			changeState(this.savedState,this.savedIndex);
		else{
			changeState(GameStates.DISCARD,discard.current());
		}

		return true;

	}

	protected void changeState(GameStates state,int playerId){
		List<Integer> playersOverLimit = board.playersToDiscard();//get players over limit
		log.gameState(state.toString());
		// Discard
		if(playersOverLimit.size() > 0 && this.state != GameStates.DISCARD){
			this.savedState = state;
			this.savedIndex = this.players.indexOf(playerId);
			this.state = GameStates.DISCARD;

			this.currentPlayers = new Cycle(playersOverLimit,0);
		}
		//Change State
		else{
			this.currentPlayers = new Cycle(players,players.indexOf(playerId));
			this.state = state;
		}
		
	}

	public GameModel(){
		observers = new ArrayList<GameObserver>();
		board = new GameBoard();
		
	}


	public void initGame(int numHumans,int numAI,String[] humanNames){

		turn = 0;

		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());
		players = board.getPlayerIds();		
		storyTurn = new Cycle<Integer>(players,0);
		//this.state = GameStates.BEGIN_TURN;
		changeState(GameStates.BEGIN_TURN,players.indexOf(storyTurn.current()));
	}

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
		if (this.state != GameStates.BEGIN_TURN)
			return;
	

		/*
		 * Action : Check if any players have won
		 */
		List<GenericPlayer> winners = board.winningPlayers();	
		if(winners.size() > 0){
			this.state = GameStates.WINNERS;
			return;
		}

		/*
		 * Action: Draw from Story Deck
		 */
		// Currently breaks the cycle since nothing is checking for state change at the moment
		board.drawFromStoryDeck(players.get(currentPlayer));
		Card card = board.getCurrentStoryCard();


		
		if (Card.Types.EVENT == card.type){
			changeState(GameStates.EVENT_LOGIC,storyTurn.current());
			//this.state = GameStates.EVENT_LOGIC;
		}

		if (Card.Types.QUEST == card.type){
			// start a cycle  with the sponsor as current player
			questSponsor = new Cycle<Integer>(players,players.indexOf(storyTurn.current()));
			//this.state = GameStates.SPONSOR_QUEST;
			changeState(GameStates.SPONSOR_QUEST,questSponsor.current());
		}

		if (Card.Types.TOURNAMENT == card.type){
			changeState(GameStates.TOURNAMENT_HANDLER,storyTurn.current());
			//this.state = GameStates.TOURNAMENT_HANDLER;
		}

		/*
		 * Need : Need to specify what kind of action to update observers
		 */

		this.updateObservers();
	}



	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void sponsorQuest(int player,boolean sponsor){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;

        int currPlayer = questSponsor.current();

		/*
		 * Verify that they can sponsor with current cards
		 */
		if(player == currPlayer && sponsor && board.playerCanSponsor(player)){
			//this.state = GameStates.SPONSOR_SUBMIT;
			log.warning("Successful Sponsorship");
			changeState(GameStates.SPONSOR_SUBMIT,questSponsor.current());
		}
		else if(player == currPlayer && !sponsor){
			log.warning("Unsuccessful Sponsorship");
			questSponsor.removeCurrent();
		}
		else if(player == currPlayer && !board.playerCanSponsor(player)){
			log.warning("Unsuccessful Sponsorship");
			questSponsor.removeCurrent();	
		}


		/*
		 * Check if there are any more items
		 */
		if(questSponsor.size() <= 0){
			//this.state = GameStates.END_TURN;
			changeState(GameStates.BEGIN_TURN, storyTurn.next());
		} else if (this.state != GameStates.SPONSOR_SUBMIT){
            changeState(GameStates.SPONSOR_QUEST,questSponsor.current());
        }
		
		this.updateObservers();

	}

	/* Might not need this since the last person who -sponsor goes to end turn state
	public void noSponsor(){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;
		this.state = GameStates.END_TURN;
		this.updateObservers();
	}
	*/

	/*
	 * NEEDS : change player parameter to a Player Object
	 * NEEDS : some kind of quest object to submit
	 */
	public void submitQuest(int player,TwoDimensionalArrayList<Card> quest){
		if(this.state != GameStates.SPONSOR_SUBMIT)
			return;

		/*
		 * verify that it is a valid quest
		 */
		if(questSponsor.current() == player && board.submitQuest(quest,player)){
			this.participants = new Cycle(players,players.indexOf(questSponsor.current()));
			this.participants.removeCurrent();
      
			changeState(GameStates.PARTICIPATE_QUEST,this.participants.current());
			//this.state = GameStates.PARTICIPATE_QUEST;
		}
        System.out.println("Invalid");
	}

	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateQuest(int player,boolean participate){
		if(this.state != GameStates.PARTICIPATE_QUEST)			
			return;
		/*
		 * ACTION : add player to quest
		 */
		if(player == participants.current() && participate){
			this.board.addParticipant(this.participants.removeCurrent());
		}
		if(player == this.participants.current() && !participate){
			//this.board.addParticipant(this.participants.removeCurrent());
			participants.removeCurrent();
		}

		// change state
		if(this.participants.size() <= 0){
			//this.state = GameStates.QUEST_HANDLER;
			changeState(GameStates.QUEST_HANDLER,this.participants.current());
		}
		else if(this.participants.size() <= 0 && this.board.getParticipants().size() <= 0){
			//this.state = GameStates.QUEST_END;	
			changeState(GameStates.QUEST_HANDLER,this.participants.current());
		}

		this.updateObservers();
	}

	public void stage(){
		if(this.state != GameStates.QUEST_HANDLER)
			return ;
		/*
		 * some kind of quest logic here
		 */
		this.participants = new Cycle<Integer>(this.board.getParticipants(),0);


		//draw card
		board.beginEncounter();

		if(board.stageType(Card.Types.FOE)){
			//this.state = GameStates.STAGE_FOE;
			changeState(GameStates.STAGE_FOE,this.participants.current());
		}
		if(board.stageType(Card.Types.TEST)){
			//this.state = GameStates.STAGE_TEST;
			changeState(GameStates.STAGE_TEST,this.participants.current());
		}

		this.updateObservers();
	}

	public boolean stageFoe(int playerID, List<Card> list){
		if(this.state != GameStates.STAGE_FOE)
			return false;

		if(playerID != this.participants.current())	
			return false;

		boolean validSubmit = board.submitHand(playerID,list);

		if(validSubmit){
			this.participants.removeCurrent();
		}
		if(!validSubmit){
			return false;
		}

		if(this.participants.size() <= 0 ){
			changeState(GameStates.STAGE_END,playerID);
			//this.state = GameStates.STAGE_END;
		}

		this.updateObservers();

		return true;

	}

	public boolean stageTest(int playerID, List<Card> list){
		if(this.state != GameStates.STAGE_TEST)
			return false;

		if(playerID != this.participants.current())
			return false;

		boolean validSubmit = board.submitBids(playerID,list);

		if(!validSubmit) {
			return false;
		}

		if(validSubmit && board.checkTestWinner()){
			//this.state = GameStates.STAGE_END;
			changeState(GameStates.STAGE_END,this.participants.current());
		}

		if(validSubmit && !board.checkTestWinner()){
			this.participants.next();
		}

		this.updateObservers();

		return true;

	}

	public void testGiveUp(Integer id){
		if(this.state != GameStates.STAGE_TEST)
			return;

		if(participants.current() != id){
			return;
		}

		board.giveUp(id);

		if(board.checkTestWinner()){
			//this.state = GameStates.STAGE_END;
			changeState(GameStates.STAGE_END,this.participants.current());
		}

	}


	public void stageEnd(){
		if(this.state != GameStates.STAGE_END)
			return;

		if(board.stageType(Card.Types.FOE)){
			board.completeFoeStage();
		}
		if(board.stageType(Card.Types.TEST)){

		}

		if(board.getParticipants().size() == 0){
			//this.state = GameStates.QUEST_END;
			changeState(GameStates.QUEST_END,this.questSponsor.current());
		}

		//distribute cards
		if(!this.board.nextStage())
			changeState(GameStates.QUEST_END,this.questSponsor.current());
			//this.state = GameStates.QUEST_END;
		else
			changeState(GameStates.QUEST_HANDLER,this.questSponsor.current());
			//this.state = GameStates.QUEST_HANDLER;
		this.updateObservers();
	}


	public void endQuest() {
		if(this.state != GameStates.QUEST_END) {
			return;
		}

		//apply story logic
		board.applyStoryCardLogic(questSponsor.current());

		changeState(GameStates.END_TURN,questSponsor.current());
		//this.state = GameStates.END_TURN;
		this.updateObservers();
	}


	public void beginTournament(){
		if(this.state != GameStates.TOURNAMENT_HANDLER)
			return;

		// start a cycle which loops through participants
		participants = new Cycle<Integer>(players,players.indexOf(storyTurn.current()));

		//this.state = GameStates.PARTICIPATE_TOURNAMENT;
		changeState(GameStates.PARTICIPATE_TOURNAMENT,participants.current());
	}



	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateTournament(int player, boolean participate){
		if(this.state != GameStates.PARTICIPATE_TOURNAMENT)
			return;
		if(player == participants.current() && participate){
			this.board.addParticipant(this.participants.removeCurrent());
		}
		if(player == this.participants.current() && !participate){
			this.board.addParticipant(this.participants.removeCurrent());
		}
		if(this.participants.size() <= 0){
			//this.state = GameStates.TOURNAMENT_HANDLER;
			changeState(GameStates.TOURNAMENT_HANDLER,player);
		}
		else if(this.participants.size() <= 1 && board.getParticipants().size() <= 1){
			changeState(GameStates.END_TURN,this.storyTurn.current());
			//this.state = GameStates.END_TURN;
		}
		this.updateObservers();
	}


	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateTournamentEnd(int player,boolean participate){
		if(this.state != GameStates.PARTICIPATE_TOURNAMENT)
			return;

		/*
		 * ACTION : add player to quest
		 */
		if(player == participants.current() && participate){
			this.board.addParticipant(this.participants.removeCurrent());
		}
		if(player == this.participants.current() && !participate){
			//this.board.addParticipant(this.participants.removeCurrent());
			participants.removeCurrent();
		}

		// change state
		if(this.participants.size() <= 0){
			this.state = GameStates.TOURNAMENT_HANDLER;
		}
		else if(this.participants.size() <= 0 && this.board.getParticipants().size() <= 0){
			this.state = GameStates.TOURNAMENT_STAGE_END;	
		}

		this.updateObservers();

	}

	public void tournamentStageStart(){
		if(this.state != GameStates.TOURNAMENT_HANDLER)
			return;

		/*
		 * some kind of quest logic here
		 */
		this.participants = new Cycle<Integer>(this.board.getParticipants(),0);


		//draw card
		board.beginEncounter();

		//this.state = GameStates.TOURNAMENT_STAGE;
		changeState(GameStates.TOURNAMENT_STAGE,this.participants.current());

		this.updateObservers();
	}

	public boolean tournamentStage(int id,List<Card> hand){
		if(this.state != GameStates.TOURNAMENT_STAGE)
			return false;

		if(id != this.participants.current())
			return false;

		// implement the tournament methods
		boolean validSubmit = board.submitHand(id,hand);

		if(!validSubmit){
			return false;
		}

		if(validSubmit){
			this.participants.removeCurrent();
		}

		
		if(this.participants.size() <= 0){
			changeState(GameStates.TOURNAMENT_STAGE_END,id);
			//this.state = GameStates.TOURNAMENT_STAGE_END;
		}
		return true;

	}

	public void tournamentStageEnd(){
		if(this.state != GameStates.TOURNAMENT_STAGE_END)
			return;
		
		//check for winner
		board.completeTournamentStage();

		//check which round we are on
		boolean anotherRound = board.nextTournament();
		//TIE round 1 
		if(anotherRound){
			//clean up round 1, 
			//this.state = GameStates.TOURNAMENT_HANDLER;
			changeState(GameStates.TOURNAMENT_HANDLER,this.participants.current());
		}
		//TIE round 2 
		if(!anotherRound){
			//clean up all	
			changeState(GameStates.TOURNAMENT_END,this.storyTurn.current());
			//this.state = GameStates.TOURNAMENT_END;
		}
		this.updateObservers();

	}

	public void tournamentEnd(){
		if(this.state != GameStates.TOURNAMENT_END)
			return;


		board.applyStoryCardLogic(-1);
		changeState(GameStates.END_TURN,this.storyTurn.current());
		//this.state = GameStates.END_TURN;
		this.updateObservers();

	}


	public void applyEventLogic(){
		if(this.state != GameStates.EVENT_LOGIC)
			return;

		/*
		 * ACTION : Apply events logic to players
		 */
		board.applyStoryCardLogic(storyTurn.current());

		//this.state = GameStates.END_TURN;
		changeState(GameStates.END_TURN,this.storyTurn.current());

		this.updateObservers();
	}


	public void endTurn(){
		if(this.state != GameStates.END_TURN)
			return;
		
		this.turn++;
		this.currentPlayer = storyTurn.next();

		//this.state = GameStates.BEGIN_TURN;
		changeState(GameStates.BEGIN_TURN,this.storyTurn.current());
		this.updateObservers();
	}

}

