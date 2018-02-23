package com.mycompany.app.controller;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.view.ConsoleView;

public class ConsoleController {
    private GameModel gameModel;
    private ConsoleView consoleView;

    public ConsoleController(GameModel gameModel, ConsoleView consoleView) {
        this.gameModel = gameModel;
        this.consoleView = consoleView;
    }

    public void startTurn() {
        gameModel.nextTurn();
    }
}
