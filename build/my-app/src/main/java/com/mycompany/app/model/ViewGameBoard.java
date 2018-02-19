package com.mycompany.app.model;

import java.util.*;
import com.mycompany.app.model.GenericPlayer;

public class ViewGameBoard extends AbstractGameBoard{

	public List<GenericPlayer>	players;

	public int numCardsAdventure;
	public int numCardsAdventureDiscard;

	public int numCardsStory;
	public int numCardsStoryDiscard;

	public int numPlayers;


	public ViewGameBoard(){
		this.players = new ArrayList<GenericPlayer>();
	}
}
