package com.mycompany.app.model;


import com.mycompany.app.GameObserver;
import com.mycompany.app.model.*;
import com.mycompany.app.model.GameStates;
import java.util.*;


public class GameModel{

	private ArrayList<GameObserver> observers;
	private GameStates state;
	private int numberOfPlayers;
	private int currentPlayer;
	private int turn;

	public GameModel(){
		observers = new ArrayList<GameObserver>();
	}

	public void registerObserver(GameObserver o){
		this.observers.add(o);
	}

	public void deregisterObserver(GameObserver o){
		this.observers.remove(o);
	}

	public void updateObservers(){
		for(Iterator<GameObserver> i = this.observers.iterator(); i.hasNext();){
			i.next().update();
		}
	}


	public void nextTurn(){
		if (this.state != GameStates.BEGIN_TURN)
			return;

		this.currentPlayer = (this.currentPlayer + 1) % numberOfPlayers;


		/*
		 * Action : Check if any players have won
		 */


		/*
		 * Action: Draw from Story Deck
		 */

		
		if (Card.Types.EVENT == Card.Types.EVENT){
			this.state = GameStates.EVENT_LOGIC;
		}
		else if (Card.Types.QUEST == Card.Types.QUEST){
			this.state = GameStates.SPONSOR_QUEST;
		}
		else if (Card.Types.TOURNAMENT == Card.Types.TOURNAMENT){
			this.state = GameStates.PARTICIPATE_TOURNAMENT;
		}

		/*
		 * Need : Need to specify what kind of action to update observers
		 */

		this.updateObservers();
	}



	/*
	 * NEEDS : change player parameter to a Player Object
	 */
	public void sponsor_quest(int player){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;

		/*
		 * Verify that they can sponsor with current cards
		 */
		if(true)	
			this.state = GameStates.SPONSOR_SUBMIT;

	}

	public void no_sponsor(){
		if(this.state != GameStates.SPONSOR_QUEST)
			return;
		this.state = GameStates.BEGIN_TURN;
	}

	/*
	 * NEEDS : change player parameter to a Player Object
	 * NEEDS : some kind of quest object to submit
	 */
	public void submit_quest(int player){
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

