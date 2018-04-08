package com.mycompany.app.controller;

import com.mycompany.app.GameLogger;
import com.mycompany.app.model.GameModel;
import com.mycompany.app.model.Interfaces.GameObserver;
import com.mycompany.app.model.GameStates;
import com.mycompany.app.view.ConsoleView;

public class ConsoleController implements GameObserver {
    private GameModel gameModel;
    private ConsoleView consoleView;
    private GameController gameController;

    private GameLogger log = GameLogger.getInstanceUsingDoubleLocking();

    public ConsoleController(GameModel gameModel, ConsoleView consoleView, GameController gameController) {
        this.gameModel = gameModel;
        this.consoleView = consoleView;
        this.gameController = gameController;

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
                consoleView.showButton("Setup Quest", e -> this.gameController.setup(1, 0), 1);
                break;
            case TOURNAMENT_STAGE:
                consoleView.display(gameModel.getCurrentPlayer().name + " is now in the tournament");
                consoleView.showButton("Setup Tournament", e -> this.gameController.setupTournament(1, 0), 1);
                break;
            case PARTICIPATE_QUEST:
                consoleView.display(gameModel.getCurrentPlayer().name + ", participate in quest?");
                consoleView.showButton("Participate", e -> gameModel.participateQuest(gameModel.getCurrentPlayer().id(), true), 1);
                consoleView.showButton("Decline", e -> gameModel.participateQuest(gameModel.getCurrentPlayer().id(), false), 2);
                break;
            case PARTICIPATE_TOURNAMENT:
                consoleView.display(gameModel.getCurrentPlayer().name + ", participate in tournament?");
                consoleView.showButton("Participate", e -> gameModel.participateTournament(gameModel.getCurrentPlayer().id(), true), 1);
                consoleView.showButton("Decline", e -> gameModel.participateTournament(gameModel.getCurrentPlayer().id(), false), 2);
                break;
            case QUEST_HANDLER:
                consoleView.display("Begin/Continue on Quest");
                consoleView.showButton("Begin", e -> gameModel.stage(), 1);
                break;
            case TOURNAMENT_HANDLER:
                consoleView.display("Depart in Tournament?");
                consoleView.showButton("Begin", e -> gameModel.tournamentStageStart(), 1);
                break;
            case DISCARD:
                consoleView.display("You have more than 12 cards!\nPlease discard.");
                consoleView.showButton("Begin", e -> gameController.discard(), 1);
                break;
            case STAGE_FOE:
                consoleView.display("Next stage is a Foe.");
                consoleView.showButton("Begin", e -> gameController.startFoeStage(), 1);
                break;
            case STAGE_TEST:
                consoleView.display("Next stage is a Test.");
                consoleView.showButton("Begin", e -> gameController.startTestStage(), 1);
                break;
            case STAGE_END:
                consoleView.display("Run the stage?");
                consoleView.showButton("End Stage", e -> gameModel.stageEnd(), 1);
                break;
        }
    }
}
