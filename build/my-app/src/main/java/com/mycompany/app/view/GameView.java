package com.mycompany.app.view;

import com.mycompany.app.controller.GameController;
import com.mycompany.app.model.GameModel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Pos;

public class GameView extends HBox {

    private GameController gameController;

    public GameView(GameModel gameModel) {
        this.gameController = new GameController(gameModel, this);

        Image roundTable = new Image("roundtable-tpbackground.png");
        ImageView roundTableView = new ImageView(roundTable);
        roundTableView.setPreserveRatio(true);
        roundTableView.setFitWidth(roundTable.getWidth()/2);
        getChildren().add(roundTableView);

        Region fillerCenter = new Region();
        setHgrow(fillerCenter,Priority.ALWAYS);
        getChildren().add(fillerCenter);

        Label title = new Label("Quests of the Round Table");
        title.setFont(new Font("Cambria",45));

        Label humanLabel = new Label("Humans");
        humanLabel.setFont(new Font("Cambria",20));

        humanLabel.setPadding(new Insets(100.0, 0.0, 0.0, 0.0));
        Slider humanSlider = makeSlider(1,4,1);
        humanSlider.setPadding(new Insets(0.0, 0.0, 100.0, 0.0));

        Label aiLabel = new Label("AIs");
        aiLabel.setFont(new Font("Cambria",20));
        Slider aiSlider = makeSlider(1,3,0);
        aiSlider.setPadding(new Insets(0.0, 0.0, 100.0, 0.0));

        humanSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    int i = newValue.intValue();

                    if (i == 1){
                        aiSlider.setMin(1.0);
                    }else{
                        aiSlider.setMin(0.0);
                        aiSlider.setValue(0.0);
                    }

                    if (i == 4){
                        aiSlider.setDisable(true);
                    } else {
                        aiSlider.setDisable(false);
                        aiSlider.setMax(4.0-i);
                    }
                } );

        final Button startGame = new Button();

        startGame.setText("Start Game");
        startGame.setFont(new Font("Cambria",20));
        startGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Gather form info - names
                String[] names = {"Carolyne", "Akhil", "Chris", "Alexa"};
                gameController.startGame((Stage) startGame.getScene().getWindow(), (int) humanSlider.getValue(), (int) aiSlider.getValue(), names);
            }
        });

        VBox gameMenu = new VBox(10, title ,humanLabel, humanSlider, aiLabel, aiSlider, startGame);
        gameMenu.setPadding(new Insets(100.0, 0.0, 0.0,0.0));
        gameMenu.setAlignment(Pos.TOP_LEFT);

        getChildren().add(gameMenu);
        Region fillerRight = new Region();
        setHgrow(fillerRight,Priority.ALWAYS);
        getChildren().add(fillerRight);
    }

    private Slider makeSlider(int min, int max, int value) {
        Slider s = new Slider(min,max,value);
        s.setSnapToTicks(true);
        s.setOrientation(Orientation.HORIZONTAL);
        s.setPrefWidth(150.0);
        s.setShowTickMarks(true);
        s.setMajorTickUnit(1);
        s.setMinorTickCount(0);
        s.setShowTickLabels(true);
        return s;
    }
}
