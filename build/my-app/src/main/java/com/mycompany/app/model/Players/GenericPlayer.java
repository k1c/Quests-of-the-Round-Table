package com.mycompany.app.model.Players;


import com.mycompany.app.model.Cards.Card;

import java.util.*;

public class GenericPlayer extends AbstractPlayer{

	public List<Card> hand;
	public List<Card> toBePlayed;
	public List<Card> inPlay;

	public int totalBattlePoints;

	public GenericPlayer(int id, String name, String shieldImage){
		super(name, shieldImage);
		this.id = id;

		this.hand = new ArrayList<Card>();
		this.toBePlayed = new ArrayList<Card>();
		this.inPlay = new ArrayList<Card>();
	}

}
