package com.mycompany.app.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * TODO:
 *  Needs to observe Game Model for changes to Adventure Deck
 *      - Empty deck means reset and shuffle
 *      - Else distribute like normal?
 */
public class AdventureDeckView extends HBox{
    private static final String BUTTON_STYLE = "-fx-background-color: transparent;";
    public AdventureDeckView() {
        // Manually get card images for now
        // Hook up model interface when that's implemented.
        String filepath = "/A Back.jpg";

        Button adventureDeck = new Button();
        adventureDeck.setStyle(BUTTON_STYLE);
        Image adventureDeckBack = new Image(filepath);
        ImageView backView = new ImageView(adventureDeckBack);
        backView.setPreserveRatio(true);
        backView.setFitHeight(180);

        adventureDeck.setGraphic(backView);

        getChildren().add(adventureDeck);
    }
}
