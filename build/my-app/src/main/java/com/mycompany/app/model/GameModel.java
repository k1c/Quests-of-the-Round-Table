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


	protected void AI_Move(AbstractAI ai){
		switch(this.state){
			case BEGIN_TURN:
				log.gameStateAction(this.state,"Draw Story",ai);

				/* this can be a user interaction */
				drawStoryCard();
				break;
			/* Quest Setup */
			// decide whether sponsor a quest
			case SPONSOR_QUEST:
				log.gameStateAction(this.state,"Sponsor Quest",ai);
				sponsorQuest(ai.id(),ai.doISponsorAQuest(this.board));
				break;
			// decide the quest setup
			case SPONSOR_SUBMIT:
				log.gameStateAction(this.state,"Submitting Sponsor",ai);
				submitQuest(ai.id(),ai.sponsorQuest(this.board));
				break;
			// decide to participate in a quest
			case PARTICIPATE_QUEST:
				log.gameStateAction(this.state,"Participate Quest",ai);
				participateQuest(ai.id(),ai.doIParticipateInQuest(this.board));
				break;
			// continue onto next stage : is AI needed
			case QUEST_HANDLER:
				log.gameStateAction(this.state,"Begin Quest Stage",ai);

				/* This can be a user interaction */
				stage();
				break;
			// submit foes for a stage
			case STAGE_FOE:
				log.gameStateAction(this.state,"Move Foe",ai);
				stageFoe(ai.id(),ai.playQuest(this.board));
				break;
			// submit bids for a stage
			// decide whether to give up
			case STAGE_TEST:
				log.gameStateAction(this.state,"Bidding",ai);
				
				// if the AI cannot id more, then give up
				if(!stageTest(ai.id(),ai.nextBid(this.board))){
					testGiveUp(ai.id());
				}
				break;
			// flip up cards : is AI needed
			case STAGE_END:
				log.gameStateAction(this.state,"Quest Stage End",ai);

				/* Can be a user interaction */
				stageEnd();
				break;
			// clear up quest : is AI needed here
			case QUEST_END:
				log.gameStateAction(this.state,"Quest End",ai);
				
				/* Can be a user interaction */
				endQuest();
				break;
			case PARTICIPATE_TOURNAMENT:
				log.gameStateAction(this.state,"Participate Tournament",ai);
				participateTournament(ai.id(),ai.doIParticipateInTournament(this.board));
				break;
			case TOURNAMENT_HANDLER:
				log.gameStateAction(this.state,"Stage Begin",ai);

				/* Can be a user interaction */
				tournamentStageStart();
				break;
			case TOURNAMENT_STAGE:
				log.gameStateAction(this.state,"Play Tournament Stage",ai);

				tournamentStage(ai.id(),ai.playInTournament(this.board));
				break;
			case TOURNAMENT_STAGE_END:
				log.gameStateAction(this.state,"End Tournament Stage",ai);

				/* Can be a user interaction */
				tournamentStageEnd();
				break;
			case TOURNAMENT_END:
				log.gameStateAction(this.state,"End Tournament",ai);

				/* Can be a user interaction */
				tournamentEnd();
			case EVENT_LOGIC:
				log.gameStateAction(this.state,"Apply Event",ai);

				/* Can be a user interaction */
				applyEventLogic();
			// end turn : is AI needed here
			case END_TURN:
				log.gameStateAction(this.state,"End Turn",ai);

				/* Can be a user interaction */
				endTurn();
				break;
		}
	}

	public boolean discard (int playerId, List<Card> discards) {
		if (this.state != GameStates.DISCARD) {
			log.gameStateAction(this.state, "Incorrect State", board.findPlayer(playerId));
			return false;
		}

		if (this.discard.current() != playerId) {
			log.gameStateAction(this.state, "Incorrect Player", board.findPlayer(playerId));
			return false;
		}

		boolean valid = board.discardHand(playerId, discards);

		if (!valid) {
			log.gameStateAction(this.state, "Invalid Discard", board.findPlayer(playerId));
			return false;
		}

		discard.removeCurrent();

		if (discard.size() <= 0){
		changeState(this.savedState, this.savedIndex);
	    }else{
			changeState(GameStates.DISCARD,discard.current());
		}

		return true;

	}

	protected void changeState(GameStates state,int playerId){
		List<Integer> playersOverLimit = board.playersToDiscard();//get players over limit
		log.gameState(state.toString());
		// Discard
		if(playersOverLimit.size() > 0){
            if (this.state != GameStates.DISCARD) {
                this.savedState = state;
                this.savedIndex = this.players.indexOf(playerId);
            }

            this.state = GameStates.DISCARD;
            log.gameStateAction(this.state,"Discard Found",board.findPlayer(playerId));
			//System.out.println(playersOverLimit);

			this.discard = new Cycle(playersOverLimit,0);
			this.currentPlayers = new Cycle(players,players.indexOf(playersOverLimit.get(0)));
		}
		//Change State
		else{
			this.currentPlayers = new Cycle(players,players.indexOf(playerId));
			this.state = state;
		}

		log.gameState(this.state.toString());
		if(board.playerIsAI(playerId))
			AI_Move(board.getAI(playerId));

		this.updateObservers();
	}

	public GameModel(){
		observers = new ArrayList<GameObserver>();
		board = new GameBoard();
		
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

	public void initGame(int numHumans,int numAI,String[] humanNames){

		turn = 0;

		board.initGame(numHumans, numAI, humanNames, CardLoader.loadAdventureCards(), CardLoader.loadStoryCards());
		players = board.getPlayerIds();		
		storyTurn = new Cycle<Integer>(players,0);
		//this.state = GameStates.BEGIN_TURN;
		changeState(GameStates.BEGIN_TURN,storyTurn.current());
	}

	public void rigGame1(){
		turn = 0;
		board = RiggedGameModel.rig1();	
		players = board.getPlayerIds();		
		storyTurn = new Cycle<Integer>(players,0);
		changeState(GameStates.BEGIN_TURN,storyTurn.current());
	}

	public void rigGame2(){
		turn = 0;
		board = RiggedGameModel.rig2();	
		players = board.getPlayerIds();		
		storyTurn = new Cycle<Integer>(players,0);
		changeState(GameStates.BEGIN_TURN,storyTurn.current());
	}

	public void rigGame3(){
		turn = 0;
		board = RiggedGameModel.rig3();	
		players = board.getPlayerIds();		
		storyTurn = new Cycle<Integer>(players,0);
		changeState(GameStates.BEGIN_TURN,storyTurn.current());
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
			questSponsor = new Cycle<Integer>(players,storyTurn.current());
			//this.state = GameStates.SPONSOR_QUEST;
			changeState(GameStates.SPONSOR_QUEST,questSponsor.current());
		}

		if (Card.Types.TOURNAMENT == card.type){
			this.participants = new Cycle<Integer>(this.players,this.storyTurn.current());
			changeState(GameStates.PARTICIPATE_TOURNAMENT,storyTurn.current());
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
		Player p = board.findPlayer(currPlayer);
		log.playerAction(p,"is deciding whether to Sponsor the Quest");
		/*
		 * Verify that they can sponsor with current cards
		 */
		if(player == currPlayer && sponsor && board.playerCanSponsor(player)){
			//this.state = GameStates.SPONSOR_SUBMIT;
			log.playerAction(p,"successfully sponsors the Quest");
			changeState(GameStates.SPONSOR_SUBMIT,questSponsor.current());
		}
		else if(player == currPlayer && !sponsor){
			log.playerAction(p,"declines to sponsor the Quest");
			questSponsor.removeCurrent();
		}
		else if(player == currPlayer && !board.playerCanSponsor(player)){
			log.playerAction(p,"cannot sponsor the Quest");
			questSponsor.removeCurrent();	
		}


		/*
		 * Check if there are any more items
		 */
		if(questSponsor.size() <= 0){
			//this.state = GameStates.END_TURN;
			changeState(GameStates.END_TURN, storyTurn.current());
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
	public boolean submitQuest(int player,TwoDimensionalArrayList<Card> quest){
		if(this.state != GameStates.SPONSOR_SUBMIT){
			log.gameStateAction(this.state,"Incorrect State",board.findPlayer(player));
			return false;
		}

		/*
		 * verify that it is a valid quest
		 */
		if(questSponsor.current() == player && board.submitQuest(quest,player)){
			log.gameStateAction(this.state,"Valid Submission",board.findPlayer(player));
			this.participants = new Cycle(players,players.indexOf(questSponsor.current()));
			this.participants.removeCurrent();
      
			changeState(GameStates.PARTICIPATE_QUEST,this.participants.current());
			return true;
			//this.state = GameStates.PARTICIPATE_QUEST;
		}
		log.gameStateAction(this.state,"Invalid Submission",board.findPlayer(player));
		return false;

	}

	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateQuest(int player,boolean participate){
		if(this.state != GameStates.PARTICIPATE_QUEST){
			log.gameStateAction(this.state,"Invalid State",board.findPlayer(player));
			return;
		}


		/*
		 * ACTION : add player to quest
		 */
		int currPlayer = this.participants.current();
		Player p = board.findPlayer(currPlayer);
		log.playerAction(p, "is deciding whether to participate in the Quest");

		if(player == currPlayer && participate){
			log.playerAction(p, "successfully participates in the Quest");
			this.board.addParticipant(this.participants.removeCurrent());
		}else if(player == currPlayer && !participate){
			log.playerAction(p, "declines to participate in the Quest");
			//this.board.addParticipant(this.participants.removeCurrent());
			participants.removeCurrent();
		}

		// change state
		if(this.participants.size() <= 0 && this.board.getParticipants().size()>0){
			//this.state = GameStates.QUEST_HANDLER;
			log.gameStateAction(this.state,"Starting Quest",board.findPlayer(player));
			changeState(GameStates.QUEST_HANDLER, currPlayer);
		}
		else if(this.participants.size() <= 0 && this.board.getParticipants().size() <= 0){
			//this.state = GameStates.QUEST_END;	
			log.gameStateAction(this.state,"Not Enough Players",board.findPlayer(player));
			changeState(GameStates.QUEST_END, currPlayer);
		}
		else if(this.state != GameStates.QUEST_HANDLER) {
			log.gameStateAction(this.state,"Next Player",board.findPlayer(player));
			changeState(GameStates.PARTICIPATE_QUEST, this.participants.current());
        }

		this.updateObservers();
	}

	public void stage(){
		if(this.state != GameStates.QUEST_HANDLER){
			log.gameStateAction(this.state,"Invalid State","");
			return ;
		}
		/*
		 * some kind of quest logic here
		 */
		this.participants = new Cycle<Integer>(this.board.getParticipants(),0);


		//draw card
		board.beginEncounter();

		if(board.stageType(Card.Types.FOE)){
			//this.state = GameStates.STAGE_FOE;
			log.gameStateAction(this.state,"Foe Stage Next","");
			changeState(GameStates.STAGE_FOE,this.participants.current());
		}
		if(board.stageType(Card.Types.TEST)){
			//this.state = GameStates.STAGE_TEST;
			log.gameStateAction(this.state,"Test Stage Next","");
			changeState(GameStates.STAGE_TEST,this.participants.current());
		}

		this.updateObservers();
	}

	public int getNumParticipants() {
	    return this.participants.size();
    }

	public boolean stageFoe(int playerID, List<Card> list){
		if(this.state != GameStates.STAGE_FOE){
			log.gameStateAction(this.state,"Invalid State",board.findPlayer(playerID));
			return false;
		}

		if(playerID != this.participants.current()){
			log.gameStateAction(this.state,"Invalid Player",board.findPlayer(playerID));
			return false;
		}


		boolean validSubmit = board.submitHand(playerID,list);

		if(validSubmit){
			log.gameStateAction(this.state,"valid Submission",board.findPlayer(playerID));
			this.participants.removeCurrent();
		}
		if(!validSubmit){
			log.gameStateAction(this.state,"invalid Submission",board.findPlayer(playerID));
			return false;
		}

		if(this.participants.size() <= 0 ){
			log.gameStateAction(this.state,"last participant",board.findPlayer(playerID));
			changeState(GameStates.STAGE_END,playerID);
			//this.state = GameStates.STAGE_END;
		} else {
		    changeState(GameStates.STAGE_FOE, this.participants.current());
        }

		this.updateObservers();

		return true;

	}

	public boolean stageTest(int playerID, List<Card> list){
		if(this.state != GameStates.STAGE_TEST){
			log.gameStateAction(this.state,"Invalid State",board.findPlayer(playerID));
			return false;
		}

		if(playerID != this.participants.current()){
			log.gameStateAction(this.state,"Invalid Player",board.findPlayer(playerID));
			return false;
		}

		boolean validSubmit = board.submitBids(playerID,list);

		if(!validSubmit) {
			log.gameStateAction(this.state,"Invalid Bid",board.findPlayer(playerID));
			changeState(GameStates.STAGE_TEST, this.participants.current());
			return false;
		}

		if(validSubmit && board.checkTestWinner()){
			//this.state = GameStates.STAGE_END;
			log.gameStateAction(this.state,"Wins Bid",board.findPlayer(playerID));
			changeState(GameStates.STAGE_END,this.participants.current());
		} else if(validSubmit && !board.checkTestWinner()){
			log.gameStateAction(this.state,"Passes Bid",board.findPlayer(playerID));
			this.participants.next();
			changeState(GameStates.STAGE_TEST, this.participants.current());
		}

		this.updateObservers();

		return true;

	}

	public void testGiveUp(Integer id){
		if(this.state != GameStates.STAGE_TEST){
			log.gameStateAction(this.state,"Invalid State",board.findPlayer(id));
			//this.participants.next();
			return;
		}

		if(participants.current() != id){
			log.gameStateAction(this.state,"Invalid Player",board.findPlayer(id));
			return;
		}

		log.gameStateAction(this.state,"Given Up",board.findPlayer(id));
		board.giveUp(id);
		this.participants.removeCurrent();

		if(board.checkTestWinner()){
			log.gameStateAction(this.state,"Someone Has Won",board.findPlayer(id));
			//this.state = GameStates.STAGE_END;
			changeState(GameStates.STAGE_END,this.participants.current());
		} else {
			changeState(GameStates.STAGE_TEST, this.participants.current());
		}

		updateObservers();

	}


	public void stageEnd(){
		if(this.state != GameStates.STAGE_END){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}

		if(board.stageType(Card.Types.FOE)){
			log.gameStateAction(this.state,"Cleaning Up Foe Stage","");
			board.completeFoeStage();
		}
		if(board.stageType(Card.Types.TEST)){
			log.gameStateAction(this.state,"Cleaning Up Test Stage","");
			board.completeTestStage();

		}

		System.out.println(board.getParticipants().size());
		if(board.getParticipants().size() == 0){
			log.gameStateAction(this.state,"No More Participants","");
			changeState(GameStates.QUEST_END,this.questSponsor.current());
		}

		//distribute cards
		else if(!this.board.nextStage()){
			log.gameStateAction(this.state,"Completed All Stages","");
			changeState(GameStates.QUEST_END,this.questSponsor.current());
		}
		else{
			log.gameStateAction(this.state,"Next Stage","");
			changeState(GameStates.QUEST_HANDLER,this.questSponsor.current());
		}
		this.updateObservers();
	}


	public void endQuest() {
		if(this.state != GameStates.QUEST_END) {
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}
		log.gameStateAction(this.state,"Applying Quest Logic","");

		//apply story logic
		board.applyStoryCardLogic(questSponsor.current());

		changeState(GameStates.END_TURN,questSponsor.current());
		//this.state = GameStates.END_TURN;
		this.updateObservers();
	}


	public void beginTournament(){
		if(this.state != GameStates.TOURNAMENT_HANDLER){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}

		log.gameStateAction(this.state,"Begin Tournament","");

		// start a cycle which loops through participants
		participants = new Cycle<Integer>(players,players.indexOf(storyTurn.current()));

		//this.state = GameStates.PARTICIPATE_TOURNAMENT;
		changeState(GameStates.PARTICIPATE_TOURNAMENT,participants.current());
	}



	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateTournament(int player, boolean participate){
		if(this.state != GameStates.PARTICIPATE_TOURNAMENT){
			log.gameStateAction(this.state,"Invalid State",board.findPlayer(player));
			return;
		}
		int currentPlayer = participants.current();
		Player p = board.findPlayer(currentPlayer);
		log.playerAction(p,"is deciding wheter to participate in the Tournament");

		if(player == currentPlayer && participate){
			log.gameStateAction(this.state,"Participates",board.findPlayer(player));
			this.board.addParticipant(this.participants.removeCurrent());
		}else if(player == currentPlayer && !participate){
			log.gameStateAction(this.state,"Does Not Participate",board.findPlayer(player));
			this.participants.removeCurrent();
		}

		if(this.participants.size() <= 0 && board.getParticipants().size() > 0){
			log.gameStateAction(this.state,"Beginning Tournament",board.findPlayer(player));
			changeState(GameStates.TOURNAMENT_HANDLER,currentPlayer);
		}
		else if(this.participants.size() <= 0 && board.getParticipants().size() <= 0){
			log.gameStateAction(this.state,"Not Enough Participants",board.findPlayer(player));
			changeState(GameStates.END_TURN,this.storyTurn.current());
			//this.state = GameStates.END_TURN;
		}
		else{
			log.gameStateAction(this.state,"Next Participant",board.findPlayer(this.participants.current()));
			changeState(GameStates.PARTICIPATE_TOURNAMENT,this.participants.current());
		}

		this.updateObservers();

	}

	public void tournamentStageStart(){
		if(this.state != GameStates.TOURNAMENT_HANDLER){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}

		log.gameStateAction(this.state,"Beginning Tournament Stage","");
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
		if(this.state != GameStates.TOURNAMENT_STAGE){
			log.gameStateAction(this.state,"Invalid State",board.findPlayer(id));
			return false;
		}

		if(id != this.participants.current()){
			log.gameStateAction(this.state,"Invalid Player",board.findPlayer(id));
			return false;
		}

		log.gameStateAction(this.state,"Participants : ",this.participants.items());
		if(this.participants.size() <= 0){
			log.gameStateAction(this.state,"All Players Have Played",board.findPlayer(id));
			return false;
		}

		// implement the tournament methods
		boolean validSubmit = board.submitHand(id,hand);

		if(!validSubmit){
			log.gameStateAction(this.state,"Incorrect Submission",board.findPlayer(id));
			return false;
		}

		if(validSubmit){
			log.gameStateAction(this.state,"Correct Submission",board.findPlayer(id));
			this.participants.removeCurrent();
		}

		
		if(this.participants.size() <= 0){
			log.gameStateAction(this.state,"Ending Stage",board.findPlayer(id));
			changeState(GameStates.TOURNAMENT_STAGE_END,id);
			//this.state = GameStates.TOURNAMENT_STAGE_END;
		} else {
			changeState(GameStates.TOURNAMENT_STAGE, this.participants.current());
		}
		return true;

	}

	public void tournamentStageEnd(){
		if(this.state != GameStates.TOURNAMENT_STAGE_END){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}


		//check for winner
		board.completeTournamentStage();

		//check which round we are on
		boolean anotherRound = board.nextTournament();
		//TIE round 1 
		if(anotherRound){
			//clean up round 1, 
			//this.state = GameStates.TOURNAMENT_HANDLER;
			log.gameStateAction(this.state,"Tie Breaker","");
			changeState(GameStates.TOURNAMENT_HANDLER,this.board.getParticipants().get(0));
		}
		//TIE round 2 
		if(!anotherRound){
			//clean up all	
			log.gameStateAction(this.state,"Winner Found","");
			changeState(GameStates.TOURNAMENT_END,this.storyTurn.current());
			//this.state = GameStates.TOURNAMENT_END;
		}
		this.updateObservers();

	}

	public void tournamentEnd(){
		if(this.state != GameStates.TOURNAMENT_END){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}


		log.gameStateAction(this.state,"Applying Tournament Logic","");
		board.applyStoryCardLogic(-1);
		changeState(GameStates.END_TURN,this.storyTurn.current());
		//this.state = GameStates.END_TURN;
		this.updateObservers();

	}


	public void applyEventLogic(){
		if(this.state != GameStates.EVENT_LOGIC){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}

		/*
		 * ACTION : Apply events logic to players
		 */
		log.gameStateAction(this.state,"Applying Event Logic","");
		board.applyStoryCardLogic(storyTurn.current());

		//this.state = GameStates.END_TURN;
		changeState(GameStates.END_TURN,this.storyTurn.current());

		this.updateObservers();
	}


	public void endTurn(){
		if(this.state != GameStates.END_TURN){
			log.gameStateAction(this.state,"Invalid State","");
			return;
		}
		
		log.gameStateAction(this.state,"End Turn","");
		this.turn++;
		this.currentPlayer = storyTurn.next();

		//this.state = GameStates.BEGIN_TURN;
		changeState(GameStates.BEGIN_TURN,this.storyTurn.current());
		this.updateObservers();
	}

}

