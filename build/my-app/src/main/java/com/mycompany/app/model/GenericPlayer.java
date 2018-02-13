package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.Player;
import java.util.*;

public class GenericPlayer extends AbstractPlayer{

	public ArrayList<Card> hand;
	public ArrayList<Card> toBePlayed;
	public ArrayList<Card> inPlay;
	//public GenericRank rank;

	public GenericPlayer(int id){
		this.id = id;

		this.hand = new ArrayList<Card>();
		this.toBePlayed = new ArrayList<Card>();
		this.inPlay = new ArrayList<Card>();
	}

}
