/*
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
import javafx.event.EventType;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;
import javafx.scene.text.Font;

/**
 * # of cols needed: perCol = WIDTH/OFFSET, numCol = numCards/perCol + 1 floor
 * total = 1 (rank) + ^ (hand) + ^ (inplay)
 * Potential issue:
 * 1) Currently passing in the game model object
 * - Any way to avoid this or make it better?
 */
    public class CurrentPlayerView extends GridPane implements GameObserver, CardStack {

    private GameModel gameModel;
    private GenericPlayer current;

    private static final int PADDING = 0;
    private static final int WIDTH = 146;
    private static final int HEIGHT = 200;
    private static final int X_OFFSET = WIDTH/3;

    private int handSpan;
    private CheckBox show;
    private StackPane playerHand;

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
        double cardsPerCol = WIDTH/X_OFFSET;
        handSpan = (int) Math.floor(numInHand/cardsPerCol + 1);
        int inplaySpan = (int) Math.ceil((numInPlay/cardsPerCol) + 1);

        // Total number of columns: 1 for rank, 1 for shield, handspan, inplay, BP
        int numCol = 1 + 1 + handSpan + 2 + inplaySpan + 1 + 1;

        // Set gridpane width
        setPrefWidth(numCol * WIDTH);

        // generate and add columns
        for (int i = 0; i < numCol; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
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
        buildShield(shield, handSpan);

        // add in hand
        buildHand(hand, true, null, null);

        // add in play
        buildInPlay(inplay, handSpan+3, inplaySpan);

        // add tobeplayed
        buildToBePlayed(numCol - 1);

        // add battle points
        buildBattlePoints(current, numCol);
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

    private void buildShield(String shield, int handSpan) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);

        box.setPadding(new Insets(0, 0,0,20));

        // add checkbox for show/hide
        show = new CheckBox("Show hand");
        show.setAllowIndeterminate(false);
        show.selectedProperty().addListener(e -> {
            if (getChildren().contains(playerHand)) getChildren().remove(playerHand);

            if (show.isSelected()) {
                buildHand(current.hand, false, null, null);
            } else {
                buildHand(current.hand,true, null, null);
            }
        });

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
        box.getChildren().addAll(show, image, mordred);
        GridPane.setColumnIndex(box, 1);
        getChildren().add(box);
    }

    public void buildHand(List<Card> hand, boolean faceDown, Button btn, Card.Types[] types){
        if(getChildren().contains(playerHand)) getChildren().remove(playerHand);

        // Create player hand
        playerHand = new StackPane();

        createStack(hand, playerHand, faceDown, true, HEIGHT, WIDTH, X_OFFSET, btn, types);

        GridPane.setColumnIndex(playerHand, 2);
        GridPane.setColumnSpan(playerHand, handSpan);
        getChildren().add(playerHand);
    }

    private void buildInPlay(List<Card> inPlay, int index, int inplaySpan) {

        // Create player in play
        StackPane playerInplay = new StackPane();

        GridPane.setColumnIndex(playerInplay, index);
        GridPane.setColumnSpan(playerInplay, inplaySpan);

        createStack(inPlay, playerInplay, false, true, HEIGHT, WIDTH, X_OFFSET, null, null);

        getChildren().add(playerInplay);
    }

    private void buildToBePlayed(int index) {
        StackPane playerToBePlayed = new StackPane();

        GridPane.setColumnIndex(playerToBePlayed, index);
        GridPane.setRowIndex(playerToBePlayed, 0);

        final ImageView back = new ImageView(new Image("A Back.jpg"));
        back.setFitWidth(WIDTH);
        back.setFitHeight(HEIGHT);
        String playerHandSize = Integer.toString(gameModel.getCurrentPlayer().toBePlayed.size());

        Label pHandSize = new Label(playerHandSize);
        pHandSize.setFont(new Font("Cambria", 40));
        pHandSize.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        StackPane.setAlignment(pHandSize, Pos.CENTER);

        Label playLabel = new Label("To Be Played");
        playLabel.setFont(new Font("Cambria", 15));
        playLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        StackPane.setAlignment(playLabel, Pos.TOP_CENTER);

        playerToBePlayed.getChildren().add(back);
        playerToBePlayed.getChildren().add(pHandSize);
        playerToBePlayed.getChildren().add(playLabel);

        getChildren().add(playerToBePlayed);
    }

    private void buildBattlePoints(GenericPlayer players, int index) {
        StackPane playerBP = new StackPane();

        GridPane.setColumnIndex(playerBP, index);
        GridPane.setRowIndex(playerBP, 0);

        ImageView bp = new ImageView(new Image("Battle_Points.png"));
        bp.setPreserveRatio(true);
        bp.setFitWidth(WIDTH);

        String pBP = Integer.toString(current.totalBattlePoints);

        Label pBPLabel = new Label(pBP);
        pBPLabel.setFont(new Font("Cambria", 30));

        pBPLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

        playerBP.getChildren().add(bp);
        playerBP.getChildren().add(pBPLabel);
        StackPane.setAlignment(pBPLabel, Pos.CENTER);

        getChildren().add(playerBP);
    }

    public ImageView getFrontCard() {
        return (ImageView) playerHand.getChildren().get(playerHand.getChildren().size() - 2);
    }

    public void update() {
        this.current = gameModel.getCurrentPlayer();

        buildLayout();

        GameStates s = gameModel.getState();
        switch (s) {
            case SPONSOR_SUBMIT:
                if (!show.isSelected())
                    show.setSelected(true);
                break;
        }
    }
}
