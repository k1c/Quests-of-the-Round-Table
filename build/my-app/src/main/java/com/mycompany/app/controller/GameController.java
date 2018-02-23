/**
 * Authors: Akhil Dalal & Carolyne Pelletier
 * Team 4
 *
 * GameController.java - Controller for GameView
 *                     - Transitions from start screen to game screen
 */

package com.mycompany.app.controller;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.view.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * TODO:
 * 1) Consistent margins and spacing
 * 2) Consistent fonts and sizes
 * 3) This can't be a God class - Strip down to minimal
 */
public class GameController{

	private static final int MARGIN_OUTER = 10;
	private final int WIDTH = 1920;
	private final int HEIGHT = 1080;

	// All components required for first screen
	private CurrentPlayerView currentPlayerView;
	private WaitingPlayersView waitingPlayersView;
	private DeckView deckView;
	private ConsoleView consoleView;
	private QuestsView questsView;

	private final GameModel gameModel;
	private final GameView gameView;

	public GameController(GameModel gameModel, GameView gameView){
		this.gameView = gameView;
		this.gameModel = gameModel;
	}

	public void startGame(Stage primaryStage, int numHumans, int numAI, String[] humanNames) {
		gameModel.initGame(numHumans, numAI, humanNames);

		currentPlayerView = new CurrentPlayerView(gameModel);
		waitingPlayersView = new WaitingPlayersView(gameModel);
        deckView = new DeckView();
        consoleView = new ConsoleView();
        questsView = new QuestsView();

        AnchorPane root = new AnchorPane();
        root.setPadding(new Insets(MARGIN_OUTER*2));

        HBox cd = new HBox(20);

        cd.setAlignment(Pos.CENTER);
        cd.getChildren().addAll(consoleView, deckView);


        root.getChildren().addAll(questsView, cd, waitingPlayersView, currentPlayerView);


        AnchorPane.setLeftAnchor(questsView, 0.0);
        AnchorPane.setTopAnchor(questsView, 0.0);

        AnchorPane.setRightAnchor(cd, 0.0);
        AnchorPane.setTopAnchor(cd, 0.0);
/*
        AnchorPane.setLeftAnchor(deckView, 0.0);
        AnchorPane.setTopAnchor(deckView, primaryStage.getHeight()/2);

        AnchorPane.setRightAnchor(consoleView, 0.0);
        AnchorPane.setTopAnchor(consoleView, 0.0);*/

        AnchorPane.setRightAnchor(waitingPlayersView, 0.0);
        AnchorPane.setTopAnchor(waitingPlayersView, primaryStage.getHeight()/4.5);

        AnchorPane.setRightAnchor(currentPlayerView, 0.0);
        AnchorPane.setBottomAnchor(currentPlayerView, 0.0);

       // currentPlayerView.setGridLinesVisible(true);

        // ---------- OLD - BORDERPANE
       /* DeckView decks = new DeckView();
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

		GridPane quests = new GridPane();

		quests.setPadding(new Insets(MARGIN_OUTER));
		quests.setAlignment(Pos.TOP_LEFT);

		QuestsView q = new QuestsView();
		quests.add(q, 0, 0);

		HBox top = new HBox(10);

		Region r = new Region();
		HBox.setHgrow(r, Priority.ALWAYS);

		top.getChildren().addAll(quests, r, consoleView);

		root.setTop(top);*/

		Scene gameScene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());

		primaryStage.setScene(gameScene);
	}
}


