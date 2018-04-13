
package com.mycompany.app.model;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.lang.*;
import java.util.*;

public class Strategy2 extends AbstractStrategyBehaviour{

	/*
	 * Description : Checks if the AI should participate in tournament
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */

	private static boolean isFirstRound = true;
	private static int counter = 0;
	GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

	public boolean doIParticipateInTournament(GameBoard board, AbstractAI ai){

		return true;
	}


	/*
	 * Description : Returns a list of cards to play in that tournament round
	 * Return Type : List<Card>
	 */
	public List<Card> playInTournament(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Getting cards to play in tournament");

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

		log.playerTournament(ai,temp);
		return temp;
	}

	/* Description : Returns whether AI will sponsor a quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */

	public boolean doISponsorAQuest(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Deciding whether to sponsor a quest");

		List<Player> players = board.getParticipantPlayers();   //change to all players

		for (Player p : players) {
			if (p.id != ai.id){
				if (p.rank.getShields() + (board.getCurrentQuestStages() + players.size()) >= p.rank.getMaxShields()) {
					log.playerAction(p,"can win the quest");
					log.count("shields + stages + num players, ", p.rank.getShields() + (board.getCurrentQuestStages() + players.size()));
					log.count("rank max shields, ", p.rank.getMaxShields());
					return false;
				}
			}
		}

		if(canISetup2(board,ai) == false){
			return false;
		}

		if (board.playerCanSponsor(ai.id)) {
			return true;
		}

		return false;
	}


	private boolean canISetup2(GameBoard board, AbstractAI ai){

		ArrayList<AdventureCard> foeList = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> weaponList = new ArrayList<AdventureCard>();

		for(int i = 0; i < ai.hand.size(); i++){
			if(ai.hand.get(i).type == Card.Types.FOE){
				foeList.add(ai.hand.get(i));
			}
			if(ai.hand.get(i).type == Card.Types.WEAPON){
				weaponList.add(ai.hand.get(i));
			}
		}

		if (foeList.size() == 0) {
			return false;
		}

		//set up last stage to be at least 50
		int questStageBP = 0;
		questStageBP += foeList.get(foeList.size() - 1).getBattlePoints(board);
		if(questStageBP < 40) {
			for (int j = weaponList.size() - 1; j > 0; j--) {
				questStageBP += weaponList.get(j).getBattlePoints(board);
				if (questStageBP >= 40) {
					log.count("can setup quest 1", questStageBP);
					return true;
				}
			}
		}
		log.count("cannot setup quest 1", questStageBP);
		return false;
	}

	/*
	 * Description : Returns a setup Quest
	 * Return Type : TwoDimensionalArrayList<Card>
	 */
	public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Getting the cards to sponsor a quest");

		TwoDimensionalArrayList<Card> aiQuest = new TwoDimensionalArrayList<Card>();
		ArrayList<AdventureCard> foeList = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> testList = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> weaponList = new ArrayList<AdventureCard>();

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

		Set<AdventureCard> uniqueWeapons = new TreeSet<AdventureCard>();
		uniqueWeapons.addAll(weaponList);

		ArrayList<AdventureCard> uw2 = new ArrayList<AdventureCard>();
		uw2.addAll(uniqueWeapons);
		//set up last stage to be at least 40
		int questStageBP = 0;
		ArrayList<Card> stageN = new ArrayList<Card>();
		questStageBP += foeList.get(foeList.size() - 1).getBattlePoints(board);
		stageN.add(foeList.get(foeList.size() - 1));
		foeList.remove(foeList.get(foeList.size() - 1));
		// TODO: change to 50
		if(questStageBP < 40) {
			for (int j = uw2.size() - 1; j > 0; j--) {
				questStageBP += uw2.get(j).getBattlePoints(board);
				stageN.add(uw2.get(j));
				if (questStageBP >= 40) {
					break;
				}
			}
		}

		log.count("AI Quest Size", aiQuest.size());

		log.count("Quest Stages - 1 (for indexing)",board.currentStory.getNumStages() -  1);

		for (Card c : stageN) {
			aiQuest.addToInnerArray(board.currentStory.getNumStages() - 1, c);
		}
		//aiQuest.add(board.currentStory.getNumStages()-1, stageN);

