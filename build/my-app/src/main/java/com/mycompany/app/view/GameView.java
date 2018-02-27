/**
 * REMINDER:
 * 1) This class contains Lambda expressions. Change language level to 8 (1.8).
 * TODO:
 * 1) Aesthetics
 */
package com.mycompany.app.view;

import com.mycompany.app.GameLogger;
import com.mycompany.app.controller.GameController;
import com.mycompany.app.model.GameModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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

        GameLogger log = GameLogger.getInstanceUsingDoubleLocking();
        log.gameState("Start Menu Screen");
        log.gameState("Gathering Game Config from User");

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
        humanLabel.setFont(new Font("Cambria",30));

        humanLabel.setPadding(new Insets(90.0, 0.0, 0.0, 0.0));
        Slider humanSlider = makeSlider(1,4,1);
        humanSlider.setPadding(new Insets(0.0, 0.0, 10.0, 0.0));

        Label aiLabel = new Label("AIs");
        aiLabel.setFont(new Font("Cambria",30));
        Slider aiSlider = makeSlider(1,3,0);
        aiSlider.setPadding(new Insets(0.0, 0.0, 10.0, 0.0));

        Label player1 = new Label("Player 1 Name:");
        TextField textField1 = new TextField();
        addValidationListener(textField1);
        player1.setVisible(true);
        textField1.setVisible(true);
        HBox player1HB = new HBox();
        player1HB.getChildren().addAll(player1, textField1);
        player1HB.setSpacing(10);

        Label player2 = new Label("Player 2 Name:");
        TextField textField2 = new TextField();
        addValidationListener(textField2);
        player2.setVisible(false);
        textField2.setVisible(false);
        HBox player2HB = new HBox();
        player2HB.getChildren().addAll(player2, textField2);
        player2HB.setSpacing(10);

        Label player3 = new Label("Player 3 Name:");
        HBox player3HB = new HBox();
        TextField textField3 = new TextField();
        addValidationListener(textField3);
        player3.setVisible(false);
        textField3.setVisible(false);
        player3HB.getChildren().addAll(player3, textField3);
        player3HB.setSpacing(10);

        Label player4 = new Label("Player 4 Name:");
        HBox player4HB = new HBox();
        TextField textField4 = new TextField();
        addValidationListener(textField4);
        player4.setVisible(false);
        textField4.setVisible(false);
        player4HB.getChildren().addAll(player4, textField4);
        player4HB.setSpacing(10);

        player1.setFont(new Font("Cambria", 30));
        player2.setFont(new Font("Cambria", 30));
        player3.setFont(new Font("Cambria", 30));
        player4.setFont(new Font("Cambria", 30));

        player1HB.setAlignment(Pos.CENTER_LEFT);
        player2HB.setAlignment(Pos.CENTER_LEFT);
        player3HB.setAlignment(Pos.CENTER_LEFT);
        player4HB.setAlignment(Pos.CENTER_LEFT);

        player4HB.setPadding(new Insets(0, 0, 30.0, 0));

        textField1.setMinHeight(30);
        textField2.setMinHeight(30);
        textField3.setMinHeight(30);
        textField4.setMinHeight(30);

        humanSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    int humanValue = newValue.intValue();

                    if (humanValue == 1){
                        aiSlider.setMin(1.0);

                        player1.setVisible(true);
                        textField1.setVisible(true);
                        player2.setVisible(false);
                        textField2.setVisible(false);
                        player3.setVisible(false);
                        textField3.setVisible(false);
                        player4.setVisible(false);
                        textField4.setVisible(false);
                    }else{
                        aiSlider.setMin(0.0);
                        aiSlider.setValue(0.0);
                    }

                    if (humanValue == 2){
                        player1.setVisible(true);
                        textField1.setVisible(true);
                        player2.setVisible(true);
                        textField2.setVisible(true);
                        player3.setVisible(false);
                        textField3.setVisible(false);
                        player4.setVisible(false);
                        textField4.setVisible(false);
                    }

                    if (humanValue == 3){
                        player1.setVisible(true);
                        textField1.setVisible(true);
                        player2.setVisible(true);
                        textField2.setVisible(true);
                        player3.setVisible(true);
                        textField3.setVisible(true);
                        player4.setVisible(false);
                        textField4.setVisible(false);
                    }

                    if (humanValue == 4){
                        aiSlider.setDisable(true);

                        player1.setVisible(true);
                        textField1.setVisible(true);
                        player2.setVisible(true);
                        textField2.setVisible(true);
                        player3.setVisible(true);
                        textField3.setVisible(true);
                        player4.setVisible(true);
                        textField4.setVisible(true);
                    } else {
                        aiSlider.setDisable(false);
                        aiSlider.setMax(4.0-humanValue);
                    }

                } );

        final Button startGame = new Button();
        startGame.setText("Start Game");
        startGame.setFont(new Font("Cambria",30));

        startGame.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                String humanNames[] = new String[4];
                if (textField1.isVisible()){
                    humanNames[0] = textField1.getText();
                }
                if (textField2.isVisible()){
                    humanNames[1] = textField2.getText();
                }
                if (textField3.isVisible()){
                    humanNames[2] = textField3.getText();
                }
                if (textField4.isVisible()){
                    humanNames[3] = textField4.getText();
                }
                gameController.startGame((Stage) startGame.getScene().getWindow(), (int) humanSlider.getValue(), (int) aiSlider.getValue(), humanNames);
            }
        });

        addValidationListener(startGame, textField1, textField2, textField3, textField4);

        VBox gameMenu = new VBox(20, title , startGame, humanLabel, humanSlider, aiLabel, aiSlider, player1HB, player2HB, player3HB, player4HB);
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

    private void addValidationListener(TextField textField){
        GameLogger log = GameLogger.getInstanceUsingDoubleLocking();
        textField.setId("red");
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (newValue.trim().length() > 11 || newValue.trim() == null || newValue.trim().isEmpty()){
                    log.error("Player Name Over 11 Chars");
                    textField.setStyle("-fx-effect: dropshadow(three-pass-box,red,10.0,0.7,0.0,0.0)");
                    textField.setId("red");
                } else {
                    textField.setStyle("-fx-effect: dropshadow(three-pass-box,green,10.0,0.7,0.0,0.0)");
                    textField.setId("green");
                }
            }
        });
    }

    private void addValidationListener(Button startButton, TextField textField1, TextField textField2, TextField textField3, TextField textField4){
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(textField1.visibleProperty(),
                        textField2.visibleProperty(),
                        textField3.visibleProperty(),
                        textField4.visibleProperty());
            }

            {
                super.bind(textField1.idProperty(),
                        textField2.idProperty(),
                        textField3.idProperty(),
                        textField4.idProperty());
            }
            @Override
            protected boolean computeValue() {
                if (textField1.isVisible() && textField1.getId().equals("red")){
                    return true;
                }
                if (textField2.isVisible() && textField2.getId().equals("red") ){
                    return true;
                }
                if (textField3.isVisible() && textField3.getId().equals("red")){
                    return true;
                }
                if (textField4.isVisible() && textField4.getId().equals("red") ){
                    return true;
                }
                return false;
            }
        };
        startButton.disableProperty().bind(bb);
    }
}
