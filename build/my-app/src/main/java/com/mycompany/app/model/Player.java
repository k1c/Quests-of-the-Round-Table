package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.Player;
import java.util.*;

public class Player{

	private static int idCount = 0;
	protected int id;

	public ArrayList<AdventureCard> hand;
	public ArrayList<AdventureCard> toBePlayed;
	public ArrayList<AdventureCard> inPlay;

	public Player(){
		this.hand = new ArrayList<AdventureCard>();
		this.toBePlayed = new ArrayList<AdventureCard>();
		this.inPlay = new ArrayList<AdventureCard>();

		this.id = idCount;
		idCount++;
	}

	public int id(){
		return this.id;
	}

}
