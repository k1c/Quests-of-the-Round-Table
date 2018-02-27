/**
 * TODO:
 * 1) Update message based on game state?
 * 2) TextArea to wrap around.
 */
package com.mycompany.app.view;

import com.mycompany.app.model.GameModel;
import com.mycompany.app.controller.ConsoleController;
import com.mycompany.app.model.GameObserver;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class ConsoleView extends HBox{
    private ConsoleController consoleController;
    private  GameModel gameModel;

    private Label consoleLog;
    private VBox btnBox;

    public ConsoleView(GameModel gameModel){
        this.gameModel = gameModel;

        setSpacing(30.0);
        setPrefWidth(500);
        setAlignment(Pos.CENTER_RIGHT);

        btnBox = new VBox(10);
        btnBox.setAlignment(Pos.CENTER);

        consoleLog = new Label("check");
        consoleLog.setWrapText(true);
        //consoleLog.setTextAlignment(TextAlignment.LEFT);
        consoleLog.setFont(new Font("Cambria",26));


        consoleController = new ConsoleController(gameModel, this);
    }

    public void display(String toDisplay) {
        int index = getChildren().indexOf(consoleLog);
        if(index != -1)
            getChildren().remove(consoleLog);
        else
            index = 0;


        consoleLog.setText(toDisplay);

        getChildren().add(index, consoleLog);
    }

    public void showButton(String name, EventHandler<MouseEvent> handler, int numButtons) {
        if (numButtons == 1)
            btnBox.getChildren().clear();

        getChildren().remove(btnBox);

        Button btn = new Button(name);
        //btn.setWrapText(true);
        btn.setFont(new Font("Cambria",20));

        btn.setOnMouseClicked(handler);

        btnBox.getChildren().add(btn);

        getChildren().add(btnBox);
    }
}
