/**
 * TODO:
 * 1) Update message based on game state?
 * 2) TextArea to wrap around.
 */
package com.mycompany.app.view;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.controller.ConsoleController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ConsoleView extends HBox {
    private ConsoleController consoleController;
    private  GameModel gameModel;
    private final int HEIGHT = 200;
    private final int WIDTH = 146;

    private final double BTN_WIDTH = WIDTH/2.0;
    private final double BTN_HEIGHT = HEIGHT/4.0;

    public ConsoleView(GameModel gameModel){
        this.gameModel = gameModel;
        consoleController = new ConsoleController(gameModel, this);

        setSpacing(30.0);
        setPrefWidth(500);
        setAlignment(Pos.CENTER_RIGHT);

        Label consoleLog = new Label("\"Ready Player 1 - aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"");
        consoleLog.setWrapText(true);
        consoleLog.setTextAlignment(TextAlignment.LEFT);
        consoleLog.setFont(new Font("Cambria",26));

        VBox buttonArea = new VBox(10);

        Button startTurn = new Button("Start Turn");
        Button endTurn = new Button("End Turn");
        startTurn.setMinSize(BTN_WIDTH, BTN_HEIGHT);
        startTurn.setMaxSize(BTN_WIDTH, BTN_HEIGHT);
        startTurn.setPrefSize(BTN_WIDTH, BTN_HEIGHT);

        endTurn.setMinSize(BTN_WIDTH, BTN_HEIGHT);
        endTurn.setMaxSize(BTN_WIDTH, BTN_HEIGHT);
        endTurn.setPrefSize(BTN_WIDTH, BTN_HEIGHT);

        endTurn.setDisable(true);
        buttonArea.setAlignment(Pos.CENTER);
        buttonArea.getChildren().addAll(startTurn,endTurn);

        startTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                consoleController.startTurn();
            }
        });

        getChildren().addAll(consoleLog,buttonArea);

    }
}
