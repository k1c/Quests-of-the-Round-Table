package com.mycompany.app.model;

public abstract class Card{
	public static enum Types {FOE,ALLY,WEAPON,AMOUR,TEST, EVENT, QUEST, TOURNAMENT};
	public final Types type;
	public final int id;
	public final String res;
	public final String name;

	public Card(int id,String res,String name, Types type){
		this.name = name;
		this.res = res;
		this.id = id;
		this.type = type;
	}
}
