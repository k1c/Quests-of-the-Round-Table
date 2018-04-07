
package com.mycompany.app.model;

import com.mycompany.app.model.Card;
import com.mycompany.app.model.TwoDimensionalArrayList;

import java.lang.*;
import java.util.*;



public class Strategy2 extends AbstractStrategyBehaviour{

	private static boolean isFirstRound = true;

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
	public boolean doISponsorAQuest(GameBoard board, AbstractAI ai) {

		List<Player> players = board.getParticipantPlayers();

		for (Player p : players) {
			if (p.id != ai.id){
				if (p.rank.getShields() + (board.getCurrentQuestStages() - players.size()) >= p.rank.getMaxShields()) {
					return false;
				}
			}
		}
		if(canISetup2 == false){
			return false;
		}

		if (board.playerCanSponsor(ai.id)) {
			return true;
		}
		return false;
	}


	private boolean canISetup2(GameBoard board, AbstractAI ai){

		ArrayList<Card> foeList = new ArrayList<Card>();
		ArrayList<Card> weaponList = new ArrayList<Card>();

		for(int i = 0; i < ai.hand.size(); i++){
			if(ai.hand.get(i).type == Card.Types.FOE){
				foeList.add(ai.hand.get(i));
			}
			if(ai.hand.get(i).type == Card.Types.WEAPON){
				weaponList.add(ai.hand.get(i));
			}
		}

		//set up last stage to be at least 40
		int questStageBP = 0;
		questStageBP += foeList.get(foeList.size() - 1).getBattlePoints();
		if(questStageBP < 40) {
			for (int j = weaponList.size() - 1; j > 0; j--) {
				questStageBP += weaponList.get(j).getBattlePoints();
				if (questStageBP >= 40) {
					return true;
				}
			}
		}
		return false;
	}



