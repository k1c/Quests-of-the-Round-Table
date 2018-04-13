package com.mycompany.app.model;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.util.List;
import java.util.ArrayList;

public abstract class AbstractAI extends Player{

	AbstractStrategyBehaviour behaviour;


	public AbstractAI(String name, String shieldImage){
		super(name,shieldImage);
		this.type = AbstractPlayer.Type.AI;
	}


	/*
	 * Description : Checks if the AI should participate in tournament
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInTournament(GameBoard board) {
		return behaviour.doIParticipateInTournament(board,this);
	}


	/*
	 * Description : Returns a list of cards to play in that tournament round
	 * Return Type : List<Card> 
	 */
	public List<Card> playInTournament(GameBoard board){
		return behaviour.playInTournament(board,this);
	}

	/* Description : Returns whether AI will sponsor a quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doISponsorAQuest(GameBoard board){
		return behaviour.doISponsorAQuest(board,this);
	}

	/*
	 * Description : Returns a setup Quest
	 * Return Type : TwoDimensionalArrayList<Card>  
	 */
	public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board){
		return behaviour.sponsorQuest(board,this);
	}


	/* Description : Returns whether AI will participate in quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInQuest(GameBoard board){
		return behaviour.doIParticipateInQuest(board,this);
	}

	/*
	 * Description : Returns a list of cards to play in that Quest round
	 * Return Type : List<Card> 
	 */
	public List<Card> playQuest(GameBoard board){
		return behaviour.playQuest(board,this);
	}

	/*
	 * Description : Returns a list of cards to play in that test round
	 * Return Type : List<Card> 
	 */
	public List<Card> nextBid(GameBoard board){
		return behaviour.nextBid(board,this);

	}

	/*
	 * ??? Discard Functionality I guess
	 * Return Type : List<Card> to discard or put int play
	 */
	public List<Card> discardAfterWinningTest(GameBoard board){
		return behaviour.discardAfterWinningTest(board,this);
	}

	public List<Card> discard(GameBoard board){
		List<Card> temp = new ArrayList<Card>();
		for(int i = 0; i <this.hand.size()-12; i++){
			temp.add(this.hand.get(i));	
		}
		return temp;
	}
}
