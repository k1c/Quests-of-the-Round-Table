package com.mycompany.app.controller;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.view.CurrentPlayerView;
import com.mycompany.app.view.GameView;
import com.mycompany.app.view.WaitingPlayersView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController{

	private static final int MARGIN_OUTER = 5;
	private CurrentPlayerView currentPlayerView;
	private WaitingPlayersView waitingPlayersView;

	private final GameModel gameModel;
	private final GameView gameView;

	public GameController(GameModel gameModel, GameView gameView){
		this.gameView = gameView;
		this.gameModel = gameModel;
	}

	public void startGame(Stage primaryStage, int numHumans, int numAI) {
		gameModel.initGame(numHumans, numAI);

		currentPlayerView = new CurrentPlayerView(gameModel);
		waitingPlayersView = new WaitingPlayersView(gameModel);

		BorderPane root = new BorderPane();

		GridPane currentPlayer = new GridPane();

		currentPlayer.setPadding(new Insets(MARGIN_OUTER));
		currentPlayer.setAlignment(Pos.BOTTOM_RIGHT);

		currentPlayer.add(currentPlayerView, 0, 0);

		root.setBottom(currentPlayer);

		GridPane waitingPlayers = new GridPane();

		waitingPlayers.setPadding(new Insets(MARGIN_OUTER));
		waitingPlayers.setAlignment(Pos.CENTER_RIGHT);

		waitingPlayers.add(waitingPlayersView, 0, 0);

		root.setRight(waitingPlayers);

		Scene gameScene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());

		primaryStage.setScene(gameScene);
	}
}


