/**
 * Authors: Akhil Dalal & Carolyne Pelletier
 * Team 4
 *
 * GameController.java - Controller for GameView
 *                     - Transitions from start screen to game screen
 */

package com.mycompany.app.controller;

import com.mycompany.app.model.*;
import com.mycompany.app.GameLogger;
import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.GameObserver;
import com.mycompany.app.model.GameStates;
import com.mycompany.app.view.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * TODO:
 * 1) Consistent margins and spacing
 * 2) Consistent fonts and sizes
 * 3) This can't be a God class - Strip down to minimal
 */
public class GameController implements GameObserver{

	private static final int MARGIN_OUTER = 10;
	private final int WIDTH = 1920;
	private final int HEIGHT = 1080;

	private AnchorPane root;
	// All components required for first screen
	private CurrentPlayerView currentPlayerView;
	private WaitingPlayersView waitingPlayersView;
	private DeckView deckView;
	private ConsoleView consoleView;
	private QuestsView questsView;

	private GenericPlayer current;

	private final GameModel gameModel;
	private final GameView gameView;

	public GameController(GameModel gameModel, GameView gameView){
		this.gameView = gameView;
		this.gameModel = gameModel;
		this.gameModel.registerObserver(this);
	}

	public void startGame(Stage primaryStage, int numHumans, int numAI, String[] humanNames) {

		gameModel.initGame(numHumans, numAI, humanNames);

		currentPlayerView = new CurrentPlayerView(gameModel);
		waitingPlayersView = new WaitingPlayersView(gameModel);
        deckView = new DeckView(gameModel);
        consoleView = new ConsoleView(gameModel, this);

        root = new AnchorPane();
        root.setPadding(new Insets(MARGIN_OUTER*2));

        HBox cd = new HBox(20);

        cd.setAlignment(Pos.CENTER);
        cd.getChildren().addAll(consoleView, deckView);


        root.getChildren().addAll(cd, waitingPlayersView, currentPlayerView);

        AnchorPane.setRightAnchor(cd, 0.0);
        AnchorPane.setTopAnchor(cd, 0.0);

        AnchorPane.setRightAnchor(waitingPlayersView, 0.0);
        AnchorPane.setTopAnchor(waitingPlayersView, primaryStage.getHeight()/4.5);

        AnchorPane.setRightAnchor(currentPlayerView, 0.0);
        AnchorPane.setBottomAnchor(currentPlayerView, 0.0);

       // currentPlayerView.setGridLinesVisible(true);

		Scene gameScene = new Scene(root, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight());

		primaryStage.setScene(gameScene);
	}

	public void setup(int stage, int row) {
		currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, null, null);
		if (stage <= gameModel.getNumberOfStages()) {
			questsView.setFocus(stage, row);
			if (row == 0) {
				// foe/test
				consoleView.display("Add a foe or a test to stage " + stage);

                Button play = new Button("Play Card");
                play.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    Card img = (Card) currentPlayerView.getFrontCard().getProperties().get("card");
                    questsView.setFoeTest(img, stage);
                    // add to player's tobeplayed and remove from hand
					// on successfull submit, cards removed from tobe
                    currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, null, null);
                });
                Card.Types[] types = {Card.Types.FOE, Card.Types.TEST};
                currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, play, types);

			}
			else {
                //weapons
                consoleView.display("Add weapon(s) to stage " + stage);

                Button play = new Button("Play Card");
                play.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    Card img = (Card) currentPlayerView.getFrontCard().getProperties().get("card");
                    questsView.setWeapons(img, stage);
                    // add to player's tobeplayed and remove from hand
                    // on successfull submit, cards removed from tobe
                    //currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, null, null);
                });
                Card.Types[] types = {Card.Types.WEAPON};
                currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, play, types);
			}

			consoleView.showButton("Next"
					, e -> {
						if (questsView.hasCard()) {
						    if (row == 0 && !questsView.isFoeStage(stage)) {
						    	questsView.setWeapons(null, stage);
								setup(stage + 1, 0);
							}
						    else if (row == 0)
								setup(stage, row+1);
							else
								setup(stage+1, 0);

						} else {
							consoleView.display("Foe/Test cannot be empty!\nAdd a foe or a test to stage " + stage);
						}
					}
					, 1);
		} else {
            consoleView.display("Done! Submit quest setup?");
            consoleView.showButton("Submit", e -> gameModel.submitQuest(gameModel.getCurrentPlayer().id(), questsView.getQuestSetup()), 1);
		}
    }

    public void setStage(int stage, int row, Image card) {

	}

	public void update() {
        GameStates s = this.gameModel.getState();

        switch (s) {
            case SPONSOR_SUBMIT:
                if (root.getChildren().contains(questsView)) root.getChildren().remove(questsView);

                questsView = new QuestsView(gameModel);
                AnchorPane.setLeftAnchor(questsView, 0.0);
                AnchorPane.setTopAnchor(questsView, 0.0);
                root.getChildren().add(questsView);
        }
    }
}
