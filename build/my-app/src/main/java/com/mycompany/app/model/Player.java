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

	public Player(String name, String shieldImage){
		super(name, shieldImage);

		this.hand = new ArrayList<AdventureCard>();
		this.toBePlayed = new ArrayList<AdventureCard>();
		this.inPlay = new ArrayList<AdventureCard>();
	}

	public GenericPlayer genericPlayer(GameBoard board){
		GenericPlayer temp = new GenericPlayer(this.id, this.name, this.shieldImage);

		temp.rank = new Rank(this.rank);
		temp.totalBattlePoints = getTotalBP(board);

		temp.hand.addAll(this.hand);
		temp.toBePlayed.addAll(this.toBePlayed);
		temp.inPlay.addAll(this.inPlay);

		Collections.sort(temp.hand);
		Collections.sort(temp.inPlay);

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
