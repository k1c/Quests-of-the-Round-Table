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
        ColumnConstraints questC = new ColumnConstraints();
        questC.setHgrow(Priority.SOMETIMES);
        questC.setMinWidth(WIDTH);
        questC.setPrefWidth(WIDTH);
        getColumnConstraints().add(questC);

        ColumnConstraints stage1C = new ColumnConstraints();
        stage1C.setHgrow(Priority.SOMETIMES);
        stage1C.setMinWidth(WIDTH);
        stage1C.setPrefWidth(WIDTH);
        getColumnConstraints().add(stage1C);

        ColumnConstraints stage2C = new ColumnConstraints();
        stage2C.setHgrow(Priority.SOMETIMES);
        stage2C.setMinWidth(WIDTH);
        stage2C.setPrefWidth(WIDTH);
        getColumnConstraints().add(stage2C);

        RowConstraints row = new RowConstraints();
        row.setMaxHeight(HEIGHT);
        row.setMinHeight(HEIGHT);
        row.setPrefHeight(HEIGHT);
        row.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        getRowConstraints().add(row);

        RowConstraints row2 = new RowConstraints();
        row2.setMaxHeight(HEIGHT);
        row2.setMinHeight(HEIGHT);
        row2.setPrefHeight(HEIGHT);
        row2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
        getRowConstraints().add(row2);

        StackPane questCard = new StackPane();
        GridPane.setColumnIndex(questCard, 0);
        GridPane.setRowIndex(questCard, 0);

        ImageView card = new ImageView(new Image("Q Boar Hunt.jpg"));

        card.setFitHeight(HEIGHT);
        card.setFitWidth(WIDTH);

        questCard.getChildren().add(card);

        getChildren().add(questCard);

        StackPane foe1 = new StackPane();
        GridPane.setColumnIndex(foe1, 1);

        ImageView card2 = new ImageView(new Image("F Thieves.jpg"));

        card2.setFitWidth(WIDTH);
        card2.setFitHeight(HEIGHT);

        foe1.getChildren().add(card2);

        getChildren().add(foe1);

        StackPane foe2 = new StackPane();
        GridPane.setColumnIndex(foe2, 2);

        ImageView card3 = new ImageView(new Image("F Boar.jpg"));

        card3.setFitWidth(WIDTH);
        card3.setFitHeight(HEIGHT);

        foe2.getChildren().add(card3);

        getChildren().add(foe2);

        // WEAPONS
        StackPane foe1weapons = new StackPane();
        GridPane.setColumnIndex(foe1weapons, 1);
        GridPane.setRowIndex(foe1weapons, 1);

        String[] weapons1 = {"W Dagger.jpg", "W Horse.jpg", "W Sword.jpg"};
        for (int i = 0; i < weapons1.length; i++) {
            ImageView c = new ImageView(new Image(weapons1[i]));

            c.setFitWidth(WIDTH);
            c.setFitHeight(HEIGHT);
            c.setTranslateY((HEIGHT/6) * i);
            foe1weapons.getChildren().add(c);
        }

        getChildren().add(foe1weapons);

        // WEAPONS
        StackPane foe2weapons = new StackPane();
        GridPane.setColumnIndex(foe2weapons, 2);
        GridPane.setRowIndex(foe2weapons, 1);

        String[] weapons2 = {"W Battle-ax.jpg", "W Lance.jpg", "W Excalibur.jpg"};
        for (int i = 0; i < weapons2.length; i++) {
            ImageView c = new ImageView(new Image(weapons2[i]));

            c.setFitWidth(WIDTH);
            c.setFitHeight(HEIGHT);
            c.setTranslateY((HEIGHT/6) * i);
            foe2weapons.getChildren().add(c);
        }

        getChildren().add(foe2weapons);
    }

    public void update() {

    }
}
