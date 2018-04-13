package com.mycompany.app.model;

public abstract class AbstractPlayer{

	private static int idCount = 0;
	public static enum Type  {Player,AI};

	public final int id;
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

	protected AbstractPlayer(String name, String shieldImage, int id){
		this.name = name;
		this.shieldImage = shieldImage;
		this.id = id;
		this.rank = new Rank();

	}

	public int id(){
		return this.id;
	}
	
	@Override
	public String toString(){
		return String.format("id=%d,name=%s,type=%s",id,name,type.toString());
	}

}
