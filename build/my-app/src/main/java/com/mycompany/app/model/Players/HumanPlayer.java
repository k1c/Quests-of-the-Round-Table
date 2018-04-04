package com.mycompany.app.model;

public class HumanPlayer extends Player{

	private static int idCount = 0;
	protected int id;
	public Rank rank;
	public String name;
	public String shieldImage;

	public HumanPlayer(String name, String shieldImage){
		super(name,shieldImage);
		this.type = AbstractPlayer.Type.Player;
	}
}
