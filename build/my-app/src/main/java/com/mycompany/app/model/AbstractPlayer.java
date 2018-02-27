package com.mycompany.app.model;

public abstract class AbstractPlayer{

	private static int idCount = 0;
	public static enum Type  {Player,AI};

	protected int id;
	public Rank rank;
	public String name;
	public String shieldImage;
	public Type type;

	public AbstractPlayer(String name, String shieldImage){
	    this.name = name;
	    this.shieldImage = shieldImage;
		this.id = idCount;
		this.rank = new Rank();
		idCount++;
	}


	public int id(){
		return this.id;
	}

}
