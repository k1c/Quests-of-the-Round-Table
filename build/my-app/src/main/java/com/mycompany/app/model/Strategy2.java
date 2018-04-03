
package com.mycompany.app.model;

import java.lang.*;
import java.util.*;

public class Strategy2 extends AbstractStrategyBehaviour{

	protected List<AdventureCard> allPossibleCards(GameBoard board, AbstractAI ai){

		Set<AdventureCard> set = new TreeSet(); List<AdventureCard> temp = new ArrayList();

		boolean amourInPlay = false;

		amourInPlay = board.cardListHas(ai.inPlay,Card.Types.AMOUR);

		for(AdventureCard item : ai.hand){
			if (item.type == Card.Types.ALLY){
				set.add(item);
			}
			if (item.type == Card.Types.WEAPON){
				set.add(item);
			}
			if(item.type == Card.Types.AMOUR && !amourInPlay){
				set.add(item);
				amourInPlay = true;
			}

			continue;
		}

		temp.addAll(set);
		return temp;
	}

	// Returns the maximum value that can be put in a knapsack of capacity W
	/*
	 * Description : Checks if the AI should participate in tournament
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInTournament(GameBoard board,AbstractAI ai){
		return true;
	}


	/*
	 * Description : Returns a list of cards to play in that tournament round
	 * Return Type : List<Card>
	 */
	public List<Card> playInTournament(GameBoard board, AbstractAI ai){

		List<Card> temp = new ArrayList();

		int points = Math.max(ai.getTotalBP(board) - 50,0);



		int totalBP = board.calculateBP(allPossibleCards(board, ai));
		//get total BP of allPossibleCards

		/* case where ai can obtain 50 */
		if(totalBP >= Math.abs(points)) {   //see if they have >= 50 BP
			while (points < 0) {
				List<AdventureCard> aiInHand = allPossibleCards(board, ai);
				AdventureCard maxCard = null;
				int maxBP = 0;
				for (int i = 0; i < aiInHand.size(); i++) {
					if (aiInHand.get(i).getBattlePoints(board) > maxBP) {
						maxBP = aiInHand.get(i).getBattlePoints(board);
						maxCard = aiInHand.get(i);
					}
				}
				temp.add(maxCard);
				aiInHand.remove(maxCard);
				points += maxBP;

				//assumption: assumed at least 50 BP; if its "exactly 50" we'll make a change
			}
		}else{
				/* case where ai cannot obtain 50 */
				temp.addAll(allPossibleCards(board, ai));
			}

		
		return temp;
	}




	/* Description : Returns whether AI will sponsor a quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doISponsorAQuest(GameBoard board, AbstractAI ai){
		return false;
	}

	/*
	 * Description : Returns a setup Quest
	 * Return Type : TwoDimensionalArrayList<Card>
	 */
	public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board, AbstractAI ai){
		return new TwoDimensionalArrayList();
	}


	/* Description : Returns whether AI will participate in quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInQuest(GameBoard board, AbstractAI ai){
		return false;
	}

	/*
	 * Description : Returns a list of cards to play in that Quest round
	 * Return Type : List<Card>
	 */
	public List<Card> playQuest(GameBoard board, AbstractAI ai){
		return new ArrayList<>();
	}

	/*
	 * Description : Returns a list of cards to play in that test round
	 * Return Type : List<Card>
	 */
	public List<Card> nextBid(GameBoard board, AbstractAI ai){
		return new ArrayList<>();
	}

	/*
	 * ??? Discard Functionality I guess
	 * Return Type : List<Card> to discard or put int play
	 */
	public List<Card> discardAfterWinningTest(GameBoard board, AbstractAI ai){
		return new ArrayList<>();
	}

}
