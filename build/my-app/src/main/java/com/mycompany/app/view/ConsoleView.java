package com.mycompany.app.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ConsoleView extends HBox {
    private final int HEIGHT = 200;
    private final int WIDTH = 146;

    private final double BTN_WIDTH = WIDTH/2.0;
    private final double BTN_HEIGHT = HEIGHT/4.0;

    public ConsoleView(){
        setSpacing(30.0);
        setAlignment(Pos.CENTER_RIGHT);
        Label consoleLog = new Label("\"Ready Player 1 - ARE YOU FUCKING READY MOTHERFUCKA\"");
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

        getChildren().addAll(consoleLog,buttonArea);

    }
}
