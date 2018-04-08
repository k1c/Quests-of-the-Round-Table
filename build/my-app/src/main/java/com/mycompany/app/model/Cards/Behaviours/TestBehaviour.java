package com.mycompany.app.model.Cards.Behaviours;

import com.mycompany.app.model.Board.GameBoard;

import java.util.*;

public class TestBehaviour extends SpecifiedBehaviour{


    public TestBehaviour(int defaultBids, int specifiedBids, List<Integer> specifiers) {

        super(0, defaultBids, 0, specifiedBids, false, false, specifiers);
    }

    public TestBehaviour(int defaultBids) {

        this(defaultBids, defaultBids, new ArrayList<Integer>());


    }


    public int getBids(GameBoard board){

        int bids = super.getBids(board);

        if(board.players.size() <= 2){
            return Math.max(bids, 3);
        }
        return Math.max(bids, 1);

    }

}