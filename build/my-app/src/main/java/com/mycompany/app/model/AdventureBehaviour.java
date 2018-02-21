package com.mycompany.app.model;

public abstract class AdventureBehaviour {

    protected Boolean freeBid;

    public AdventureBehaviour(){
   	this.freeBid = false; 
    };

    public abstract int getBP(GameBoard board);

    public abstract int getBids(GameBoard board);

    public abstract Boolean isFreeBid(GameBoard board);

}

