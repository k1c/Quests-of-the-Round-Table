/**
 * TODO:
 * 1) Able to query model for # of stages
 * 2) Able to select each stage and build it
 * 3) Able to submit proposed quest
 * 4) If denied, able to correct
 * 5) If accepted, able to run correctly (face down, face up per stage encounter)
 */
package com.mycompany.app.view;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.Cards.Card;
import com.mycompany.app.model.DataStructures.TwoDimensionalArrayList;
import com.mycompany.app.model.Interfaces.GameObserver;
import com.mycompany.app.model.GameModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.util.List;

public class QuestsView extends GridPane implements GameObserver, CardStack{
    private final int WIDTH = 146;
    private final int HEIGHT = 200;
    private final GameModel gameModel;

    private StackPane[] stages;
    private StackPane[] weapons;

    private boolean set = false;
    private int Y_OFFSET = HEIGHT/5;
    private TwoDimensionalArrayList<Card> questSetup;

    GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

    public QuestsView(GameModel gameModel) {
        this.gameModel = gameModel;

        this.questSetup = new TwoDimensionalArrayList<>();

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
        // stages[i] is the for the foe at stage i+1
        stages = new StackPane[numStages];

        for (int i = 0; i < numStages; i++) {
            stages[i] = new StackPane();
            stages[i].setPrefHeight(HEIGHT);
            stages[i].setPrefWidth(WIDTH);
            stages[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");

            Label placeholder = new Label("Foe/Test");
            placeholder.setStyle("-fx-font-weight: bold; -fx-font-size: 26;");

            stages[i].getChildren().add(placeholder);
            stages[i].setAlignment(Pos.CENTER);
            GridPane.setColumnIndex(stages[i], (i+1));
            GridPane.setRowIndex(stages[i], 0);
            getChildren().add(stages[i]);
        }
    }

    private void showWeapons(int numStages) {
        // weapons[i] is for the weapons at stage i+1
        weapons = new StackPane[numStages];

        for (int i = 0; i < numStages; i++) {
            weapons[i] = new StackPane();
            weapons[i].setPrefHeight(HEIGHT);
            weapons[i].setPrefWidth(WIDTH);
            weapons[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");

            Label placeholder = new Label("Weapons");
            placeholder.setStyle("-fx-font-weight: bold; -fx-font-size: 26;");

            weapons[i].getChildren().add(placeholder);
            weapons[i].setAlignment(Pos.CENTER);
            GridPane.setColumnIndex(weapons[i], (i+1));
            GridPane.setRowIndex(weapons[i], 1);
            getChildren().add(weapons[i]);
        }
    }

    public boolean hasCard() {
        return set;
    }

    public void setFoeTest(Card card, int stage) {
        questSetup.addToInnerArray(stage-1, card);
        set = true;


        stages[stage-1].getChildren().clear();

        ImageView img = new ImageView(new Image(card.res));
        img.setTranslateX(0);
        img.setFitWidth(WIDTH);
        img.setFitHeight(HEIGHT);

        stages[stage-1].getChildren().add(img);
    }

    public void setFocus(int stage, int row) {
        set = false;
        int i = 0;
        if (row == 0) {
            // foe/test
            for (StackPane s : stages) {
                s.setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                weapons[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                i++;
            }

            stages[stage-1].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: gold");

        } else {
            // weapons
            // foe/test
            for (StackPane w : weapons) {
                stages[i].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                w.setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: black");
                i++;
            }

            weapons[stage-1].setStyle("-fx-border-style: solid inside; -fx-border-width: 5; -fx-border-color: gold");

            set = true;
        }
    }

    public void setStageBP(int stage, int BP) {
        ImageView img = new ImageView(new Image("Battle_Points.png"));
        img.setPreserveRatio(true);
        img.setFitHeight(85);
        StackPane.setAlignment(img, Pos.CENTER);

        Label label = new Label(BP + "");
        label.setFont(new Font("Cambria", 25));
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");


        stages[stage].getChildren().add(img);
        stages[stage].getChildren().add(label);
    }

    public void setWeapons(Card card, int stage) {

        if (card == null) {
            weapons[stage-1].setStyle("");
            weapons[stage-1].getChildren().clear();
            return;
        }

        questSetup.addToInnerArray(stage - 1, card);

        weapons[stage-1].getChildren().clear();

        List<Card> cards = questSetup.get(stage - 1);

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
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, weapons[stage - 1], false, 0, null, null));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            weapons[stage - 1].getChildren().add(image);

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                    image.setStyle((String) image.getProperties().get("color"));
            });
        }
    }

    public void clearQuest() {
        questSetup.clear();
        buildLayout();
    }

    public boolean isFoeStage(int stage) {
        if (questSetup.get(stage - 1).get(0).type == Card.Types.FOE )
            return true;

        return false;
    }

    public TwoDimensionalArrayList<Card> getQuestSetup() {
        return this.questSetup;
    }

    /*
     * if facedown, render everything facedown
     * else for each stage # in stage, render them face up and rest facedown.
     */
    public void rebuild(boolean faceDown, int[] stage) {

    }

    public void update() {

    }
}
