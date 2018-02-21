package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.Player;
import java.util.*;

public class GenericPlayer extends AbstractPlayer{

	public List<Card> hand;
	public List<Card> toBePlayed;
	public List<Card> inPlay;

	public int totalBattlePoints;

	public GenericPlayer(int id){
		this.id = id;

		this.hand = new ArrayList<Card>();
		this.toBePlayed = new ArrayList<Card>();
		this.inPlay = new ArrayList<Card>();
	}

}
