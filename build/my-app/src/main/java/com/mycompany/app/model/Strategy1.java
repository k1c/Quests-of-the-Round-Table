package com.mycompany.app.model;

import java.util.*;
import com.mycompany.app.GameLogger;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

public class Strategy1 extends AbstractStrategyBehaviour{
    /*
     * Description : Checks if the AI should participate in tournament
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */

    private static boolean isFirstRound = true;
    GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

    public boolean doIParticipateInTournament(GameBoard board, AbstractAI ai){

        log.playerAction(ai, "Deciding whether to participate in tournament");

        List<Player> players = board.players;
        for (Player p : players ) {
            log.count("shields + stages + num players must be >= to ", p.rank.getShields() + (board.currentStory.getNumStages() + players.size()));
            log.count("rank max shields, ", p.rank.getMaxShields());
            if (p.rank.getShields() + (board.currentStory.getNumStages() + players.size()) >= p.rank.getMaxShields()) {
                log.playerAction(p,"can participate in tournament");
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

        log.playerAction(ai, "Getting cards to play in tournament");

        List<Player> players = board.getParticipantPlayers();
        for (Player p : players ) {
            if (p.rank.getShields() + (board.getCurrentQuestStages() - players.size()) >= p.rank.getMaxShields()) {
                log.playerCards(ai, toCards(allPossibleCards(board,ai)), "Adventure deck");
                return toCards(allPossibleCards(board,ai));
            }
        }

        List<Card> aiTournament = new ArrayList<Card>();
        List<Card> tempWeapons = new ArrayList<Card>();
        List<AdventureCard> tempAIHand = ai.hand;

        for(int i = 0; i < tempAIHand.size(); i++){
            if (tempAIHand.get(i).type == Card.Types.WEAPON){
                tempWeapons.add(tempAIHand.get(i));
            }
        }

        Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>();
        for (int i = 0; i < tempWeapons.size(); i++) {
            if (cardDuplicates.get(tempWeapons.get(i)) != null) {
                cardDuplicates.put(tempWeapons.get(i), cardDuplicates.get(tempWeapons.get(i)) + 1);
            } else {
                cardDuplicates.put(tempWeapons.get(i), 1);
            }
        }

        for (Map.Entry<Card, Integer> entry : cardDuplicates.entrySet()) {
            if (entry.getValue() > 1) {
                aiTournament.add(entry.getKey());
            }
        }

        log.playerTournament(ai,aiTournament);
        return aiTournament;
    }

    /* Description : Returns whether AI will sponsor a quest or not
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */

    public boolean doISponsorAQuest(GameBoard board, AbstractAI ai){

        log.playerAction(ai, "Deciding whether to sponsor a quest");

        List<Player> players = board.getParticipantPlayers();

        for (Player p : players) {
            if (p.id != ai.id){
                log.count("shields + stages + num players must be >= to ", p.rank.getShields() + (board.currentStory.getNumStages() + players.size()));
                log.count("rank max shields, ", p.rank.getMaxShields());
                if (p.rank.getShields() + (board.getCurrentQuestStages() + players.size()) >= p.rank.getMaxShields()) {
                    log.playerAction(p,"can sponsor the quest");
                    return false;
                }
            }
        }

        if(!canISetup1(board,ai)){
            return false;
        }

        if (board.playerCanSponsor(ai.id)) {
            return board.playerCanSponsor(ai.id);
        }

        return false;
    }


    private boolean canISetup1(GameBoard board, AbstractAI ai){

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
        if(questStageBP < 50) {
            for (int j = weaponList.size() - 1; j > 0; j--) {
                questStageBP += weaponList.get(j).getBattlePoints(board);
                if (questStageBP >= 50) {
                    log.count("can setup quest for AI1", questStageBP);
                    return true;
                }
            }
        }
        log.count("cannot setup quest for AI1", questStageBP);
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
        ArrayList<Card> stageN = new ArrayList<Card>();
        int questStageBP = 0;

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

        //set up last stage to be at least 50
        questStageBP += foeList.get(foeList.size() - 1).getBattlePoints(board);
        stageN.add(foeList.get(foeList.size() - 1));
        foeList.remove(foeList.get(foeList.size() - 1));

        if(questStageBP < 50) {
            for (int j = uw2.size() - 1; j > 0; j--) {
                questStageBP += uw2.get(j).getBattlePoints(board);
                stageN.add(uw2.get(j));
                if (questStageBP >= 50) {
                    break;
                }
            }
        }

        log.count("AI Quest Size", aiQuest.size());
        log.count("Quest Stages - 1 (for indexing)",board.currentStory.getNumStages() -  1);

        for (Card c : stageN) {
            aiQuest.addToInnerArray(board.currentStory.getNumStages() - 1, c);
        }

        if(testList.size() > 0) {
            log.playerAction(ai, "Adding the test");

            List<Card> weapons = getWeapons(weaponList);
            int foeSize = foeList.size()-1;
            int weaponSize = weapons.size()-1;
            ArrayList<Card> testStage = new ArrayList<Card>();

            testStage.add(testList.get(0));

            aiQuest.addToInnerArray(board.currentStory.getNumStages() - 2, testList.get(0));

            for (int i = board.currentStory.getNumStages() - 3; i >= 0; i--) {
                aiQuest.addToInnerArray(i, foeList.get(foeSize));
                log.cardPlayed(ai,foeList.get(foeSize),"to sponsor quest");
                foeSize--;
                if (weaponSize >= 0) {
                    aiQuest.addToInnerArray(i, weapons.get(weaponSize));
                    weaponSize--;
                }
            }
        } else {
            List<Card> weapons = getWeapons(weaponList);
            int foeSize = foeList.size()-1;
            int weaponSize = weapons.size()-1;

            for (int i = board.currentStory.getNumStages() - 2; i >= 0; i--) {
                log.playerQuest(ai,foeList.get(foeSize));

                aiQuest.addToInnerArray(i, foeList.get(foeSize));
                foeSize--;
                if (weaponSize >= 0) {
                    aiQuest.addToInnerArray(i, weapons.get(weaponSize));
                    weaponSize--;
                }
            }
        }
        log.playerQuest(ai,aiQuest);
        return aiQuest;
    }

    private List<Card> getWeapons(ArrayList<AdventureCard> weaponList){
        List<Card> weapons = new ArrayList<Card>();
        Map<Card, Integer> cardDuplicates = new HashMap<Card, Integer>();

        for (int i = 0; i < weaponList.size(); i++) {
            if (cardDuplicates.get(weaponList.get(i)) != null) {
                cardDuplicates.put(weaponList.get(i), cardDuplicates.get(weaponList.get(i)) + 1);
            } else {
                cardDuplicates.put(weaponList.get(i), 1);
            }
        }

        for (Map.Entry<Card, Integer> entry : cardDuplicates.entrySet()) {
            if (entry.getValue() > 1) {
                weapons.add(entry.getKey());
            }
        }
        return weapons;
    }


    /* Description : Returns whether AI will participate in quest or not
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */
    public boolean doIParticipateInQuest(GameBoard board, AbstractAI ai){

        log.playerAction(ai, "Deciding whether to participate in quest");

        isFirstRound = true;

        List<AdventureCard> allyList = new ArrayList<AdventureCard>();
        List<AdventureCard> weaponList = new ArrayList<AdventureCard>();

        //C1
        for (int i = 0; i < ai.hand.size(); i++) {
            if (ai.hand.get(i).type == Card.Types.ALLY) {
                allyList.add(ai.hand.get(i));
            }
            if (ai.hand.get(i).type == Card.Types.WEAPON) {
                weaponList.add(ai.hand.get(i));
            }
        }

        if((allyList.size() + weaponList.size()) >= 2 * board.getCurrentQuestStages()) {
            log.playerAction(ai,"has at least 2 weapons/allies per stage");

            //C2
            int foeCounter = 0;

            for(int i = 0; i < ai.hand.size(); i++){
                if (ai.hand.get(i).type == Card.Types.FOE){
                    if(ai.hand.get(i).getBattlePoints(board) < 20){
                        foeCounter++;
                        if(foeCounter == 2){
                            log.playerAction(ai,"has at least 2 foes of < 20 points");
                            return true;
                        }
                    }
                }
            }
        }
        log.playerAction(ai, "does not have at least 2 weapons/allies per stage OR does not have at least 2 foes of < 20 points");
        log.playerAction(ai, "cannot participate in quest");
        return false;
    }

    /*
     * Description : Returns a list of cards to play in that Quest round
     * Return Type : List<Card>
     */
    public List<Card> playQuest(GameBoard board, AbstractAI ai){

        log.playerAction(ai, "Getting cards to play in quest");

        int numCardsPlayed = 0;
        List<AdventureCard> playableCards = allPossibleCards(board,ai);
        List<AdventureCard> questCards = new ArrayList<>();

        if(board.currentQuestIndex + 1== board.getCurrentQuestStages()){
            questCards = allPossibleCards(board,ai);
        }else{
            for(AdventureCard c : playableCards){
                if((c.type == Card.Types.ALLY) || (c.type == Card.Types.AMOUR)){
                    questCards.add(c);
                    numCardsPlayed++;
                    if(numCardsPlayed == 2){
                        log.playerQuest(ai,questCards);
                        return toCards(questCards);
                    }
                }
            }
            for(AdventureCard c : playableCards){
                if(c.type == Card.Types.WEAPON){
                    questCards.add(c);
                    numCardsPlayed++;
                    if(numCardsPlayed == 2){
                        log.playerQuest(ai,questCards);
                        return toCards(questCards);
                    }
                }
            }
        }
        log.playerQuest(ai,toCards(questCards));
        return toCards(questCards);
    }

    /*
     * Description : Returns a list of cards to play in that test round
     * Return Type : List<Card>
     */
    public List<Card> nextBid(GameBoard board, AbstractAI ai){

        log.playerAction(ai, "Getting cards to bid in test");

        List<Player> players = board.getParticipantPlayers();
        List<Card> aiBids = new ArrayList<Card>();
        int currentMax = 0;

        for(Player p: players){
            if (board.totalPlayerBids(p) > currentMax){
                currentMax = board.totalPlayerBids(p);
            }
        }

        if(isFirstRound) {

            for (int i = 0; i < ai.hand.size(); i++) {
                if (ai.hand.get(i).type == Card.Types.FOE) {
                    if (ai.hand.get(i).getBattlePoints(board) < 20) {
                        aiBids.add(ai.hand.get(i));
                    }
                }
            }
            isFirstRound = false;
            log.playerBid(ai,aiBids);
            return aiBids;
        }

        log.playerBid(ai,aiBids);
        log.playerAction(ai,"no bids");
        return aiBids;
    }

    /*
     * Discard Functionality
     * Return Type : List<Card> to discard or put int play
     */
    public List<Card> discardAfterWinningTest(GameBoard board, AbstractAI ai){

        log.playerAction(ai, "Getting cards to discard after winning test");

        return nextBid(board,ai);
    }
}