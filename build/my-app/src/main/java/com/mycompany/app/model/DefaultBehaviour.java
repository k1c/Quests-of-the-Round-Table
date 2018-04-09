package com.mycompany.app.model;

public class DefaultBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    public DefaultBehaviour(int BP, int bids) {
	this(BP,bids,false);
    }
    public DefaultBehaviour(int BP, int bids,boolean defaultFreeBid) {
	super();

        this.BP = BP;
        this.bids = bids;
	    this.defaultFreeBid = defaultFreeBid;
    }


    public int getBP(GameBoard board){
        return this.BP;
    }

    public int getBids(GameBoard board){
        return this.bids;
    }

    public  Boolean isFreeBid(GameBoard board){
        return this.defaultFreeBid;
    }



}
