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
        consoleView.showButton("Start Turn", e -> this. gameModel.drawStoryCard(), 1);
    }

    public void update() {
        GameStates s = gameModel.getState();
        switch (s) {
            case BEGIN_TURN:
                consoleView.display("Ready, " + gameModel.getCurrentPlayer().name + "?");
                consoleView.showButton("Start Turn", e -> gameModel.drawStoryCard(), 1);
                break;
            case EVENT_LOGIC:
                consoleView.display("Begin event: " + gameModel.getCurrentStory().name);
                consoleView.showButton("Start Event", e -> gameModel.applyEventLogic(), 1);
                break;
            case END_TURN:
                consoleView.display("End turn?");
                consoleView.showButton("End Turn", e -> gameModel.endTurn(), 1);
                break;
            case SPONSOR_QUEST:
                consoleView.display("Quest: " + gameModel.getCurrentStory().name);
                consoleView.showButton("Sponsor?", e -> gameModel.sponsorQuest(gameModel.getCurrentPlayer().id(), true), 1);
                consoleView.showButton("Decline?", e -> gameModel.sponsorQuest(gameModel.getCurrentPlayer().id(), false), 2);
                break;
            case SPONSOR_SUBMIT:
                consoleView.display(gameModel.getCurrentPlayer().name + " is now sponsoring the quest.\n" +
                        "Please start setting up quest.");
                consoleView.showButton("Setup Quest", e -> System.out.println("Nothing for now"), 1);
                break;
            default:
                consoleView.display("Defaulted in ConsoleController.");
        }
    }
}
