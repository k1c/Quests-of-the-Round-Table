/**
 * Author: Carolyne Pelletier
 */
package com.mycompany.app.view;

import com.mycompany.app.model.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.input.MouseEvent;

public class WaitingPlayersView extends GridPane implements GameObserver {

    private GameModel model;
    private ViewGameBoard currentGameState;

    private static final int PADDING = 0;
    private static final int WIDTH = 108;
    private static final int HEIGHT = 150;
    private static final int X_OFFSET = WIDTH/2;

    public WaitingPlayersView(GameModel gameModel) {
        this.model = gameModel;
        this.model.registerObserver(this);
        this.currentGameState = model.getGameBoard();

        // gridpane properties
        setPadding(new Insets(PADDING));
        setAlignment(Pos.TOP_RIGHT);
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setPrefHeight(HEIGHT);
        setVgap(5);

        buildLayout();
    }

    private void buildLayout() {

        // Clear section
        getChildren().clear();

        // Get waiting players info (assume 1, 2, 3 and not 0)
        List<GenericPlayer> waiting = new ArrayList<GenericPlayer>();
        waiting.add(currentGameState.players.get(1));
        waiting.add(currentGameState.players.get(2));
        waiting.add(currentGameState.players.get(3));

        ArrayList<Card> hand = waiting.get(1).hand;
        ArrayList<Card> inplay = waiting.get(1).inPlay;
        int numInHand = hand.size();
        int numInPlay = inplay.size();

        // Create grid
        // get # of cols needed

        // how many columns needed to cover stacked cards
        int cardsPerCol = WIDTH/X_OFFSET;
        int handSpan = (int) Math.floor(numInHand/cardsPerCol + 1);
        int inplaySpan = (int) Math.floor(numInPlay/cardsPerCol + 1);

        // Total number of columns: 2 extra for spacing, 1 for rank,
        int numCol = 2 + 1 + handSpan + inplaySpan;

        // Set gridpane width
        setPrefWidth(numCol * WIDTH);

        // generate and add columns
        for (int i = 0; i < numCol; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            col.setMinWidth(WIDTH);
            col.setPrefWidth(WIDTH);
            getColumnConstraints().add(col);
        }

        // get number of rows
        int numRows = 3;

        for (int i = 0; i < numRows; i++) {
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(HEIGHT);
            row.setMinHeight(HEIGHT);
            row.setPrefHeight(HEIGHT);
            row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            getRowConstraints().add(row);
        }

        // add rank
        String[] ranks = {"R Squire.jpg", "R Knight.jpg", "R Champion Knight.jpg"};
        buildRank(ranks);

        // add in hand
        buildHand(waiting, handSpan);

        // add in play
        buildInPlay(waiting, handSpan+3);
    }

    private void buildRank(String[] ranks) {

        // Create player rank
        for (int i = 0; i < ranks.length; i++) {
            StackPane playerRank = new StackPane();

            GridPane.setColumnIndex(playerRank, 0);
            GridPane.setRowIndex(playerRank, i);

            ImageView rankCard = new ImageView(new Image(ranks[i]));
            rankCard.setFitWidth(WIDTH);
            rankCard.setFitHeight(HEIGHT);

            playerRank.getChildren().add(rankCard);

            getChildren().add(playerRank);
        }
    }

    private void buildHand(List<GenericPlayer> players, int handSpan){

        // For each player, create hand, ONLY BACK OF CARDS: hard coded for now
        for (int i = 0; i < players.size(); i++) {
            StackPane playerHand = new StackPane();

            GridPane.setColumnIndex(playerHand, 2);
            GridPane.setRowIndex(playerHand, i);
            GridPane.setColumnSpan(playerHand, handSpan);
            createStack(players.get(i).hand, playerHand, true);

            getChildren().add(playerHand);
        }

    }

    private void buildInPlay(List<GenericPlayer> players, int index) {

        // For each player, create in play cards
        for (int i = 0; i < players.size(); i++) {
            // Create player in play
            StackPane playerInplay = new StackPane();

            GridPane.setColumnIndex(playerInplay, index);
            GridPane.setColumnSpan(playerInplay, REMAINING);
            GridPane.setRowIndex(playerInplay, i);

            createStack(players.get(i).inPlay, playerInplay, false);

            getChildren().add(playerInplay);
        }
    }

    private void createStack(ArrayList<Card> cards, StackPane s, final boolean flag) {
        int offset = 0;

        for (Card card : cards) {
            final ImageView image;
            if (flag) {
                image = new ImageView(new Image("A Back.jpg"));
            }
            else {
                image = new ImageView(new Image(card.res));
                // Add border with same color as card
                image.getProperties().put("color", getColor(card));
            }


            // Set alignment
            StackPane.setAlignment(image, Pos.BOTTOM_LEFT);

            // Scale image
            image.setFitHeight(HEIGHT);
            image.setFitWidth(WIDTH);

            // Move to left for 'stack' effect
            image.setTranslateX(X_OFFSET * offset);

            // Add 'correct' position of the card in the stackpane
            // correct means visual index instead of calculated z-index which changes
            image.setId(offset + "");

            image.setStyle((String) image.getProperties().get("color"));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            s.getChildren().add(image);

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (!flag)
                        image.setStyle((String) image.getProperties().get("color"));
                }
            });
        }
    }

    private String getColor(Card card) {
        String css = "-fx-effect: innershadow(gaussian, ";

        switch (card.type) {
            case FOE:
                css += "white, ";
                break;
            case ALLY:
                css += "deepskyblue, ";
                break;
            case TEST:
                css += "forestgreen, ";
                break;
            case AMOUR:
                css += "yellow, ";
                break;
            case WEAPON:
                css += "red, ";
                break;
        }

        css += "10, 0.7, 0, 0);";

        return css;
    }

    public void update() {
        this.currentGameState = model.getGameBoard();
        buildLayout();
    }
}