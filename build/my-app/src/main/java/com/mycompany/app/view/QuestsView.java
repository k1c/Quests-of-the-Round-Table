/**
 * TODO:
 * 1) Able to query model for # of stages
 * 2) Able to select each stage and build it
 * 3) Able to submit proposed quest
 * 4) If denied, able to correct
 * 5) If accepted, able to run correctly (face down, face up per stage encounter)
 */
package com.mycompany.app.view;

import com.mycompany.app.Quests;
import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.GameObserver;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class QuestsView extends GridPane implements GameObserver{
    private final int WIDTH = 146;
    private final int HEIGHT = 200;
    private final GameModel gameModel;

    public QuestsView(GameModel gameModel) {
        this.gameModel = gameModel;

        setHgap(10.0);

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

        // Display Weapon placeholders

        // Display total BP
    }

    private void showQuestCard (ImageView questCard) {
        StackPane qc = new StackPane();

        qc.getChildren().add(questCard);

        GridPane.setRowIndex(qc, 0);
        GridPane.setColumnIndex(qc, 0);

        getChildren().add(questCard);
    }

    public void update() {

    }
}
