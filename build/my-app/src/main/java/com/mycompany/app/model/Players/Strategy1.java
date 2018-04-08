package com.mycompany.app.model.Players;

import com.mycompany.app.model.Board.GameBoard;
import com.mycompany.app.model.Board.ViewGameBoard;
import com.mycompany.app.model.Cards.Card;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;

import java.util.ArrayList;
import java.util.List;

public class Strategy1 extends AbstractStrategyBehaviour{
    /*
     * Description : Checks if the AI should participate in tournament
     * Return Type : TRUE -- I want to participate
     * 		 FALSE - I do not participate
     */
    public boolean doIParticipateInTournament(GameBoard board, AbstractAI ai){

        List<Player> players = board.getParticipantPlayers();
        int currentRank = 0;
        boolean win = false;

        ViewGameBoard b = board.getViewCopy();

        for (Player p : players ) {
            if (p.rank.getShields() + (board.getCurrentQuestStages() - players.size()) >= p.rank.getMaxShields()) {
                win = true;
            }
        }
        if (win == true){
            return true;
        }

        return false;
    }


    /*
     * Description : Returns a list of cards to play in that tournament round
     * Return Type : List<Card>
     */
    public List<Card> playInTournament(GameBoard board, AbstractAI ai){
        return new ArrayList();
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
