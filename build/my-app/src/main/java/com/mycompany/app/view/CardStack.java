package com.mycompany.app.view;

import com.mycompany.app.model.Card;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public interface CardStack {

    default void createStack(List<Card> cards, StackPane s, final boolean faceDown, int height, int width, int x_offset) {
        int offset = 0;

        for (Card card : cards) {
            final ImageView image;
            if (faceDown) {
                image = new ImageView(new Image("A Back.jpg"));
            }
            else {
                image = new ImageView(new Image(card.res));
                // Add border with same color as card
                image.getProperties().put("color", getColor(card));

                // Add focus event handler
                image.addEventHandler(MouseEvent.MOUSE_ENTERED, focusCard(image, s));
            }

            // Set alignment
            StackPane.setAlignment(image, Pos.BOTTOM_LEFT);

            // Scale image
            image.setFitHeight(height);
            image.setFitWidth(width);

            // Move to left for 'stack' effect
            image.setTranslateX(x_offset * offset);

            // Add 'correct' position of the card in the stackpane
            // correct means visual index instead of calculated z-index which changes
            image.setId(offset + "");

            image.setStyle((String) image.getProperties().get("color"));

            // Offset/Position for next card
            offset++;

            // Add to StackPane
            s.getChildren().add(image);

            // Reset border color when mouse is no longer hovering on card
            image.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                        if (!faceDown)
                            image.setStyle((String) image.getProperties().get("color"));
                    }
            );
        }
    }


    default EventHandler<MouseEvent> focusCard(final ImageView image, final StackPane p) {
        return e -> {
            // Selection color
            image.setStyle("-fx-effect: dropshadow(gaussian, #ff7c14, 5, 1, 0, 0)");

            // Get all current cards on display
            List<Node> children = new ArrayList<>(p.getChildren());

            // Sort by actual index instead of z-index
            children.sort((o1, o2) -> {
                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
            });

            // Get position of card user is hovering over
            int index = children.indexOf(image);

            // Start to re-arrange cards from both ends till they meet
            int right = 0;
            int left = children.size() - 1;

            while (right != left) {
                if (!children.get(right).equals(image)) {
                    children.get(right).toFront();
                    right++;
                }

                if (!children.get(left).equals(image)) {
                    children.get(left).toFront();
                    left--;
                }
            }

            // Bring target card in front
            children.get(index).toFront();
        };
    }

    default String getColor(Card card) {
        String css = "-fx-effect: innershadow(gaussian, ";

        switch (card.type) {
            case FOE:
                css += "white, ";
                break;
            case ALLY:
                css += "deepskyblue, ";
                break;
            case TEST:
                css += "forestgreen, ";
                break;
            case AMOUR:
                css += "yellow, ";
                break;
            case WEAPON:
                css += "red, ";
                break;
        }

        css += "10, 0.7, 0, 0);";

        return css;
    }
}
