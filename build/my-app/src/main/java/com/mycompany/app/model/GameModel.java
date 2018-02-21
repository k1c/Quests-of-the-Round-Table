package com.mycompany.app.model;


import java.util.*;
import com.mycompany.app.model.Card;


public class GameModel{

	private List<GameObserver> observers;
	private GameStates state;

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

	public GameModel(){
		observers = new ArrayList<GameObserver>();
		board = new GameBoard();
		
		}

	public void initGame(int NumHumans,int numAI){
		turn = 0;

		numberOfPlayers = numberOfPlayers;

		board.initGame(numberOfPlayers,CardLoader.loadAdventureCards(),new ArrayList<StoryCard>());
		players = board.getPlayerIds();		
		storyTurn = new Cycle<Integer>(players,0);
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

	public GenericPlayer getCurrentPlayer(){
		int p = storyTurn.current();
		return board.getGenericPlayer(p);
	}

	public List<GenericPlayer> getWaitingPlayers(){
		Cycle<Integer> waitingPlayersCycle = (new Cycle(storyTurn));
		waitingPlayersCycle.removeCurrent();
		List<Integer>  waitingPlayers = waitingPlayersCycle.items();

		List<GenericPlayer> temp = new ArrayList();

		for(Integer id: waitingPlayers)
			temp.add(board.getGenericPlayer(id));	

		return temp;
	}

	

	public void nextTurn(){
		if (this.state != GameStates.BEGIN_TURN)
			return;

		this.turn++;
		this.currentPlayer = storyTurn.next();
		

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
			// start a cycle  with the sponsor as current player
			questSponsor = new Cycle<Integer>(players,players.indexOf(storyTurn.current()));
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
	public void sponsorQuest(int player,boolean sponsor){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;

		/*
		 * Verify that they can sponsor with current cards
		 */
		if(player == questSponsor.current() && sponsor && board.playerCanSponsor(player)){
			this.state = GameStates.SPONSOR_SUBMIT;
		}
		else if(player == questSponsor.current() && !sponsor){
			questSponsor.removeCurrent();
		}
		else if(player == questSponsor.current() && !board.playerCanSponsor(player)){
			questSponsor.removeCurrent();	
		}


		/*
		 * Check if there are any more items
		 */
		if(questSponsor.size() <= 0){
			this.state = GameStates.BEGIN_TURN;
		}
		
		this.updateObservers();

	}

	public void noSponsor(){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;
		this.state = GameStates.BEGIN_TURN;
		this.updateObservers();
	}

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
		if(questSponsor.current() == player && board.submitQuest(quest,player))
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
			state = GameStates.QUEST_HANDLER;
	}
	

	public void beginQuest(){
		if(this.state != GameStates.QUEST_HANDLER)
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
		board.applyStoryCardLogic(questSponsor.current());

		this.state = GameStates.BEGIN_TURN;
		this.updateObservers();
	}

}

