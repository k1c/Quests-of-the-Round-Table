package com.mycompany.app.model;

import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractStrategyBehaviour {

    /*
     * Description : Checks if the AI should participate in tournament
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */
    abstract public boolean doIParticipateInTournament(GameBoard board, AbstractAI ai);


    /*
     * Description : Returns a list of cards to play in that tournament round
     * Return Type : List<Card>
     */
    abstract public List<Card> playInTournament(GameBoard board, AbstractAI ai);

    /* Description : Returns whether AI will sponsor a quest or not
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */
    abstract public boolean doISponsorAQuest(GameBoard board, AbstractAI ai);

    /*
     * Description : Returns a setup Quest
     * Return Type : TwoDimensionalArrayList<Card>
     */
    abstract public TwoDimensionalArrayList<Card> sponsorQuest(GameBoard board, AbstractAI ai);


    /* Description : Returns whether AI will participate in quest or not
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */
    abstract public boolean doIParticipateInQuest(GameBoard board, AbstractAI ai);

    /*
     * Description : Returns a list of cards to play in that Quest round
     * Return Type : List<Card>
     */
    abstract public List<Card> playQuest(GameBoard board, AbstractAI ai);

    /*
     * Description : Returns a list of cards to play in that test round
     * Return Type : List<Card>
     */
    abstract public List<Card> nextBid(GameBoard board, AbstractAI ai);

    /*
     * ??? Discard Functionality I guess
     * Return Type : List<Card> to discard or put int play
     */
    abstract public List<Card> discardAfterWinningTest(GameBoard board, AbstractAI ai);

    /*
    HELPERS
     */

    protected List<AdventureCard> allPossibleCards(GameBoard board, AbstractAI ai){

        Set<AdventureCard> set = new TreeSet();
        List<AdventureCard> temp = new ArrayList();

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

    protected List<Card> toCards(List<AdventureCard> adventureCards){
        List<Card> cards = new ArrayList<Card>();
        cards.addAll(adventureCards);
        return cards;
    }


}
