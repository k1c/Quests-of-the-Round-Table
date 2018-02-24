/**
 * Author: Carolyne Pelletier
 *
 * TODO:
 * 1) Able to update correctly
 */
package com.mycompany.app.view;

import com.mycompany.app.model.*;
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
import javafx.scene.text.Font;

public class WaitingPlayersView extends GridPane implements GameObserver {

    private GameModel gameModel;
    private List<GenericPlayer> waiting;

    private static final int PADDING = 0;
    private static final int WIDTH = 108;
    private static final int HEIGHT = 150;
    private static final int X_OFFSET = WIDTH/3;

    public WaitingPlayersView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.registerObserver(this);
        this.waiting = gameModel.getWaitingPlayers();

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

        while (getRowConstraints().size() > 0) {
            getRowConstraints().remove(0);
        }

        while (getColumnConstraints().size() > 0) {
            getColumnConstraints().remove(0);
        }

        // Get waiting players info

        // Find highest hand and inplay, get ranks
        int handIndex = 0;
        int inplayIndex = 0;
        int hHand = 0;
        int hInplay = 0;
        int c = waiting.size();

        String[] ranks = new String[c];
        for (GenericPlayer p : waiting) {
            if (p.hand.size() >= hHand) {
                hHand = p.hand.size();
                handIndex = waiting.indexOf(p);
            }

            if (p.inPlay.size() >= hInplay) {
                hInplay = p.inPlay.size();
                inplayIndex = waiting.indexOf(p);
            }

            ranks[c-1] = p.rank.getPath();
            c--;
        }

        List<Card> hand = waiting.get(handIndex).hand;
        List<Card> inplay = waiting.get(inplayIndex).inPlay;
        int numInHand = 1;
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
        int numRows = waiting.size();

        for (int i = 0; i < numRows; i++) {
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(HEIGHT);
            row.setMinHeight(HEIGHT);
            row.setPrefHeight(HEIGHT);
            row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
            getRowConstraints().add(row);
        }

        // add ranks
        buildRank(ranks);

        // add shields and mordred button
        buildShield();

        // add in hand
        buildHand(waiting, handSpan);

        // add in play
        buildInPlay(waiting, handSpan+3);

        // add battle points
       // buildBattlePoints(waiting, )


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

    private void buildShield() {
        int i = waiting.size();
        for (GenericPlayer p : waiting) {
            VBox box = new VBox(10);
            box.setAlignment(Pos.CENTER);

            StackPane image = new StackPane();
            // add shield image
            ImageView shield= new ImageView(new Image(p.shieldImage));
            shield.setPreserveRatio(true);
            shield.setFitWidth(WIDTH/1.2);

            Label currShields = new Label(p.rank.getShields() + "/" + p.rank.getMaxShields());
            currShields.setFont(new Font("Cambria", 30));
            currShields.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            image.getChildren().add(shield);
            image.getChildren().add(currShields);
            StackPane.setAlignment(currShields, Pos.CENTER);

            // add button
            Button mordred = new Button("Play Mordred");

            box.getChildren().addAll(image, mordred);
            GridPane.setColumnIndex(box, 1);
            GridPane.setRowIndex(box, i-1);

            i--;

            getChildren().add(box);
        }
    }

    private void buildHand(List<GenericPlayer> players, int handSpan){

        // For each player, create hand, ONLY BACK OF CARD
        for (int i = players.size() - 1; i >= 0; i--) {
            StackPane playerHand = new StackPane();

            GridPane.setColumnIndex(playerHand, 2);
            GridPane.setRowIndex(playerHand, i);
            GridPane.setColumnSpan(playerHand, handSpan);

            final ImageView back = new ImageView(new Image("A Back.jpg"));
            back.setPreserveRatio(true);
            back.setFitWidth(WIDTH/1.2);
            String playerHandSize = Integer.toString(players.get(i).hand.size());

            Label pHandSize = new Label(playerHandSize);
            pHandSize.setFont(new Font("Cambria", 30));
            pHandSize.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            playerHand.getChildren().add(back);
            playerHand.getChildren().add(pHandSize);
            StackPane.setAlignment(pHandSize, Pos.CENTER);

            getChildren().add(playerHand);
        }

    }

    private void buildInPlay(List<GenericPlayer> players, int index) {

        // For each player, create in play cards
        for (int i = players.size() - 1; i >= 0; i--) {
            // Create player in play
            StackPane playerInplay = new StackPane();

            GridPane.setColumnIndex(playerInplay, index);
            GridPane.setColumnSpan(playerInplay, REMAINING);
            GridPane.setRowIndex(playerInplay, i);

            createStack(players.get(i).inPlay, playerInplay, false);

            getChildren().add(playerInplay);
        }
    }

    private void createStack(List<Card> cards, StackPane s, final boolean faceDown) {
        int offset = 0;

        for (Card card : cards) {
            final ImageView image;
            if (faceDown) {
                image = new ImageView(new Image("A Back.jpg"));
            }
            else {
                image = new ImageView(new Image(card.res));
                // Add border with same color as card
                image.getProperties().put("color", getColor(card));

                // Add focus event handler
                image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, s));
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
            //offset++;

            // Add to StackPane
            s.getChildren().add(image);

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    if (!faceDown)
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
        this.waiting = gameModel.getWaitingPlayers();
        buildLayout();
    }
}
