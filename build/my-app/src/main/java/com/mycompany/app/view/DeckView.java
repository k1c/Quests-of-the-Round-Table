package com.mycompany.app.view;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.GameObserver;
import com.mycompany.app.model.GameStates;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Stack;

/**
 * TODO:
 *  Needs to observe Game Model for changes to Adventure Deck
 *      - Empty deck means reset and shuffle
 *      - Else distribute like normal?
 *  Able to turn over story card after start turn button is clicked
 */
public class DeckView extends HBox implements GameObserver{

    private GameModel gameModel;

    private StackPane adventureDeck;
    private StackPane storyDeck;
    private ImageView adv, sty;

    private static final int WIDTH = 146;
    private static final int HEIGHT = 200;

    public DeckView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameModel.registerObserver(this);

        setSpacing(15.0);
        setAlignment(Pos.BOTTOM_LEFT);
        //setPadding(new Insets(10));

        String aFilepath = "A Back.jpg";
        String sFilepath = "S Back.jpg";

        adventureDeck = new StackPane();
        storyDeck = new StackPane();

        adv = new ImageView(new Image(aFilepath));
        adv.setFitWidth(WIDTH);
        adv.setFitHeight(HEIGHT);
        adventureDeck.getChildren().add(adv);



        sty = new ImageView(new Image(sFilepath));
        sty.setFitWidth(WIDTH);
        sty.setFitHeight(HEIGHT);
        storyDeck.getChildren().add(sty);



        getChildren().addAll(adventureDeck, storyDeck);
    }

    private void refreshStoryDeck() {
        storyDeck.getChildren().clear();
        sty.setImage(new Image(gameModel.getCurrentStory().res));
        storyDeck.getChildren().add(sty);
    }

    private void resetStoryDeck() {
        storyDeck.getChildren().clear();
        sty.setImage(new Image("S Back.jpg"));
        storyDeck.getChildren().add(sty);
    }

    public void update() {
        GameStates s = gameModel.getState();

        switch (s) {
            case EVENT_LOGIC:
            case SPONSOR_QUEST:
            case PARTICIPATE_TOURNAMENT:
                refreshStoryDeck();
                break;
            default:
                resetStoryDeck();
                break;
        }
    }
}
