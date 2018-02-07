package com.mycompany.app.model;

import com.mycompany.app.model.Player;
import java.util.*;

public class Player{

	public ArrayList<Card> hand;
	public ArrayList<Card> toBePlayed;
	public ArrayList<Card> inPlay;

	public Player(){
		this.hand = new ArrayList<Card>();
		this.toBePlayed = new ArrayList<Card>();
		this.inPlay = new ArrayList<Card>();
	}

}
