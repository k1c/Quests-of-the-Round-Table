package com.mycompany.app.model;

public abstract class AbstractPlayer{

	private static int idCount = 0;
	protected int id;
	public Rank rank;


	public AbstractPlayer(){
		this.id = idCount;
		this.rank = new Rank();
		idCount++;
	}


	public int id(){
		return this.id;
	}

}
