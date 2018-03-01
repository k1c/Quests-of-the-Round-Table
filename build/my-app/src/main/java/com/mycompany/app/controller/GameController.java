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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
	private List<Card> questSetup;
    private TournamentView tournamentView;
    private List<Card> tournamentSetup;

	private GenericPlayer current;

	private GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

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
		GenericPlayer curr = gameModel.getCurrentPlayer();
		if (stage <= 1 && row == 0) {
			questsView.clearQuest();
		}
		questSetup = questsView.getQuestSetup().toList();
		curr.hand.removeAll(questSetup);
		currentPlayerView.buildHand(curr.hand, false, null, null);


		if (stage <= gameModel.getNumberOfStages()) {
			questsView.setFocus(stage, row);
			if (row == 0) {
				// foe/test
				log.playerAction(gameModel.getCurrentPlayer(), "adding a foe/test to stage " + stage);
				consoleView.display("Add a foe or a test to stage " + stage);

                Button play = new Button("Play Card");
                play.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    Card img = (Card) currentPlayerView.getFrontCard().getProperties().get("card");
                    log.cardPlayed(gameModel.getCurrentPlayer(), img, "in Quest setup");
                    questsView.setFoeTest(img, stage);
                    // add to player's tobeplayed and remove from hand
					// on successfull submit, cards removed from tobe
					curr.hand.remove(img);
                    currentPlayerView.buildHand(curr.hand, false, null, null);
                });
                Card.Types[] types = {Card.Types.FOE, Card.Types.TEST};
                currentPlayerView.buildHand(curr.hand, false, play, types);

			}
			else {
                //weapons
				log.playerAction(gameModel.getCurrentPlayer(), "adding weapon(s) to stage " + stage);
                consoleView.display("Add weapon(s) to stage " + stage);
				Card.Types[] types = {Card.Types.WEAPON};

                Button play = new Button("Play Card");
                play.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    Card img = (Card) currentPlayerView.getFrontCard().getProperties().get("card");
					log.cardPlayed(gameModel.getCurrentPlayer(), img, "in Quest setup");
                    questsView.setWeapons(img, stage);
                    curr.hand.remove(img);
                    currentPlayerView.buildHand(curr.hand,false,play,types);
                    // add to player's tobeplayed and remove from hand
                    // on successfull submit, cards removed from tobe
                    //currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, null, null);
                });
                currentPlayerView.buildHand(curr.hand, false, play, types);
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
            consoleView.showButton("Submit", e -> {
            	boolean valid = gameModel.submitQuest(gameModel.getCurrentPlayer().id(), questsView.getQuestSetup());
            	if (!valid) {
            		log.error("Quest setup is invalid and it will restart");
            		consoleView.display("The quest setup is invalid!\nPlease rebuild the quest.");
					consoleView.showButton("Setup Quest", e2 -> setup(1, 0), 1);
					//currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand,false,null,null);
				} else {
					log.playerAction(curr, "successfully set up the Quest");
				}
			}, 1);
		}
    }

    public void setupTournament(int player, int row){
        GenericPlayer curr = gameModel.getCurrentPlayer();

        if (player <= 1 && row == 0) {
            tournamentView.clearTournament();
        }
        currentPlayerView.buildHand(curr.hand, false, null, null);

        if (player <= gameModel.getNumParticipants()) {
            tournamentView.setFocus(player, row);
            if (row == 0) {
                log.gameState(gameModel.getCurrentPlayer()+" is added to the tournament");
                consoleView.display("Ready " + gameModel.getCurrentPlayer().name + " ?");
                Card card = new Card(666,gameModel.getCurrentPlayer().rank.getPath(),"Rank",Card.Types.RANK);
                tournamentView.setPlayer(card,player);
            }
            else {
                //cards
                log.playerAction(gameModel.getCurrentPlayer(), "adding card(s) to tournament");
                consoleView.display("Add card(s) in tournament");
                Card.Types[] types = {Card.Types.WEAPON, Card.Types.ALLY, Card.Types.AMOUR};

                Button play = new Button("Play Card");
                play.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    Card img = (Card) currentPlayerView.getFrontCard().getProperties().get("card");
                    log.cardPlayed(gameModel.getCurrentPlayer(), img, "in Tournament setup");
                    tournamentView.setCards(img,player);
                    curr.hand.remove(img);
                    currentPlayerView.buildHand(curr.hand,false,play,types);
                    // add to player's tobeplayed and remove from hand
                    // on successfull submit, cards removed from tobe
                    //currentPlayerView.buildHand(gameModel.getCurrentPlayer().hand, false, null, null);
                });
                currentPlayerView.buildHand(curr.hand, false, play, types);
            }

            consoleView.showButton("Next"
                    , e -> {

                        if (row == 0){
                            setupTournament(player,row+1);
                        }else{
                            List<Card> temp = tournamentView.getTournamentSetup().get(player - 1);
                            temp.remove(0);
                            /*if(!gameModel.tournamentStage(gameModel.getCurrentPlayer().id(), temp)) {
                                setupTournament(player, row);
                            } else {
                                setupTournament(player+1,0);
                            }*/
                            setupTournament(player+1,0);
                        }
                    }
                    , 1);
        } else {
            consoleView.display("Run Tournament");
            consoleView.showButton("Start", e -> {
                gameModel.tournamentStageEnd();
            }, 1);
        }

    }

    // PASS IN CUSTOM HANDLER TO SHOW/HIDE TO PREVENT SCREWUP
    // DISCARD STARTS WITH PLAYER HAND DOWN
    public void discard() {
	    GenericPlayer p = gameModel.getCurrentPlayer();
	    List<Card> discards = new ArrayList<>();
	    consoleView.display(p.name + ", discard to get to 12");
	    consoleView.showButton("Submit Discard", e -> {
	        gameModel.discard(p.id(), discards);
	        if (gameModel.getNumDiscards() > 0)
	            discard();
        }, 1);

	    Button btn = new Button("Discard");
        Card.Types[] types = {Card.Types.FOE, Card.Types.ALLY, Card.Types.TEST, Card.Types.AMOUR, Card.Types.WEAPON};
	    btn.addEventHandler(MouseEvent.MOUSE_CLICKED, removeCard(discards, p, btn, types));
        p.hand.removeAll(discards);
	    currentPlayerView.buildHand(p.hand, false, btn, types);
    }

    public void startFoeStage() {
        GenericPlayer p = gameModel.getCurrentPlayer();

        // a player has to choose cards to play
        // send to stageFoe (p id, list of cards)
        List<Card> toPlay = new ArrayList<>();

        consoleView.display(p.name + ", choose cards to fight the foe.\n Click submit once ready.");
        consoleView.showButton("Submit", e -> {
            gameModel.stageFoe(p.id(), toPlay);
            if (gameModel.getNumParticipants() > 1)
                startFoeStage();
        }, 1);

        Card.Types[] types = {Card.Types.WEAPON, Card.Types.ALLY, Card.Types.AMOUR};

        // Button for the card. Add card from here to list.
        Button btn = new Button("Play");
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, removeCard(toPlay, p, btn, types));
        p.hand.removeAll(toPlay);
        currentPlayerView.buildHand(p.hand, false, btn, types);
    }

    public void startTestStage() {
        GenericPlayer p = gameModel.getCurrentPlayer();
        List<Card> bids = new ArrayList<>();

        consoleView.display(p.name + ", choose the cards you'd like to bid.\n");
        consoleView.showButton("Submit", e -> {
            if(!gameModel.stageTest(p.id(), bids)){
            	consoleView.display("You need to bid more!");
            }
            if (gameModel.getNumParticipants() > 1)
                startTestStage();
        }, 1);
        consoleView.showButton("Give up", e -> {
            gameModel.testGiveUp(p.id());
            if (gameModel.getNumParticipants() > 1)
                startTestStage();
        }, 2);

        Card.Types[] types = {Card.Types.FOE, Card.Types.ALLY, Card.Types.WEAPON, Card.Types.TEST, Card.Types.AMOUR};

        Button btn = new Button("Play");

        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, removeCard(bids, p, btn, types));

        p.hand.removeAll(bids);
        currentPlayerView.buildHand(p.hand, false, btn, types);

    }

    public EventHandler<MouseEvent> removeCard(List<Card> cards, GenericPlayer p, Button btn, Card.Types[] types) {
        return e -> {
            Card img = (Card) currentPlayerView.getFrontCard().getProperties().get("card");
            cards.add(img);
            p.hand.remove(img);
            currentPlayerView.buildHand(p.hand,false,btn, types);
        };
    }

	public void update() {
        GameStates s = this.gameModel.getState();

        switch (s) {
            case TOURNAMENT_HANDLER:
                if (root.getChildren().contains(tournamentView)) root.getChildren().remove(tournamentView);
                tournamentView = new TournamentView((gameModel));
                AnchorPane.setLeftAnchor(tournamentView, 0.0);
                AnchorPane.setTopAnchor(tournamentView, 0.0);
                root.getChildren().add(tournamentView);
                break;
            case SPONSOR_SUBMIT:
                if (root.getChildren().contains(questsView)) root.getChildren().remove(questsView);
                questsView = new QuestsView(gameModel);
                AnchorPane.setLeftAnchor(questsView, 0.0);
                AnchorPane.setTopAnchor(questsView, 0.0);
                root.getChildren().add(questsView);
                break;
            case QUEST_END:
                consoleView.display("End of quest!");
                consoleView.showButton("Finish", e -> {
                    root.getChildren().remove(questsView);
                    gameModel.endQuest();
                }, 1);
                break;
            case TOURNAMENT_END:
                consoleView.display("END OF TOURNEY");
        }
    }
}
