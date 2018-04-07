package com.mycompany.app.model;

public abstract class AdventureBehaviour {

    protected Boolean defaultFreeBid;

    public AdventureBehaviour(){
   	this.defaultFreeBid = false;
    };

    public abstract int getBP(GameBoard board);

    public abstract int getBids(GameBoard board);

    public abstract Boolean isFreeBid(GameBoard board);

}
