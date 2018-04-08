/*
 * Author: Carolyne Pelletier
 *
 * TODO:
 * 1) Able to update correctly
 */
package com.mycompany.app.view;

import com.mycompany.app.model.states_api.GameState.GameModel;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.text.Font;

public class WaitingPlayersView extends GridPane implements GameObserver, CardStack {

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
        setVgap(10);
        setHgap(5);

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

        for (GenericPlayer p : waiting) {
            if (p.hand.size() >= hHand) {
                hHand = p.hand.size();
                handIndex = waiting.indexOf(p);
            }

            if (p.inPlay.size() >= hInplay) {
                hInplay = p.inPlay.size();
                inplayIndex = waiting.indexOf(p);
            }
        }

        List<Card> hand = waiting.get(handIndex).hand;
        List<Card> inplay = waiting.get(inplayIndex).inPlay;
        int numInHand = 1;
        int numInPlay = inplay.size();

        // Create grid
        // get # of cols needed

        // how many columns needed to cover stacked cards
        double cardsPerCol = WIDTH/X_OFFSET;
        int handSpan = (int) Math.floor(numInHand/cardsPerCol + 1);
        int inplaySpan = (int) Math.ceil(numInPlay/cardsPerCol + 1);

        // Total number of columns: 1 for rank, 1 for shield, handspan, inplay, BP
        int numCol = 1 + 1 + handSpan + inplaySpan + 1 + 1;

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
        buildRank();

        // add shields and mordred button
        buildShield();

        // add in hand
        buildHand(waiting, handSpan);

        // add in play
        buildInPlay(waiting, handSpan+2, inplaySpan);

        buildToBePlayed(numCol - 1);

        // add battle points
        buildBattlePoints(waiting, numCol);

    }

    private void buildRank() {

        // Create player rank
        int i = waiting.size() - 1;
        for (GenericPlayer p : waiting) {
            StackPane playerRank = new StackPane();

            GridPane.setColumnIndex(playerRank, 0);
            GridPane.setRowIndex(playerRank, i);

            ImageView rankCard = new ImageView(new Image(p.rank.getPath()));
            rankCard.setFitWidth(WIDTH);
            rankCard.setFitHeight(HEIGHT);

            playerRank.getChildren().add(rankCard);

            i--;

            getChildren().add(playerRank);
        }
    }

    private void buildShield() {
        int i = waiting.size() - 1;
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

            Label name = new Label(p.name);
            name.setFont(new Font("Cambria", 15));
            name.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            StackPane.setAlignment(name, Pos.TOP_CENTER);
            image.getChildren().add(shield);
            image.getChildren().add(currShields);
            image.getChildren().add(name);
            StackPane.setAlignment(currShields, Pos.CENTER);

            // add button
            Button mordred = new Button("Play Mordred");

            box.getChildren().addAll(image, mordred);
            GridPane.setColumnIndex(box, 1);
            GridPane.setRowIndex(box, i);

            i--;

            getChildren().add(box);
        }
    }

    private void buildHand(List<GenericPlayer> players, int handSpan){

        int i = waiting.size() - 1;

        // For each player, create hand, ONLY BACK OF CARD
        for (GenericPlayer p : waiting) {
            StackPane playerHand = new StackPane();

            GridPane.setColumnIndex(playerHand, 2);
            GridPane.setRowIndex(playerHand, i);
            GridPane.setColumnSpan(playerHand, handSpan);


            final ImageView back = new ImageView(new Image("A Back.jpg"));
            back.setFitWidth(WIDTH);
            back.setFitHeight(HEIGHT);
            String playerHandSize = Integer.toString(p.hand.size());

            Label pHandSize = new Label(playerHandSize);
            pHandSize.setFont(new Font("Cambria", 30));
            pHandSize.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            Label playLabel = new Label("In Hand");
            playLabel.setFont(new Font("Cambria", 15));
            playLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            StackPane.setAlignment(playLabel, Pos.TOP_CENTER);

            playerHand.getChildren().add(back);
            playerHand.getChildren().add(pHandSize);
            playerHand.getChildren().add(playLabel);

            StackPane.setAlignment(pHandSize, Pos.CENTER);

            i--;

            getChildren().add(playerHand);
        }

    }

    private void buildInPlay(List<GenericPlayer> players, int index, int inplaySpan) {

        int i = waiting.size() - 1;
        // For each player, create in play cards
        for (GenericPlayer p : waiting) {
            // Create player in play
            StackPane playerInplay = new StackPane();

            GridPane.setColumnIndex(playerInplay, index);
            GridPane.setColumnSpan(playerInplay, inplaySpan);
            GridPane.setRowIndex(playerInplay, i);

            createStack(p.inPlay, playerInplay, false, false, HEIGHT, WIDTH, X_OFFSET, null, null);

            i--;

            getChildren().add(playerInplay);
        }
    }

    private void buildToBePlayed(int index){

        int i = waiting.size() - 1;

        // For each player, create hand, ONLY BACK OF CARD
        for (GenericPlayer p : waiting) {
            StackPane playerHand = new StackPane();

            GridPane.setColumnIndex(playerHand, index);
            GridPane.setRowIndex(playerHand, i);


            final ImageView back = new ImageView(new Image("A Back.jpg"));
            back.setFitWidth(WIDTH);
            back.setFitHeight(HEIGHT);
            String playerHandSize = Integer.toString(p.toBePlayed.size());

            Label pHandSize = new Label(playerHandSize);
            pHandSize.setFont(new Font("Cambria", 30));
            pHandSize.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");


            Label playLabel = new Label("To Be Played");
            playLabel.setFont(new Font("Cambria", 15));
            playLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
            StackPane.setAlignment(playLabel, Pos.TOP_CENTER);

            playerHand.getChildren().add(back);
            playerHand.getChildren().add(pHandSize);
            playerHand.getChildren().add(playLabel);
            StackPane.setAlignment(pHandSize, Pos.CENTER);

            i--;

            getChildren().add(playerHand);
        }

    }

    private void buildBattlePoints(List<GenericPlayer> players, int index) {

        int  i = waiting.size() - 1;
        // For each player, create in play cards
        for (GenericPlayer p : waiting) {
            // Create player in play
            StackPane playerBP = new StackPane();

            GridPane.setColumnIndex(playerBP, index);
            GridPane.setRowIndex(playerBP, i);

            ImageView bp = new ImageView(new Image("Battle_Points.png"));
            bp.setPreserveRatio(true);
            bp.setFitWidth(WIDTH);
            String pBP = Integer.toString(p.totalBattlePoints);

            Label pBPLabel = new Label(pBP);
            pBPLabel.setFont(new Font("Cambria", 30));
            pBPLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

            playerBP.getChildren().add(bp);
            playerBP.getChildren().add(pBPLabel);
            StackPane.setAlignment(pBPLabel, Pos.CENTER);

            i--;

            getChildren().add(playerBP);
        }
    }

    public void update() {
        this.waiting = gameModel.getWaitingPlayers();
        buildLayout();
    }
}
