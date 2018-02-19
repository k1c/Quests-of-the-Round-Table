package com.mycompany.app.model;


import java.util.*;


public class GameModel{

	private List<GameObserver> observers;
	private GameStates state;

	private int numberOfPlayers;
	private int currentPlayer;
	private List<Integer> players;

	private int turn;
	private GameBoard board;

	public GameModel(){
		observers = new ArrayList<GameObserver>();
		board = new GameBoard();
		
		numberOfPlayers = 4;
		currentPlayer = 0;

		turn = 0;

		board.initGame(numberOfPlayers,CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());
		players = board.getPlayerIds();		

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

	public ViewGameBoard getGameBoard(){
		return board.getViewCopy();
	}

	public void nextTurn(){
		if (this.state != GameStates.BEGIN_TURN)
			return;

		this.turn++;
		this.currentPlayer = (this.turn) % this.numberOfPlayers;
		

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
		
		board.drawFromStoryDeck(players.get(currentPlayer));
		Card card = board.getCurrentStoryCard();
		

		
		if (Card.Types.EVENT == card.type){
			this.state = GameStates.EVENT_LOGIC;
			return;
		}
		if (Card.Types.QUEST == card.type){
			this.state = GameStates.SPONSOR_QUEST;
			return;
		}
		if (Card.Types.TOURNAMENT == card.type){
			this.state = GameStates.PARTICIPATE_TOURNAMENT;
			return;
		}

		/*
		 * Need : Need to specify what kind of action to update observers
		 */

		this.updateObservers();
	}



	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void sponsorQuest(int player){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;
		
		/*
		 * Verify that they can sponsor with current cards
		 */
		if(board.playerCanSponsor(player))	
			this.state = GameStates.SPONSOR_SUBMIT;

	}

	public void noSponsor(){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;
		this.state = GameStates.BEGIN_TURN;
	}

	/*
	 * NEEDS : change player parameter to a Player Object
	 * NEEDS : some kind of quest object to submit
	 */
	public void submitQuest(int player){
		if(this.state != GameStates.SPONSOR_SUBMIT)
			return;
		/*
		 * verify that it is a valid quest
		 */

		this.state = GameStates.PARTICIPATE_QUEST;
	}

	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateQuest(int player){
		if(this.state != GameStates.PARTICIPATE_QUEST)			
			return;
		/*
		 * ACTION : add player to quest
		 */
	}

	public void participateQuestEnd(){
		if(this.state != GameStates.PARTICIPATE_QUEST)
			return;

		/*
		 * Action : check number of players participating in quest 
		 */
		int numberOfParticipants = 0;

		if(numberOfParticipants == 0)
			state = GameStates.BEGIN_TURN;
		else
			state = GameStates.QUEST_HANDlER;
	}
	

	public void beginQuest(){
		if(this.state != GameStates.QUEST_HANDlER)
			return ;

		/*
		 * some kind of quest logic here
		 */

		this.state = GameStates.BEGIN_TURN;
	}


	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateTournament(int player){
		if(this.state != GameStates.PARTICIPATE_TOURNAMENT)
			return;
		/*
		 * Action add player to tournament
		 */
					
	}


	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void participateTournamentEnd(int player){
		if(this.state != GameStates.PARTICIPATE_TOURNAMENT)
			return;

		int numberOfParticipants = 0;

		/*
		 * Action : get number of participants
		 */


		if(numberOfParticipants==0)
			state = GameStates.BEGIN_TURN;
		else
			state = GameStates.TOURNAMENT_HANDlER;
	}

	public void beginTournament(){
		if(this.state != GameStates.TOURNAMENT_HANDlER)
			return;

		/*
		 * handle tournament logic with another state
		 */
		
		this.state = GameStates.BEGIN_TURN;

	}

	public void applyEventLogic(){
		if(this.state != GameStates.EVENT_LOGIC)
			return;

		/*
		 * ACTION : Apply events logic to players
		 */

		this.state = GameStates.BEGIN_TURN;
	}

}

