package com.mycompany.app.model;

import java.util.List;

public abstract class AbstractStrategyBehaviour {

    /*
     * Description : Checks if the AI should participate in tournament
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */
    abstract public boolean doIParticipateInTournament(GameBoard board,AbstractAI ai);


    /*
     * Description : Returns a list of cards to play in that tournament round
     * Return Type : List<Card>
     */
    abstract public List<Card> playInTournament(GameBoard board,AbstractAI ai);

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

}
