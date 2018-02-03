package com.mycompany.app.model;

public abstract class Card{
	public static enum Types {FOE,ALLY,WEAPON,AMOUR,TEST};
	public final Types type;
	public final int id;
	public final String res;
	public final String name;

	public Card(int id,String res,String name){
		this.name = name;
		this.res = res;
		this.id = id;
	}
}
