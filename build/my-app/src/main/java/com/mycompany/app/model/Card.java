package com.mycompany.app.model;


import com.mycompany.app.model.Card;

public class Card {
	public static enum Types {FOE,ALLY,WEAPON,AMOUR,TEST, EVENT, QUEST, TOURNAMENT};
	public final Types type;
	public final int id;
	public final String res;
	public final String name;

	protected Card(int id,String res,String name, Types type){
		this.name = name;
		this.res = res;
		this.id = id;
		this.type = type;
	}

	private Card(Card original){
		this(original.id, original.res, original.name, original.type);
	}

	public Card instance(){
		return new Card(this);
	}
}
