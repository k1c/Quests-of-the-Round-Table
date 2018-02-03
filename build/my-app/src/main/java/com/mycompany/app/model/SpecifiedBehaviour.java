package com.mycompany.app.model;

import java.util.*;

public class SpecifiedBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    protected int specifiedBP;
    protected int specifiedBids;
    protected ArrayList<Integer> specifiers;


    public SpecifiedBehaviour(int specifiedBP, int specifiedBids) {

        this.BP = BP;
        this.bids = bids;

    }

    public int getBP(){
        return this.BP;
    }

    public int getBids(){
        return this.bids;
    }

}