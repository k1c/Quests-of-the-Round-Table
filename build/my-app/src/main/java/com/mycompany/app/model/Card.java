package com.mycompany.app.model;


public abstract class Card{
	public static enum Types {FOE,ALLY,WEAPON,AMOUR,TEST};
	public final type;
	public final int id;
	public final String res;

	public Card(int id,String res){
		this.res = res;
		this.id = id;
	}
}
