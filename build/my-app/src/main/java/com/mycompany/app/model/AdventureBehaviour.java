package com.mycompany.app.model;

public abstract class AdventureBehaviour {

    protected Boolean freeBid;

    public AdventureBehaviour(){
   	this.freeBid = false; 
    };

    public abstract int getBP();

    public abstract int getBids();

    public abstract Boolean isFreeBid();

}

