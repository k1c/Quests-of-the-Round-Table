package com.mycompany.app.model;

public abstract class AdventureBehaviour {

    protected boolean freeBid;

    public AdventureBehaviour(){
   	this.freeBid = false; 
    };

    public abstract int getBP();

    public abstract int getBids();

    public abstract boolean isFreeBid();

}

