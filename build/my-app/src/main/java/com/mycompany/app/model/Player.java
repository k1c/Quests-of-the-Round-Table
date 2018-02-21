package com.mycompany.app.model;

import com.mycompany.app.model.AdventureCard;
import com.mycompany.app.model.Player;
import com.mycompany.app.model.GenericPlayer;
import com.mycompany.app.model.Rank;
import java.util.*;

public class Player extends AbstractPlayer{

	public List<AdventureCard> hand;
	public List<AdventureCard> toBePlayed;
	public List<AdventureCard> inPlay;

	public Player(){
		super();

		this.hand = new ArrayList<AdventureCard>();
		this.toBePlayed = new ArrayList<AdventureCard>();
		this.inPlay = new ArrayList<AdventureCard>();
	}

	public GenericPlayer genericPlayer(GameBoard board){
		GenericPlayer temp = new GenericPlayer(this.id);	

		temp.rank = new Rank(this.rank);
		temp.totalBattlePoints = getTotalBP(board);

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

	public int getInPlayBp(GameBoard board){
		int BP = 0;
		for(AdventureCard card : this.inPlay){
			BP += card.getBattlePoints(board);	
		}
		return BP;
	}

	public int getTotalBP(GameBoard board){
		return getInPlayBp(board) + rank.getBP();
	}

}
