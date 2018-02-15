package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.Player;
import com.mycompany.app.model.GenericPlayer;
import java.util.*;

public class Player extends AbstractPlayer{

	public List<AdventureCard> hand;
	public List<AdventureCard> toBePlayed;
	public List<AdventureCard> inPlay;
	//public SpecifiedRank rank;

	public Player(){
		super();

		this.hand = new ArrayList<AdventureCard>();
		this.toBePlayed = new ArrayList<AdventureCard>();
		this.inPlay = new ArrayList<AdventureCard>();
	}

	public GenericPlayer genericPlayer(){
		GenericPlayer temp = new GenericPlayer(this.id);	
		for(Card card:hand){
			temp.hand.add(card.instance());	
		}
		for(Card card:toBePlayed){
			temp.toBePlayed.add(card.instance());	
		}
		for(Card card:inPlay){
			temp.inPlay.add(card.instance());	
		}
		return temp;

	}

}
