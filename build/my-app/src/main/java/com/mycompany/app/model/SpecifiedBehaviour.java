package com.mycompany.app.model;

import java.util.*;

public class SpecifiedBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    protected ArrayList<Integer> specifiers;


    public SpecifiedBehaviour(int specifiedBP, int specifiedBids /* , array of specifiers */) {

        this.BP = specifiedBP;
        this.bids = specifiedBids;

    }

    public int getBP(){
        return this.BP;
    }

    public int getBids(){
        return this.bids;
    }

}