package com.mycompany.app.controller;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.view.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

        DeckView decks = new DeckView();
		ConsoleView consoleView = new ConsoleView();
		BorderPane root = new BorderPane();

		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(consoleView);
		stackPane.setAlignment(Pos.CENTER_RIGHT);
		stackPane.setPadding(new Insets(MARGIN_OUTER*2));
		root.setTop(stackPane);

        root.setLeft(decks);

		GridPane currentPlayer = new GridPane();

		currentPlayer.setPadding(new Insets(MARGIN_OUTER));
		currentPlayer.setAlignment(Pos.BOTTOM_RIGHT);

		currentPlayer.add(currentPlayerView, 0, 0);

		root.setBottom(currentPlayer);

		GridPane waitingPlayers = new GridPane();

		waitingPlayers.setPadding(new Insets(0, MARGIN_OUTER, MARGIN_OUTER*10, MARGIN_OUTER));
		waitingPlayers.setAlignment(Pos.BOTTOM_RIGHT);

		waitingPlayers.add(waitingPlayersView, 0, 0);

		root.setRight(waitingPlayers);

		Scene gameScene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());

		primaryStage.setScene(gameScene);
	}
}


