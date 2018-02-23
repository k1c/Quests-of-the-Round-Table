/**
 * Author: Akhil Dalal
 * As part of TEAM 4
 *
 * CurrentPlayerView - Renders and sets up view for current player
 *
 * TODO:
 * 1) Add eventhandler for playing the cards
 * 2) Able to update correctly
 * 3) Start face down
 * 4) Able to go face up after clicking Start Turn button
 */
package com.mycompany.app.view;

import com.mycompany.app.model.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * # of cols needed: perCol = WIDTH/OFFSET, numCol = numCards/perCol + 1 floor
 * total = 1 (rank) + ^ (hand) + ^ (inplay)
 * Potential issue:
 * 1) Currently passing in the game model object
 * - Any way to avoid this or make it better?
 */
    public class CurrentPlayerView extends GridPane implements GameObserver {

    private GameModel gameModel;
    private GenericPlayer current;

    private static final int PADDING = 0;
    private static final int WIDTH = 146;
    private static final int HEIGHT = 200;
    private static final int X_OFFSET = WIDTH/3;

    public CurrentPlayerView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.registerObserver(this);
        this.current = gameModel.getCurrentPlayer();

        // gridpane properties
        setPadding(new Insets(PADDING));
        setAlignment(Pos.BOTTOM_RIGHT);
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setPrefHeight(HEIGHT);

        buildLayout();
        setHgap(10);
    }

    private void buildLayout() {

        // Clear section
        getChildren().clear();

        while (getRowConstraints().size() > 0) {
            getRowConstraints().remove(0);
        }

        while (getColumnConstraints().size() > 0) {
            getColumnConstraints().remove(0);
        }

        // Get current player info
        List<Card> hand = current.hand;
        List<Card> inplay = current.inPlay;

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

        // add row
        RowConstraints row = new RowConstraints();
        row.setMaxHeight(HEIGHT);
        row.setMinHeight(HEIGHT);
        row.setPrefHeight(HEIGHT);
        row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        getRowConstraints().add(row);

        // add rank
        String rank = current.rank.getPath();
        buildRank(rank);

        // add shield + mordred button
        String shield = current.shieldImage;
        buildShield(shield);

        // add in hand
        buildHand(hand, handSpan);

        // add in play
        buildInPlay(inplay, handSpan+3);
    }

    private void buildRank(String rank) {
        // Create player rank
        StackPane playerRank = new StackPane();
        GridPane.setColumnIndex(playerRank, 0);

        ImageView rankCard = new ImageView(new Image(rank));
        rankCard.setFitWidth(WIDTH);
        rankCard.setFitHeight(HEIGHT);

        playerRank.getChildren().add(rankCard);

        // Border for player name
        playerRank.setPadding(new Insets(10, 0, 0,0));
        playerRank.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 10;");

        // Get border color
        String s = playerRank.getStyle();
        if (current.shieldImage.contains("Blue")) {
            playerRank.setStyle(s + "-fx-border-color: #006bb6;");
        } else if (current.shieldImage.contains("Red")) {
            playerRank.setStyle(s + "-fx-border-color: #aa0000;");
        } else if (current.shieldImage.contains("Green")) {
            playerRank.setStyle(s + "-fx-border-color: #29862a;");
        } else {
            playerRank.setStyle(s + "-fx-border-color: #8e0085;");
        }

        Label title = new Label(current.name);
        title.setStyle("-fx-background-color: #f4f4f4; -fx-font-weight: bold;");
        title.setTranslateY(-HEIGHT/2 - 20);
        title.setTranslateX(-2);
        title.setFont(new Font("Cambria", 26));
        playerRank.getChildren().add(title);

        getChildren().add(playerRank);
    }

    private void buildShield(String shield) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        box.setPadding(new Insets(0, 0,0,20));
        StackPane image = new StackPane();
        // add shield image
        ImageView shieldImage = new ImageView(new Image(shield));
        shieldImage.setPreserveRatio(true);
        shieldImage.setFitWidth(WIDTH/1.2);

        Label currShields = new Label(current.rank.getShields() + "/" + current.rank.getMaxShields());
        currShields.setFont(new Font("Cambria", 40));
        currShields.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        image.getChildren().add(shieldImage);
        image.getChildren().add(currShields);
        StackPane.setAlignment(currShields, Pos.CENTER);

        // add button
        Button mordred = new Button("Play Mordred");

        box.getChildren().addAll(image, mordred);
        GridPane.setColumnIndex(box, 1);
        getChildren().add(box);
    }

    private void buildHand(List<Card> hand, int handSpan){

        // Create player hand
        StackPane playerHand = new StackPane();

        createStack(hand, playerHand);

        /*HBox test = new HBox();
        test.getChildren().add(playerHand);
*/
        GridPane.setColumnIndex(playerHand, 2);
        GridPane.setColumnSpan(playerHand, handSpan);
        /*test.setMaxWidth(handSpan*WIDTH - X_OFFSET + 12);
        test.setPadding(new Insets(10, 0, 0,0));
        test.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 10;" + "-fx-border-color: #006bb6;");

        Label title = new Label(" In Hand ");
        title.setStyle("-fx-background-color: #f4f4f4; -fx-font-weight: bold;");
        title.setTranslateY(-40);
        title.setTranslateX((handSpan*WIDTH - X_OFFSET + 15)/5.5);

        test.getChildren().add(title);
        title.setFont(new Font("Cambria", 26));*/
        getChildren().add(playerHand);
    }

    private void buildInPlay(List<Card> inPlay, int index) {

        // Create player in play
        StackPane playerInplay = new StackPane();

        GridPane.setColumnIndex(playerInplay, index);
        GridPane.setColumnSpan(playerInplay, REMAINING);

        createStack(inPlay, playerInplay);

        getChildren().add(playerInplay);
    }

    private void createStack(List<Card> cards, StackPane s) {
        int offset = 0;

        for (Card card : cards) {
            final ImageView image = new ImageView(new Image(card.res));

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

            // Add border with same color as card
            image.getProperties().put("color", getColor(card));

            image.setStyle((String) image.getProperties().get("color"));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            s.getChildren().add(image);

            // Hover on card -
            // Brings it to front, rearranges other cards for easy access.
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, s));

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    image.setStyle((String) image.getProperties().get("color"));
                }
            });
        }
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
        this.current = gameModel.getCurrentPlayer();
        buildLayout();
    }
}
