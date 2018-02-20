package com.mycompany.app.view;

import com.mycompany.app.controller.GameController;
import com.mycompany.app.model.GameModel;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class GameView extends GridPane{

    private GameModel model;
    private GameController controller;

    public GameView(GameModel gameModel) {
        this.model = gameModel;
        this.controller = new GameController(model, this);

        final Button start = new Button();

        start.setText("Start Game");

        start.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.startGame(start);
            }
        });

        getChildren().add(start);
    }
}
