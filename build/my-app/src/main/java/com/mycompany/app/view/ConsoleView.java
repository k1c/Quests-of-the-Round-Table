package com.mycompany.app.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ConsoleView extends HBox {

    public ConsoleView(){
        setSpacing(30.0);
        setAlignment(Pos.CENTER_RIGHT);
        Label consoleLog = new Label("\"Ready Player 1\"");
        consoleLog.setFont(new Font("Cambria",26));

        VBox buttonArea = new VBox(10);

        Button startTurn = new Button("Start Turn");
        Button endTurn = new Button("End Turn");
        endTurn.setDisable(true);
        buttonArea.setAlignment(Pos.CENTER);
        buttonArea.getChildren().addAll(startTurn,endTurn);

        getChildren().addAll(consoleLog,buttonArea);

    }
}
