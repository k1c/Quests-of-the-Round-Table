package com.mycompany.app;

import com.mycompany.app.controller.GameController;
import com.mycompany.app.model.GameObserver;
import com.mycompany.app.view.*;
import com.mycompany.app.model.GameModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

/**
 * Quests class brings together major UI components and launches the application.
 *
 * to run:
 *   mvn package
 *   java -cp target/my-app-1.0-SNAPSHOT.jar com.mycompany.app.Quests
 */

public class Quests extends Application{

    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final String TITLE = "Quests of the Round Table";

    private GameModel model = new GameModel();
    private GameView view = new GameView(model);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(TITLE);

        BorderPane root = new BorderPane();

        root.setCenter(view);

        Scene start = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setScene(start);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
