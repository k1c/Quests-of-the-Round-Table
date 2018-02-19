package com.mycompany.app.model;

public abstract class AbstractPlayer{

	private static int idCount = 0;
	protected int id;


	public AbstractPlayer(){
		this.id = idCount;
		idCount++;
	}


	public int id(){
		return this.id;
	}

}
