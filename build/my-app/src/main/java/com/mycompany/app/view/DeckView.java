package com.mycompany.app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * TODO:
 *  Needs to observe Game Model for changes to Adventure Deck
 *      - Empty deck means reset and shuffle
 *      - Else distribute like normal?
 */
public class DeckView extends HBox{

    private static final int WIDTH = 146;
    private static final int HEIGHT = 200;

    public DeckView() {

        setSpacing(15.0);
        setAlignment(Pos.BOTTOM_LEFT);
        setPadding(new Insets(10));

        String aFilepath = "A Back.jpg";
        String sFilepath = "S Back.jpg";

        StackPane adventureDeck = new StackPane();
        StackPane storyDeck = new StackPane();
        // Create stack effect?
        for(int i = 0; i < 4; i++) {
            ImageView c = new ImageView(new Image(aFilepath));
            c.setFitWidth(WIDTH);
            c.setFitHeight(HEIGHT);
            //c.setTranslateX(2*i);
            adventureDeck.getChildren().add(c);
        }

        for(int i = 0; i < 4; i++) {
            ImageView c = new ImageView(new Image(sFilepath));
            c.setFitWidth(WIDTH);
            c.setFitHeight(HEIGHT);
            //c.setTranslateX(-2*i);
            storyDeck.getChildren().add(c);
        }


        getChildren().addAll(adventureDeck, storyDeck);
    }
}
