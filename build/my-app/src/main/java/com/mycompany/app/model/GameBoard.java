package com.mycompany.app.model;

import com.mycompany.app.model.GameBoard;

public class GameBoard{
	public static GameBoard board = null;

	//protected 

	private GameBoard(){}

	public static GameBoard getInstance(){
		if(GameBoard.board == null)
			GameBoard.board = new GameBoard();
		return GameBoard.board;
	}

}
