package com.mycompany.app.model;
import java.util.List;

public abstract class AbstractAI extends Player{

	private static int idCount = 0;
	protected int id;
	public Rank rank;
	public String name;
	public String shieldImage;

	/*
	 * behaviour 1
	 * behaviour 2
	 * behaviour 3
	 * behaviour 4
	 */



	public AbstractAI(String name, String shieldImage){
		super(name,shieldImage);
		this.type = AbstractPlayer.Type.AI;
	}


	/*
	 * Description : Checks if the AI should participate in tournament
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	abstract public boolean doIParticipateInTournament(GameBoard board);


	/*
	 * Description : Returns a list of cards to play in that tournament round
	 * Return Type : List<Card> 
	 */
	abstract public List<Card> playInTournament(GameBoard board);

	/* Description : Returns whether AI will sponsor a quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	abstract public boolean doISponsorAQuest(GameBoard board);

	/*
	 * Description : Returns a setup Quest
	 * Return Type : TwoDimensionalArrayList<Card>  
	 */
	abstract public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board);


	/* Description : Returns whether AI will participate in quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	abstract public boolean doIParticipateInQuest(GameBoard board);

	/*
	 * Description : Returns a list of cards to play in that Quest round
	 * Return Type : List<Card> 
	 */
	abstract public List<Card> playQuest(GameBoard board);

	/*
	 * Description : Returns a list of cards to play in that test round
	 * Return Type : List<Card> 
	 */
	abstract public List<Card> nextBid(GameBoard board);

	/*
	 * ??? Discard Functionality I guess
	 * Return Type : List<Card> to discard or put int play
	 */
	abstract public List<Card> discardAfterWinningTest(GameBoard board);
}
