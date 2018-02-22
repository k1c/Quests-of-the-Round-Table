package com.mycompany.app.view;

import com.mycompany.app.Quests;
import com.mycompany.app.model.GameObserver;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class QuestsView extends GridPane implements GameObserver{
    private final int WIDTH = 146;
    private final int HEIGHT = 200;

    public QuestsView() {
        setHgap(10.0);
        // two rows
        // row 1
        //  - Q F1 F2
        // row 2
        //  -   St St

        // Num cols and num rows
        int numCols = 5;
        int numRows = 2;

        for (int i = 0; i < numCols+1; i++) {
            ColumnConstraints questC = new ColumnConstraints();
            questC.setHgrow(Priority.SOMETIMES);
            questC.setMinWidth(WIDTH);
            questC.setPrefWidth(WIDTH);
            getColumnConstraints().add(questC);
        }

        for (int i = 0; i < numRows; i++) {
            RowConstraints row = new RowConstraints();
            row.setMaxHeight(HEIGHT);
            row.setMinHeight(HEIGHT);
            row.setPrefHeight(HEIGHT);
            row.setVgrow(Priority.SOMETIMES);
            getRowConstraints().add(row);
        }

        for (int i = 1; i < numCols+1; i++) {
            StackPane card = new StackPane();
            GridPane.setColumnIndex(card, i);
            GridPane.setRowIndex(card, 0);

            ImageView img = new ImageView(new Image("F Thieves.jpg"));
            img.setFitHeight(HEIGHT);
            img.setFitWidth(WIDTH);

            card.getChildren().add(img);
            getChildren().add(card);
        }

        for (int i = 1; i < numCols+1; i++) {
            StackPane card = new StackPane();
            GridPane.setColumnIndex(card, i);
            GridPane.setRowIndex(card, 1);

            for (int j = 0; j < 6; j++) {
                ImageView c = new ImageView(new Image("W Dagger.jpg"));
                c.setFitWidth(WIDTH);
                c.setFitHeight(HEIGHT);
                c.setTranslateY((HEIGHT/6) * j);
                card.getChildren().add(c);
            }

            getChildren().add(card);
        }


        StackPane questCard = new StackPane();
        GridPane.setColumnIndex(questCard, 0);
        GridPane.setRowIndex(questCard, 0);

        ImageView card = new ImageView(new Image("Q Boar Hunt.jpg"));

        card.setFitHeight(HEIGHT);
        card.setFitWidth(WIDTH);

        questCard.getChildren().add(card);

        getChildren().add(questCard);
    }

    public void update() {

    }
}