		if(testList.size() > 0) {
			int foeSize = foeList.size()-1;

			log.playerAction(ai, "Adding the test");
			ArrayList<Card> testStage = new ArrayList<Card>();
			testStage.add(testList.get(0));

			aiQuest.addToInnerArray(board.currentStory.getNumStages() - 2, testList.get(0));
			//aiQuest.add(board.currentStory.getNumStages() - 2, testStage);

			for (int i = 0; i < board.currentStory.getNumStages() - 2; i++) {
				aiQuest.addToInnerArray(i, foeList.get(foeSize));
				foeSize--;
				log.cardPlayed(ai,foeList.get(i),"to sponsor quest");
			}
		} else {
			int foeSize = foeList.size()-1;

			for (int i = 0; i < board.currentStory.getNumStages() - 1; i++) {
				aiQuest.addToInnerArray(i, foeList.get(foeSize));
				log.cardPlayed(ai,foeList.get(i),"to sponsor quest");
				foeSize--;
			}
		}

		return aiQuest;
	}


	/* Description : Returns whether AI will participate in quest or not
	 * Return Type : TRUE -- I want to participate
	 * 		 FALSE - I do not participate
	 */
	public boolean doIParticipateInQuest(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Deciding whether to participate in quest");

		int numStages = board.getNumQuestCards();
		List<AdventureCard> aiInHand = allPossibleCards(board,ai);

		//C1
		int counter = 0;
		int currentBP = 0;
		int previousBP = 0;

		for(int i = 0; i < aiInHand.size(); i++) {
			if (aiInHand.get(i).getBattlePoints(board) >= 10) {
				currentBP = aiInHand.get(i).getBattlePoints(board);
				if ((currentBP - previousBP) >= 10) {
					counter++;
					if (counter >= numStages) {
						log.playerAction(ai, "can increment by 10 at each stage");
						break;
					}
					previousBP = currentBP;
				}
			}
		}

		//C2

		int foeCounter = 0;

		for(int i = 0; i < ai.hand.size(); i++){
			if (ai.hand.get(i).type == Card.Types.FOE){
				if(ai.hand.get(i).getBattlePoints(board) < 25){
					foeCounter++;
					if(foeCounter == 2){
						log.playerAction(ai,"has at least 2 foes of < 25 points");
						return true;
					}
				}
			}
		}
		log.playerAction(ai, "cannot increment by 10 at each stage OR does not have at least 2 foes of < 25 points");
		log.playerAction(ai, "cannot participate in quest");
		return false;
	}

	/*
	 * Description : Returns a list of cards to play in that Quest round
	 * Return Type : List<Card>
	 */
	public List<Card> playQuest(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Getting cards to play in quest");
		ArrayList<AdventureCard> temp = new ArrayList<AdventureCard>();

		if (board.quest.get(board.getQuestIndex()).get(0).type == Card.Types.TEST){
			return nextBid(board, ai);
		} else if ((board.getQuestIndex() + 1) == board.getCurrentQuestStages()){
			log.playerQuest(ai,allPossibleCards(board,ai)),"Adventure deck");
			return toCards(allPossibleCards(board,ai));
		} else {
			List<AdventureCard> allPos = allPossibleCards(board,ai);

			ArrayList<AdventureCard> amourList = new ArrayList<AdventureCard>();
			ArrayList<AdventureCard> allyList = new ArrayList<AdventureCard>();
			ArrayList<AdventureCard> weaponList = new ArrayList<AdventureCard>();

			for (int i = 0; i < allPos.size(); i++) {
				if (allPos.get(i).type == Card.Types.AMOUR) {
					amourList.add(allPos.get(i));
				}
				if (allPos.get(i).type == Card.Types.ALLY) {
					allyList.add(allPos.get(i));
				}
				if (allPos.get(i).type == Card.Types.WEAPON) {
					weaponList.add(allPos.get(i));
				}
			}

			int currentBP = 0;
			int previousBP = 0;

			for(int i = 0; i < allPos.size(); i++){
				if(allPos.get(i).type == Card.Types.AMOUR){
					currentBP += allPos.get(i).getBattlePoints(board);
					if ((currentBP - previousBP) >= 10) {
						temp.add(allPos.get(i));
					}
				}
				if(allPos.get(i).type == Card.Types.ALLY){
					currentBP += allPos.get(i).getBattlePoints(board);
					if ((currentBP - previousBP) >= 10) {
						temp.add(allPos.get(i));
					}
				}
				if(allPos.get(i).type == Card.Types.WEAPON){
					currentBP += allPos.get(i).getBattlePoints(board);
					if ((currentBP - previousBP) >= 10) {
						temp.add(allPos.get(i));
					}
				}
				previousBP = currentBP;
			}
		}
		log.playerQuest(ai,toCards(temp));
		return toCards(temp);
	}

	/*
	 * Description : Returns a list of cards to play in that test round
	 * Return Type : List<Card>
	 */
	public List<Card> nextBid(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Getting cards to bid in test");


		List<Player> players = board.getParticipantPlayers();   //make this a helper function for both AI strategies
		int currentMax = 0;

		for(Player p: players){
			if (board.totalPlayerBids(p) > currentMax){
				currentMax = board.totalPlayerBids(p);
			}
		}														//end max helper function

		List<Card> aiBids = new ArrayList<Card>();
		if(counter%2==0) {

			for (int i = 0; i < ai.hand.size(); i++) {
				if (ai.hand.get(i).type == Card.Types.FOE) {
					if (ai.hand.get(i).getBattlePoints(board) < 25) {
						aiBids.add(ai.hand.get(i));
					}
				}
			}
			counter++;
			//isFirstRound = false;

		} else if(counter%2!=0) {
			List<AdventureCard> tempAIHand = ai.hand;

			for (int i = 0; i < tempAIHand.size(); i++) {
				if (tempAIHand.get(i).type == Card.Types.FOE) {
					if (tempAIHand.get(i).getBattlePoints(board) < 25) {
						aiBids.add(tempAIHand.get(i));
						tempAIHand.remove(tempAIHand.get(i));
					}
				}
			}

			Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>(); //make this a helper function for both AI strategies
			for (int i = 0; i < tempAIHand.size(); i++) {
				if (cardDuplicates.get(tempAIHand.get(i)) != null) {
					cardDuplicates.put(tempAIHand.get(i), cardDuplicates.get(tempAIHand.get(i)) + 1);
				} else {
					cardDuplicates.put(tempAIHand.get(i), 1);
				}
			}

			for (Map.Entry<Card, Integer> entry : cardDuplicates.entrySet()) {
				if (entry.getValue() > 1) {
					aiBids.add(entry.getKey());
				}
			}  //end cardDuplicates helper function
			counter++;
			//isFirstRound = true;
		}

		if (aiBids.size() > currentMax){
			log.playerBid(ai,aiBids);
			return aiBids;
		}
		log.playerAction(ai,"no bids");
		return new ArrayList<>(); //return null?
	}

	/*
	 * ??? Discard Functionality I guess
	 * Return Type : List<Card> to discard or put int play
	 */
/*	public List<Card> discardAfterWinningTest(GameBoard board, AbstractAI ai){

		log.playerAction(ai, "Getting cards to discard after winning test");

		List<Card> aiBids = new ArrayList<Card>();

		if(isFirstRound) {

			for (int i = 0; i < ai.hand.size(); i++) {
				if (ai.hand.get(i).type == Card.Types.FOE) {
					if (ai.hand.get(i).getBattlePoints(board) < 25) {
						aiBids.add(ai.hand.get(i));
					}
				}
			}
			isFirstRound = false;
		}

		if(isFirstRound == false) {
			List<AdventureCard> tempAIHand = ai.hand;

			for (int i = 0; i < tempAIHand.size(); i++) {
				if (tempAIHand.get(i).type == Card.Types.FOE) {
					if (tempAIHand.get(i).getBattlePoints(board) < 25) {
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

			for (Map.Entry<Card, Integer> entry : cardDuplicates.entrySet()) {
				if (entry.getValue() > 1) {
					aiBids.add(entry.getKey());
				}
			}
			isFirstRound = true;
		}
		log.cardPlayed(ai,aiBids,"Adventure deck");
		return aiBids;
	}*/

}
