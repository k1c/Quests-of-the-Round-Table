package com.mycompany.app.controller;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.view.CurrentPlayerView;
import com.mycompany.app.view.GameView;
import com.mycompany.app.view.WaitingPlayersView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameController{

	private static final int MARGIN_OUTER = 5;
	private CurrentPlayerView currentPlayerView;
	private WaitingPlayersView waitingPlayersView;

	private final GameModel model;
	private final GameView view;

	public GameController(GameModel model, GameView view){
		this.view = view;
		this.model = model;
	}

	public void startGame(Button btn) {
        /*
        if (model.startGame(userOptions) == true) this.changeScene
        else view.showError()
         */
		Stage primaryStage = (Stage) btn.getScene().getWindow();

		currentPlayerView = new CurrentPlayerView(model);
		waitingPlayersView = new WaitingPlayersView(model);

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

		Scene gameScene = new Scene(root, btn.getScene().getWidth(), btn.getScene().getHeight());

		primaryStage.setScene(gameScene);
	}
}


