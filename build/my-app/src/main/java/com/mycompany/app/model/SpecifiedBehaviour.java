package com.mycompany.app.model;

import java.util.*;

public class SpecifiedBehaviour extends DefaultBehaviour{

    protected int specifiedBP;
    protected int specifiedBids;
    protected List<Integer> specifiers;


    public SpecifiedBehaviour(int defaultBP, int defaultBids,int specifiedBP, int specifiedBids,boolean freeBid) {
	
	super(defaultBP,defaultBids,freeBid);

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
