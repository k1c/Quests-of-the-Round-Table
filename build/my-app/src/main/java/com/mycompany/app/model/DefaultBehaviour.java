package com.mycompany.app.model;

public class DefaultBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    public DefaultBehaviour(int BP, int bids) {
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

    public  Boolean isFreeBid(){
        return this.freeBid;
    }



}