	/*
	 * Description : Returns a setup Quest
	 * Return Type : TwoDimensionalArrayList<Card>
	 */
	public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board, AbstractAI ai){

		TwoDimensionalArrayList<Card> aiQuest = new TwoDimensionalArrayList<Card>();
		ArrayList<Card> foeList = new ArrayList<Card>();
		ArrayList<Card> testList = new ArrayList<Card>();
		ArrayList<Card> weaponList = new ArrayList<Card>();

		for(int i = 0; i < ai.hand.size(); i++){
			if(ai.hand.get(i).type == Card.Types.FOE){
				foeList.add(ai.hand.get(i));
			}
			if(ai.hand.get(i).type == Card.Types.TEST){
				testList.add(ai.hand.get(i));
			}
			if(ai.hand.get(i).type == Card.Types.WEAPON){
				weaponList.add(ai.hand.get(i));
			}
		}

		//set up last stage to be at least 40
		int questStageBP = 0;
		ArrayList<Card> stageN = new ArrayList<Card>();
		questStageBP += foeList.get(foeList.size() - 1).getBattlePoints();
		stageN.add(foeList.get(foeList.size() - 1));
		foeList.remove(foeList.get(foeList.size() - 1));
		if(questStageBP < 40) {
			for (int j = weaponList.size() - 1; j > 0; j--) {
				questStageBP += weaponList.get(j).getBattlePoints();
				stageN.add(weaponList.get(j));
				if (questStageBP >= 40) {
					break;
				}
			}
		}
		aiQuest.add(board.currentStory.getNumStages()-1, stageN);

		if(testList.size() > 0) {
			ArrayList<Card> testStage = new ArrayList<Card>();
			testStage.add(testList.get(0));
			aiQuest.add(currentStory.getNumStages() - 2, testStage);
			for (int i = 0; i < currentStory.getNumStages() - 2; i++) {
				ArrayList<Card> temp = new ArrayList<Card>();
				temp.add(foeList.get(i));
				aiQuest.add(i, temp);
			}
		} else {
			for (int i = 0; i < currentStory.getNumStages() - 1; i++) {
				ArrayList<Card> temp = new ArrayList<Card>();
				temp.add(foeList.get(i));
				aiQuest.add(i, temp);
			}
		}

		return aiQuest;
	}


	/* Description : Returns whether AI will participate in quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInQuest(GameBoard board, AbstractAI ai){

	   int numStages = board.getNumQuestCards();
	   List<AdventureCard> aiInHand = allPossibleCards(board,ai);

	   //C1
	   int counter = 0;
	   int currentBP = 0;
	   int previousBP = 0;

	   for(int i = 0; i < aiInHand.size(); i++) {
           if (aiInHand.get(i).getBattlePoints() >= 10) {
               currentBP = aiInHand.get(i).getBattlePoints();
               if ((currentBP - previousBP) >= 10) {
                   counter++;
                   if (counter >= numStages) {
                       break;
                   }
                   previousBP = currentBP;
               }
           }
       }

        //C2
        //ai.hand type.FOE, check BP

        int foeCounter = 0;

        for(int i = 0; i < ai.hand.size(); i++){
	       if (ai.hand.get(i).type == Card.Types.FOE){
	           if(ai.hand.get(i).getBattlePoints() < 25){
	               foeCounter++;
	               if(foeCounter == 2){
	                   return true;
                   }
               }
           }
       }

		return false;
	}

	/*
	 * Description : Returns a list of cards to play in that Quest round
	 * Return Type : List<Card>
	 */
	public List<Card> playQuest(GameBoard board, AbstractAI ai){

	    //board.quest --> 2D array list
	    //board.getQuestIndex() --> current stage number
        if (board.quest.get(board.getQuestIndex()).get(0).type == Card.Types.TEST){
            return nextBid(board, ai);
        }


		return new ArrayList<>();
	}

	/*
	 * Description : Returns a list of cards to play in that test round
	 * Return Type : List<Card>
	 */
	public List<Card> nextBid(GameBoard board, AbstractAI ai){

		List<Player> players = board.getParticipantPlayers();
		int currentMax = 0;

		for(Player p: players){
			if (board.totalPlayerBids(p) > currentMax){
				currentMax = board.totalPlayerBids(p);
			}
		}

		if(isFirstRound) {
			List<Card> aiBids = new List<Card>();

			for (int i = 0; i < ai.hand.size(); i++) {
				if (ai.hand.get(i).type == Card.Types.FOE) {
					if (ai.hand.get(i).getBattlePoints() < 25) {
						aiBids.add(ai.hand.get(i));
					}
				}
			}
			isFirstRound = false;
		}

		if(isFirstRound == false) {
			List<Card> aiBids = new List<Card>();
			List<AdventureCard> tempAIHand = ai.hand;

			for (int i = 0; i < tempAIHand.size(); i++) {
				if (tempAIHand.get(i).type == Card.Types.FOE) {
					if (tempAIHand.get(i).getBattlePoints() < 25) {
						aiBids.add(tempAIHand.get(i));
						tempAIHand.remove(tempAIHand.get(i));
					}
				}
			}

			Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>();
			for (int i = 0; i < tempAIHand.size(); i++) {
				if (cardDuplicates.get(tempAIHand.get(i)) != null) {
					cardDuplicates.put(tempAIHand.get(i), cardDuplicates.get(tempAIHand.get(i)) + 1);
				} else {
					cardDuplicates.put(tempAIHand.get(i), 1);
				}
			}

			for (Map.Entry<Card, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 1) {
					aiBids.add(entry.getKey());
				}
			}
		}

		if (aiBids.size() > currentMax){
			return aiBids;
		}

		return new ArrayList<>(); //return null?
	}

	/*
	 * ??? Discard Functionality I guess
	 * Return Type : List<Card> to discard or put int play
	 */
	public List<Card> discardAfterWinningTest(GameBoard board, AbstractAI ai){

		if(isFirstRound) {
			List<Card> aiBids = new List<Card>();

			for (int i = 0; i < ai.hand.size(); i++) {
				if (ai.hand.get(i).type == Card.Types.FOE) {
					if (ai.hand.get(i).getBattlePoints() < 25) {
						aiBids.add(ai.hand.get(i));
					}
				}
			}
			isFirstRound = false;
		}

		if(isFirstRound == false) {
			List<Card> aiBids = new List<Card>();
			List<AdventureCard> tempAIHand = ai.hand;

			for (int i = 0; i < tempAIHand.size(); i++) {
				if (tempAIHand.get(i).type == Card.Types.FOE) {
					if (tempAIHand.get(i).getBattlePoints() < 25) {
						aiBids.add(tempAIHand.get(i));
						tempAIHand.remove(tempAIHand.get(i));
					}
				}
			}

			Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>();
			for (int i = 0; i < tempAIHand.size(); i++) {
				if (cardDuplicates.get(tempAIHand.get(i)) != null) {
					cardDuplicates.put(tempAIHand.get(i), cardDuplicates.get(tempAIHand.get(i)) + 1);
				} else {
					cardDuplicates.put(tempAIHand.get(i), 1);
				}
			}

			for (Map.Entry<Card, Integer> entry : map.entrySet()) {
				if (entry.getValue() > 1) {
					aiBids.add(entry.getKey());
				}
			}
		}
		return aiBids;
	}

}
