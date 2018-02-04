package com.mycompany.app.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
/**
 * TODO:
 *  Needs to observe Game Model for changes to Story Deck
 *      - Empty deck means reset and shuffle
 *      - Else distribute like normal?
 */
public class StoryDeckView extends HBox{
    private static final String BUTTON_STYLE = "-fx-background-color: transparent;";
    public StoryDeckView() {
        // Manually get card images for now
        // Hook up model interface when that's implemented.
        String filepath = "C:\\Developer\\Project3004\\Quests-of-the-Round-Table\\documents\\resources\\CroppedCards\\S Back.jpg";

        Button storyDeck = new Button();
        storyDeck.setStyle(BUTTON_STYLE);
        Image storyDeckBack = new Image("File:"+filepath);
        ImageView backView = new ImageView(storyDeckBack);
        backView.setPreserveRatio(true);
        backView.setFitHeight(180);

        storyDeck.setGraphic(backView);

        getChildren().add(storyDeck);
    }
}
