package com.mycompany.app.view;

import com.mycompany.app.model.*;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.*;

import javafx.scene.input.MouseEvent;

/**
 * Potential issue:
 *  1) Currently passing in the game model object
 *      - Any way to avoid this or make it better?
 */
public class CurrentPlayerView extends StackPane implements GameObserver{

    private GameModel model;
    private ViewGameBoard currentGameState;
    private static final int PADDING = 5;
    private static final int X_OFFSET = -75;
    private double currX;
    public CurrentPlayerView(GameModel gameModel){
        this.model = gameModel;
        this.currentGameState = model.getGameBoard();
        setPadding(new Insets(PADDING));
        setAlignment(Pos.TOP_CENTER);
        gameModel.registerObserver(this);
        buildLayout();
    }

    private void buildLayout() {
        getChildren().clear();

        int offset = 0;
        // get current player
        GenericPlayer current = currentGameState.players.get(0);
        // get current player hand and display
        ArrayList<Card> hand = current.hand;
        for(final Card card : hand) {
            // FOE,ALLY,WEAPON,AMOUR,TEST
            final Image im = new Image(card.res);
            final ImageView image = new ImageView(im);
            image.setId(card.id+"");
            image.setTranslateX(X_OFFSET*offset);
            switch (card.type) {
                case FOE:
                    image.setStyle("-fx-effect: innershadow(gaussian, white, 10, 0.0, 0, 0);");
                    break;
                case ALLY:
                    image.setStyle("-fx-effect: innershadow(gaussian, deepskyblue, 10, 0.0, 0, 0);");
                    break;
                case TEST:
                    image.setStyle("-fx-effect: innershadow(gaussian, forestgreen, 10, 0.0, 0, 0);");
                    break;
                case AMOUR:
                    image.setStyle("-fx-effect: innershadow(gaussian, yellow, 10, 0.0, 0, 0);");
                    break;
                case WEAPON:
                    image.setStyle("-fx-effect: innershadow(gaussian, red, 10, 0.0, 0, 0);");
                    break;
            }

            offset++;
            getChildren().add(image);

            image.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    image.setStyle("-fx-effect: dropshadow(gaussian, #ff7c14, 10, 0.7, 0, 0);");

                    ObservableList<Node> children = getChildren();
                    Node[] temp = new Node[children.size()];

                    for  (int i = 0; i < children.size(); i++) {
                        temp[i] = null;
                    }


                    for (Node child : children) {
                        int aIndex = (int) Math.abs(child.getTranslateX() / X_OFFSET);
                        temp[aIndex] = child;
                    }

                    List<Node> sortedChildren = new ArrayList<Node>(Arrays.asList(temp));
                    int index = sortedChildren.indexOf(image);

                    for (int i = 0; i < index; i++) {
                        sortedChildren.get(i).toFront();
                    }

                    for (int i = sortedChildren.size() - 1; i > index; i--) {
                        sortedChildren.get(i).toFront();
                    }

                   sortedChildren.get(index).toFront();

                }
            });

            image.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    switch (card.type) {
                        case FOE:
                            image.setStyle("-fx-effect: innershadow(gaussian, white, 10, 0.0, 0, 0);");
                            break;
                        case ALLY:
                            image.setStyle("-fx-effect: innershadow(gaussian, deepskyblue, 10, 0.0, 0, 0);");
                            break;
                        case TEST:
                            image.setStyle("-fx-effect: innershadow(gaussian, forestgreen, 10, 0.0, 0, 0);");
                            break;
                        case AMOUR:
                            image.setStyle("-fx-effect: innershadow(gaussian, yellow, 10, 0.0, 0, 0);");
                            break;
                        case WEAPON:
                            image.setStyle("-fx-effect: innershadow(gaussian, red, 10, 0.0, 0, 0);");
                            break;
                    }
                }
            });
        } // end for

        // get current player in-play and display
    }

    public void update() {
        // UPDATE CURRENT PLAYER STUFF
        this.currentGameState = model.getGameBoard();
        buildLayout();
    }
}
