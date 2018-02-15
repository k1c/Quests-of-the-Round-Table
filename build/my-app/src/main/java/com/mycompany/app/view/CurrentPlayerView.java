/**
 * Author: Akhil Dalal
 * As part of TEAM 4
 *
 * CurrentPlayerView - Renders and sets up view for current player
 *                   - TODO: Add eventhandler for playing the cards
 */
package com.mycompany.app.view;

import com.mycompany.app.model.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.*;

import javafx.scene.input.MouseEvent;

/**
 * Potential issue:
 * 1) Currently passing in the game model object
 * - Any way to avoid this or make it better?
 */
    public class CurrentPlayerView extends HBox implements GameObserver {

    private GameModel model;
    private ViewGameBoard currentGameState;

    private static final int PADDING = 5;
    private static final int X_OFFSET = -75;

    public CurrentPlayerView(GameModel gameModel) {
        this.model = gameModel;
        this.model.registerObserver(this);
        this.currentGameState = model.getGameBoard();

        setPadding(new Insets(PADDING));
        setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(25);
        buildLayout();
    }

    private void buildLayout() {
        getChildren().clear();

        // rank
        buildRank();

        // in hand
        buildHand();

        // in play
        buildInPlay();
    }

    private void buildRank() {

    }

    private void buildHand(){

        StackPane playerHand = new StackPane();
        playerHand.setPadding(new Insets(PADDING));
        playerHand.setAlignment(Pos.BOTTOM_CENTER);
        int offset = 0;

        // get current player
        GenericPlayer current = currentGameState.players.get(0);

        // get current player hand
        ArrayList<Card> hand = current.hand;

        // For each card, add it to stackpane and attach eventhandlers
        for (Card card : hand) {
            final ImageView image = new ImageView(new Image(card.res));

            // Scale image
            image.setFitHeight(200);
            image.setFitWidth(146);

            // Move to left for 'stack' effect
            image.setTranslateX(X_OFFSET * offset);

            // Add 'correct' position of the card in the stackpane
            // correct means visual index instead of calculated z-index which changes
            image.setId(offset + "");

            // Add border with same color as card
            image.getProperties().put("color", getColor(card));

            image.setStyle((String) image.getProperties().get("color"));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            //add(image, 1 ,0);
            playerHand.getChildren().add(image);

            // Hover on card -
            // Brings it to front, rearranges other cards for easy access.
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, playerHand));

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    image.setStyle((String) image.getProperties().get("color"));
                }
            });
        }
        ArrayList<Card> hand2 = current.inPlay;
        StackPane playerHand3 = new StackPane();
        offset -= 1;
        // For each card, add it to stackpane and attach eventhandlers
        for (Card card : hand2) {
            final ImageView image = new ImageView(new Image(card.res));

            // Scale image
            image.setFitHeight(200);
            image.setFitWidth(146);

            // Move to left for 'stack' effect
            image.setTranslateX(X_OFFSET * offset);

            // Add 'correct' position of the card in the stackpane
            // correct means visual index instead of calculated z-index which changes
            image.setId(offset + "");

            // Add border with same color as card
            image.getProperties().put("color", getColor(card));

            image.setStyle((String) image.getProperties().get("color"));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            //add(image, 1 ,0);
            playerHand3.getChildren().add(image);

            // Hover on card -
            // Brings it to front, rearranges other cards for easy access.
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, playerHand3));

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    image.setStyle((String) image.getProperties().get("color"));
                }
            });
        }

        getChildren().add(playerHand3);
        getChildren().add(playerHand);

        StackPane playerHand2 = new StackPane();
        ImageView image = new ImageView(new Image("R Squire.jpg"));
        image.setFitHeight(200);
        image.setFitWidth(146);
        playerHand2.getChildren().add(image);
        getChildren().add(playerHand2);
    }

    private void buildInPlay() {

    }

    private EventHandler<MouseEvent> focusCard(final ImageView image, final StackPane p) {
        return new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                // Selection color
                image.setStyle("-fx-effect: dropshadow(gaussian, #ff7c14, 5, 1, 0, 0)");

                // Get all current cards on display
                List<Node> children = new ArrayList<Node>(p.getChildren());

                // Sort by actual index instead of z-index
                Collections.sort(children, new Comparator<Node>() {
                    public int compare(Node o1, Node o2) {
                        return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
                    }
                });

                // Get position of card user is hovering over
                int index = children.indexOf(image);

                // Start to re-arrange cards from both ends till they meet
                int right = 0;
                int left = children.size() - 1;

                while (right != left) {
                    if (!children.get(right).equals(image)) {
                        children.get(right).toFront();
                        right++;
                    }

                    if (!children.get(left).equals(image)) {
                        children.get(left).toFront();
                        left--;
                    }
                }

                // Bring target card in front
                children.get(index).toFront();
            }
        };
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
        // UPDATE CURRENT PLAYER STUFF
        this.currentGameState = model.getGameBoard();
        buildLayout();
    }
}