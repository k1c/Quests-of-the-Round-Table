package com.mycompany.app.controller;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.GameObserver;
import com.mycompany.app.model.GameStates;
import com.mycompany.app.view.ConsoleView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class ConsoleController implements GameObserver{
    private GameModel gameModel;
    private ConsoleView consoleView;

    public ConsoleController(GameModel gameModel, ConsoleView consoleView) {
        this.gameModel = gameModel;
        this.consoleView = consoleView;

        this.gameModel.registerObserver(this);
        consoleView.display("Ready, " + this.gameModel.getCurrentPlayer().name + "?");
        consoleView.showButton("Start Turn", e -> this. gameModel.drawStoryCard());
    }

    public void startTurn() {
        gameModel.drawStoryCard();
    }

    public void update() {
        GameStates s = gameModel.getState();
        switch (s) {
            case BEGIN_TURN:
                consoleView.display("Ready, " + gameModel.getCurrentPlayer().name + "?");
                consoleView.showButton("Start Turn", e -> gameModel.drawStoryCard());
                break;
            case EVENT_LOGIC:
                consoleView.display("Begin event: " + gameModel.getCurrentStory().name);
                consoleView.showButton("Start Event", e -> gameModel.applyEventLogic());
                break;
            case END_TURN:
                consoleView.display("End turn?");
                consoleView.showButton("End Turn", e -> gameModel.endTurn());
                break;
            default:
                consoleView.display("Defaulted in ConsoleController.");
        }
    }
}
