package com.mycompany.app.model;

import java.util.*;

public class SpecifiedBehaviour extends DefaultBehaviour{

    protected int specifiedBP;
    protected int specifiedBids;
    protected List<Integer> specifiers;

    /*public SpecifiedBehaviour(int defaultBP, int defaultBids, int specifiedBP, int specifiedBids) {
        this(defaultBP, defaultBids, specifiedBP, specifiedBids,false);
    }*/
    public SpecifiedBehaviour(int defaultBP, int defaultBids,int specifiedBP, int specifiedBids, boolean freeBid, List<Integer> specifiers) {
	
	super(defaultBP,defaultBids,freeBid);

        this.BP = BP;
        this.bids = bids;
	    this.freeBid = false;
	    this.specifiers = null;

    }

    public int getBP(GameBoard board){


        return this.BP;
    }

    public int getBids(GameBoard board){


        return this.bids;
    }

    public Boolean isFreeBid(GameBoard board){
	return this.freeBid;
    }

}
