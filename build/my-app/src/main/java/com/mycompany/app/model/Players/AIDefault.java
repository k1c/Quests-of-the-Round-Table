package com.mycompany.app.model;
import java.util.List;
import java.util.ArrayList;

public class AIDefault extends AbstractAI{

	private static int idCount = 0;
	protected int id;
	public Rank rank;
	public String name;
	public String shieldImage;



	public AIDefault(String name, String shieldImage){
		super(name,shieldImage);
	}


	/*
	 * Description : Checks if the AI should participate in tournament
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInTournament(GameBoard board){
		return false;
	}


	/*
	 * Description : Returns a list of cards to play in that tournament round
	 * Return Type : List<Card> 
	 */
	public List<Card> playInTournament(GameBoard board){
		return new ArrayList();
	}

	/* Description : Returns whether AI will sponsor a quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doISponsorAQuest(GameBoard board){
		return false;
	}

	/*
	 * Description : Returns a setup Quest
	 * Return Type : TwoDimensionalArrayList<Card>  
	 */
	public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board){
		return new TwoDimensionalArrayList();
	}


	/* Description : Returns whether AI will participate in quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInQuest(GameBoard board){
		return false;
	}

	/*
	 * Description : Returns a list of cards to play in that Quest round
	 * Return Type : List<Card> 
	 */
	public List<Card> playQuest(GameBoard board){
		return new ArrayList();
	}

	/*
	 * Description : Returns a list of cards to play in that test round
	 * Return Type : List<Card> 
	 */
	public List<Card> nextBid(GameBoard board){
		return new ArrayList();
	}

	/*
	 * ??? Discard Functionality I guess
	 * Return Type : List<Card> to discard or put int play
	 */
	public List<Card> discardAfterWinningTest(GameBoard board){
		return new ArrayList();
	}
}
