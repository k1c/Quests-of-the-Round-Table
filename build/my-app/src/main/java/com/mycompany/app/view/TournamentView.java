package com.mycompany.app.view;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Card;
import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.GameObserver;
import com.mycompany.app.model.TwoDimensionalArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.List;

public class TournamentView extends GridPane implements GameObserver, CardStack  {
    private final int WIDTH = 146;
    private final int HEIGHT = 200;
    private final GameModel gameModel;

    private StackPane[] players;
    private StackPane[] tournamentCards;

    private boolean set = false;
    private int Y_OFFSET = HEIGHT/5;
    private TwoDimensionalArrayList<Card> tournamentSetup;

    GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

    public TournamentView(GameModel gameModel) {
        this.gameModel = gameModel;

        this.tournamentSetup = new TwoDimensionalArrayList<>();

        setHgap(10.0);
        setVgap(5.0);

        buildLayout();
    }

    private void buildLayout() {
        getChildren().clear();

        while (getRowConstraints().size() > 0) {
            getRowConstraints().remove(0);
        }

        while (getColumnConstraints().size() > 0) {
            getColumnConstraints().remove(0);
        }

        // Get quest info
        int numStages = this.gameModel.getNumberOfStages();
        ImageView questCard = new ImageView(new Image(this.gameModel.getCurrentStory().res));

        // Build grid
        // 2 rows
        // numStages columns + 1 for quest card

        // Columns
        for (int i = 0; i < numStages + 1; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.SOMETIMES);
            col.setMinWidth(WIDTH);
            col.setPrefWidth(WIDTH);
            getColumnConstraints().add(col);
        }

        // Rows
        for (int i = 0; i < 2; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            row.setMinHeight(HEIGHT);
            row.setPrefHeight(HEIGHT);
            getRowConstraints().add(row);
        }

        // Display Quest card
        showQuestCard(questCard);

        // Display Stages placeholders
        showStages(numStages);

        // Display Weapon placeholders
        showWeapons(numStages);

        // Display total BP
    }

    private void showQuestCard (ImageView questCard) {
        StackPane qc = new StackPane();

        questCard.setFitHeight(HEIGHT);
        questCard.setFitWidth(WIDTH);

        qc.getChildren().add(questCard);

        GridPane.setRowIndex(qc, 0);
        GridPane.setColumnIndex(qc, 0);

        getChildren().add(questCard);
    }

    private void showStages(int numStages) {
        // players[i] is the for the foe at player i+1
        players = new StackPane[numStages];

        for (int i = 0; i < numStages; i++) {
            players[i] = new StackPane();
            players[i].setPrefHeight(HEIGHT);
            players[i].setPrefWidth(WIDTH);
            players[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");

            Label placeholder = new Label("Foe/Test");
            placeholder.setStyle("-fx-font-weight: bold; -fx-font-size: 26;");

            players[i].getChildren().add(placeholder);
            players[i].setAlignment(Pos.CENTER);
            GridPane.setColumnIndex(players[i], (i+1));
            GridPane.setRowIndex(players[i], 0);
            getChildren().add(players[i]);
        }
    }

    private void showWeapons(int numStages) {
        // tournamentCards[i] is for the tournamentCards at player i+1
        tournamentCards = new StackPane[numStages];

        for (int i = 0; i < numStages; i++) {
            tournamentCards[i] = new StackPane();
            tournamentCards[i].setPrefHeight(HEIGHT);
            tournamentCards[i].setPrefWidth(WIDTH);
            tournamentCards[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");

            Label placeholder = new Label("Weapons");
            placeholder.setStyle("-fx-font-weight: bold; -fx-font-size: 26;");

            tournamentCards[i].getChildren().add(placeholder);
            tournamentCards[i].setAlignment(Pos.CENTER);
            GridPane.setColumnIndex(tournamentCards[i], (i+1));
            GridPane.setRowIndex(tournamentCards[i], 1);
            getChildren().add(tournamentCards[i]);
        }
    }

    public boolean hasCard() {
        return set;
    }

    public void setFoeTest(Card card, int player) {
        tournamentSetup.addToInnerArray(player-1, card);
        set = true;


        players[player-1].getChildren().clear();

        ImageView img = new ImageView(new Image(card.res));
        img.setTranslateX(0);
        img.setFitWidth(WIDTH);
        img.setFitHeight(HEIGHT);

        players[player-1].getChildren().add(img);
    }

    public void setFocus(int player, int row) {
        set = false;
        int i = 0;
        if (row == 0) {
            // foe/test
            for (StackPane s : players) {
                s.setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                tournamentCards[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                i++;
            }

            players[player-1].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: gold");

        } else {
            // tournamentCards
            // foe/test
            for (StackPane w : tournamentCards) {
                players[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                w.setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                i++;
            }

            tournamentCards[player-1].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: gold");

            set = true;
        }
    }

    public void setWeapons(Card card, int player) {

        if (card == null) {
            tournamentCards[player-1].setStyle("");
            tournamentCards[player-1].getChildren().clear();
            return;
        }

        tournamentSetup.addToInnerArray(player - 1, card);

        tournamentCards[player-1].getChildren().clear();

        List<Card> cards = tournamentSetup.get(player - 1);

        int offset = 0;

        boolean skip = true;
        // restack
        for (Card c : cards) {
            if (skip) {
                skip = false;
                continue;
            }
            ImageView image = new ImageView(new Image(c.res));

            // Scale image
            image.setFitHeight(HEIGHT);
            image.setFitWidth(WIDTH);

            // Move to down for 'stack' effect
            image.setTranslateY(Y_OFFSET * offset);

            // Add card object as a property
            image.getProperties().put("card", c);

            // Add image front path
            image.getProperties().put("front", c.res);

            // Add 'correct' position of the card in the stackpane
            // correct means visual index instead of calculated z-index which changes
            image.setId(offset + "");

            image.setStyle((String) image.getProperties().get("color"));

            // Add focus event handler
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, tournamentCards[player - 1], false, 0, null, null));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            tournamentCards[player - 1].getChildren().add(image);

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                image.setStyle((String) image.getProperties().get("color"));
            });
        }
    }

    public void clearTournament() {
        tournamentSetup.clear();
        buildLayout();
    }

    public boolean isFoeStage(int player) {
        if (tournamentSetup.get(player - 1).get(0).type == Card.Types.FOE )
            return true;

        return false;
    }

    public TwoDimensionalArrayList<Card> getTournamentSetup() {
        return this.tournamentSetup;
    }

    /*
     * if facedown, render everything facedown
     * else for each player # in player, render them face up and rest facedown.
     */
    public void rebuild(boolean faceDown, int[] player) {

    }

    public void update() {

    }
}
