package com.mycompany.app.model;

import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Strategy1 extends AbstractStrategyBehaviour{
    /*
     * Description : Checks if the AI should participate in tournament
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */

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


    public boolean doIParticipateInTournament(GameBoard board, AbstractAI ai){

        List<Player> players = board.players;
        int currentRank = 0;
        boolean win = false;

        ViewGameBoard b = board.getViewCopy();

        for (Player p : players ) {
            if (p.rank.getShields() + (board.getCurrentQuestStages() + players.size()) >= p.rank.getMaxShields()) { //getCurrentQuestStages() gets the number of bonus shields
                return true;
            }
        }

        return false;
    }


    /*
     * Description : Returns a list of cards to play in that tournament round
     * Return Type : List<Card>
     */
    public List<Card> playInTournament(GameBoard board, AbstractAI ai){

        List<Player> players = board.getParticipantPlayers();
        int currentRank = 0;
        boolean win = false;

        ViewGameBoard b = board.getViewCopy();

        for (Player p : players ) {
            if (p.rank.getShields() + (board.getCurrentQuestStages() - players.size()) >= p.rank.getMaxShields()) {
                return allPossibleCards(board,ai);
            }
        }

        List<Card> aiTournament = new List<Card>();
        List<AdventureCard> tempAIHand = ai.hand;
        List<AdventureCard> tempWeapons = new ArrayList<AdventureCard>();

        for(int i = 0; i < tempAIHand.size(); i++){
            if (tempAIHand.get(i).type == Card.Types.WEAPON){
                tempWeapons.add(tempAIHand.get(i));
            }
        }

        //TODO: override equalsTo
        Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>();
        for (int i = 0; i < tempWeapons.size(); i++) {
            if (cardDuplicates.get(tempWeapons.get(i)) != null) {
                cardDuplicates.put(tempWeapons.get(i), cardDuplicates.get(tempWeapons.get(i)) + 1);
            } else {
                cardDuplicates.put(tempWeapons.get(i), 1);
            }
        }

        for (Map.Entry<Card, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                aiTournament.add(entry.getKey());
            }
        }

        return aiTournament;

       // Set weapons = aiTournament.keySet(); //returning one of each duplicate weapon

        //return weapons;
    }

    /* Description : Returns whether AI will sponsor a quest or not
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */

    public boolean doISponsorAQuest(GameBoard board, AbstractAI ai){

        List<Player> players = board.getParticipantPlayers();

        for (Player p : players) {
            if (p.id != ai.id){
                if (p.rank.getShields() + (board.getCurrentQuestStages() + players.size()) >= p.rank.getMaxShields()) {
                    return false;
                }
            }
        }
        if(canISetup1(board,ai) == false){
            return false;
        }

        if (board.playerCanSponsor(ai.id)) {
            return true;
        }
        return false;
    }


    private boolean canISetup1(GameBoard board, AbstractAI ai){

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

        //set up last stage to be at least 50
        int questStageBP = 0;
        questStageBP += foeList.get(foeList.size() - 1).getBattlePoints();
        if(questStageBP < 50) {
            for (int j = weaponList.size() - 1; j > 0; j--) {
                questStageBP += weaponList.get(j).getBattlePoints();
                if (questStageBP >= 50) {
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

        //set up last stage to be at least 50
        int questStageBP = 0;
        ArrayList<Card> stageN = new ArrayList<Card>();
        questStageBP += foeList.get(foeList.size() - 1).getBattlePoints();
        stageN.add(foeList.get(foeList.size() - 1));
        foeList.remove(foeList.get(foeList.size() - 1));
        if(questStageBP < 50) {
            for (int j = weaponList.size() - 1; j > 0; j--) {
                questStageBP += weaponList.get(j).getBattlePoints();
                stageN.add(weaponList.get(j));
                if (questStageBP >= 50) {
                    break;
                }
            }
        }
        aiQuest.add(board.currentStory.getNumStages()-1, stageN);

        if(testList.size() > 0) {
            ArrayList<Card> testStage = new ArrayList<Card>();
            testStage.add(testList.get(0));
            aiQuest.add(currentStory.getNumStages() - 2, testStage);


            List<Card> weapons = new List<Card>();
            Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>();
            for (int i = 0; i < weaponList.size(); i++) {
                if (cardDuplicates.get(weaponList.get(i)) != null) {
                    cardDuplicates.put(weaponList.get(i), cardDuplicates.get(weaponList.get(i)) + 1);
                } else {
                    cardDuplicates.put(weaponList.get(i), 1);
                }
            }

            //TODO: add duplicated cards

            for (Map.Entry<Card, Integer> entry : map.entrySet()) {
                if (entry.getValue() > 1) {
                    weapons.add(entry.getKey());
                }
            }

            int foeSize = foeList.size()-1;
            int weaponSize = weapons.size()-1;
            for (int i = currentStory.getNumStages() - 2; i > 0; i--) {
                ArrayList<Card> temp = new ArrayList<Card>();
                temp.add(foeList.get(foeSize));
                foeSize--;
                temp.add(weapons.get(weaponSize));
                weaponSize--;
                //check for increasing BPs
                aiQuest.add(i, temp);
            }

        } else {
            int foeSize = foeList.size()-1;
            int weaponSize = weapons.size()-1;
            for (int i = currentStory.getNumStages() - 1; i > 0; i--) {
                ArrayList<Card> temp = new ArrayList<Card>();
                temp.add(foeList.get(foeSize));
                foeSize--;
                temp.add(weapons.get(weaponSize));
                weaponSize--;
                //check for increasing BPs
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

        ArrayList<AdventureCard> allyList = new ArrayList<AdventureCard>();
        ArrayList<AdventureCard> weaponList = new ArrayList<AdventureCard>();

        //C1
        for (int i = 0; i < ai.hand.size(); i++) {
            if (allPos.get(i).type == Card.Types.ALLY) {
                allyList.add(ai.hand.get(i));
            }
            if (allPos.get(i).type == Card.Types.WEAPON) {
                weaponList.add(ai.hand.get(i));
            }
        }

        if((allyList.size() + weaponList.size()) >= 2 * board.getCurrentQuestStages()) {
            //C2

            int foeCounter = 0;

            for(int i = 0; i < ai.hand.size(); i++){
                if (ai.hand.get(i).type == Card.Types.FOE){
                    if(ai.hand.get(i).getBattlePoints() < 20){
                        foeCounter++;
                        if(foeCounter == 2){
                            return true;
                        }
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

        int numCardsPlayed = 0;
        List<AdventureCard> playableCards = allPossibleCards(board,ai);
        List<Card> questCards = new List<Card>();

        if(board.currentQuestIndex + 1== board.getCurrentQuestStages()){
            questCards = allPossibleCards(board,ai);
        }else{
            for(AdventureCard c : playableCards){
                if((c.type == Card.Types.ALLY) || (c.type == Card.Types.AMOUR)){
                    questCards.add(c);
                    numCardsPlayed++;
                    if(numCardsPlayed == 2){
                        return questCards;
                    }
                }
            }
            for(AdventureCard c : playableCards){
                if(c.type == Card.Types.WEAPON){
                    questCards.add(c);
                    numCardsPlayed++;
                    if(numCardsPlayed == 2){
                        return questCards;
                    }
                }
            }
        }
        return questCards;
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
                    if (ai.hand.get(i).getBattlePoints() < 20) {
                        aiBids.add(ai.hand.get(i));
                    }
                }
            }
            isFirstRound = false;
            return aiBids;
        }

        return new ArrayList<>(); //return null
    }

    /*
     * ??? Discard Functionality I guess
     * Return Type : List<Card> to discard or put int play
     */
    public List<Card> discardAfterWinningTest(GameBoard board, AbstractAI ai){

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
                    if (ai.hand.get(i).getBattlePoints() < 20) {
                        aiBids.add(ai.hand.get(i));
                    }
                }
            }
            isFirstRound = false;
            return aiBids;
        }

        return new ArrayList<>(); // return null
    }

}
