package com.mycompany.app.model;

import com.mycompany.app.model.DefaultBehaviour;

public class DefaultBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    public DefaultBehaviour(int BP, int bids) {
	this(BP,bids,false);
    }
    public DefaultBehaviour(int BP, int bids,boolean freeBid) {
	super();

        this.BP = BP;
        this.bids = bids;
	this.freeBid = freeBid;
    }


    public int getBP(GameBoard board){
        return this.BP;
    }

    public int getBids(GameBoard board){
        return this.bids;
    }

    public  Boolean isFreeBid(GameBoard board){
        return this.freeBid;
    }



}
