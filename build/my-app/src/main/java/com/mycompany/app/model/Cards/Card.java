package com.mycompany.app.model.Cards;



public class Card implements Comparable<Card>{
	public static enum Types {FOE,ALLY,WEAPON,AMOUR,TEST, EVENT, QUEST, TOURNAMENT, RANK};
	public final Types type;
	public final int id;
	public final String res;
	public final String name;

	public Card(int id,String res,String name, Types type){  //change back to protected once Rank cards are added
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

	@Override
	public int compareTo(Card card) {

		//System.out.println("Hello" + card.type);

		if(this.type.ordinal() - card.type.ordinal() != 0){
			return this.type.ordinal() - card.type.ordinal();
		}
		else{
			return this.id - card.id;
		}
	}

	public String toString() {
		return this.name;
	}
}
