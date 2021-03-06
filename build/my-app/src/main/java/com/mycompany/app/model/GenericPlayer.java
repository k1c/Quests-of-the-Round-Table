package com.mycompany.app.model;


import java.util.*;

public class GenericPlayer extends AbstractPlayer{

	public List<Card> hand;
	public List<Card> toBePlayed;
	public List<Card> inPlay;

	public int totalBattlePoints;

	public GenericPlayer(int id, String name, String shieldImage){
		super(name, shieldImage,id);
		//this.id = id;

		this.hand = new ArrayList<Card>();
		this.toBePlayed = new ArrayList<Card>();
		this.inPlay = new ArrayList<Card>();
	}

}
