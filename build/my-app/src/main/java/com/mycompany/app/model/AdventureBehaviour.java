package com.mycompany.app.model;

import com.mycompany.app.model.*;
import java.util.ArrayList;

public abstract class AdventureBehaviour {


    public AdventureBehaviour(){}

    public abstract int getBP();

    public abstract int getBids();

}


public class DefaultBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    defaultBehaviour(int BP, int bids) {

        this.BP = BP;
        this.bids = bids;

    }

}

public class SpecifiedBehaviour extends AdventureBehaviour {

    protected int BP;
    protected int bids;

    protected int specifiedBP;
    protected int specifiedBids;
    ArrayList<int> specifiers;


    specifiedBehaviour(int specifiedBP, int specifiedBids, ) {

        this.BP = BP;
        this.bids = bids;

    }

}



       // minBids(int minBid) {
       //     }

        //mordredBehaviour(int BP, ) {}