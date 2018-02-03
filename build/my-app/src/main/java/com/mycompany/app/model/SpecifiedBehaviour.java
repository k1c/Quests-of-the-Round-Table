package com.mycompany.app.model;

import java.util.*;

public class SpecifiedBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    protected int specifiedBP;
    protected int specifiedBids;
    protected ArrayList<Integer> specifiers;


    public SpecifiedBehaviour(int specifiedBP, int specifiedBids) {
	
	super();

        this.BP = BP;
        this.bids = bids;
	this.freeBid = false;

    }

    public int getBP(){
        return this.BP;
    }

    public int getBids(){
        return this.bids;
    }

    public Boolean isFreeBid(){
	return this.freeBid;
    }

}
