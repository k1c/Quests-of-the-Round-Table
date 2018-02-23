package com.mycompany.app.model;

import java.util.*;

public class SpecifiedBehaviour extends DefaultBehaviour{

    protected int specifiedBP;
    protected int specifiedBids;
	protected boolean specifiedFreeBid;

    protected List<Integer> specifiers;

    /*public SpecifiedBehaviour(int defaultBP, int defaultBids, int specifiedBP, int specifiedBids) {
        this(defaultBP, defaultBids, specifiedBP, specifiedBids,false);
    }*/
    public SpecifiedBehaviour(int defaultBP, int defaultBids,int specifiedBP, int specifiedBids, boolean defaultFreeBid, boolean specifiedFreeBid, List<Integer> specifiers) {
	
	super(defaultBP,defaultBids,defaultFreeBid);

        this.specifiedBP = specifiedBP;
        this.specifiedBids = specifiedBids;
        this.specifiedFreeBid = specifiedFreeBid;
	    this.specifiers = specifiers;

    }

    public int getBP(GameBoard board){

		if (isSpecified(board)){
			return this.specifiedBP;
		}
        return this.BP;
    }

    public int getBids(GameBoard board){

	    if (isSpecified(board)) {
			return this.specifiedBids;
		}
		return this.bids;
    }

    public Boolean isFreeBid(GameBoard board){

		return this.defaultFreeBid || (isSpecified(board) && this.specifiedFreeBid) ;
    }

    protected boolean isSpecified(GameBoard board){
	    for (Integer specID : this.specifiers){
		    if (specID == board.currentStory.id) {
			    return true;
		    }
	    }

	    for (Player p : board.players) {
		    for (Card c : p.inPlay) {
			    for (Integer specID : specifiers) {
				    if (specID == c.id) {
					    return true;
				    }
			    }
		    }
	    }
	    return false;
    }

}
